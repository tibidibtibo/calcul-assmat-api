package fr.deboissieu.calculassmat.bl.saisie.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.parametrage.ParametrageBlo;
import fr.deboissieu.calculassmat.bl.saisie.ExcelFileBlo;
import fr.deboissieu.calculassmat.bl.saisie.SaisieBlo;
import fr.deboissieu.calculassmat.bl.synthese.SyntheseBlo;
import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.commons.exceptions.ValidationExceptionsEnum;
import fr.deboissieu.calculassmat.dl.CertificationRepository;
import fr.deboissieu.calculassmat.dl.SaisieRepository;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.Saisie;
import fr.deboissieu.calculassmat.model.saisie.SaisieEnfantDto;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

@Component
public class SaisieBloImpl implements SaisieBlo {

	private static final Logger logger = LogManager.getLogger(SaisieBloImpl.class);

	@Resource
	SaisieRepository saisieRepository;

	@Resource
	ExcelFileBlo excelFileBlo;

	@Resource
	ParametrageBlo parametrageBlo;

	@Resource
	CertificationRepository certificationRepository;

	@Resource
	SyntheseBlo syntheseBlo;

	@Override
	public void enregistrerSaisie(SaisieRequest saisie) {
		saisie.getSaisie().stream().forEach(s -> {
			saisieRepository.save(SaisieEnfantDto.toSaisie(s));
		});
	}

	@Override
	public Collection<SaisieEnfantDto> findSaisiesByMonth(Integer month, Integer year) {

		Date startDate = DateUtils.getDate(year, month, 1);
		Date stopDate = DateUtils.toMaxMonth(month, year);
		Map<String, ParametrageEnfant> paramsEnfant = parametrageBlo.getMapIdParamsEnfants();
		Map<String, ParametrageEmploye> paramsEmploye = parametrageBlo.getMapIdParamsEmployes();
		return SaisieEnfantDto.toSaisieEnfantDto(saisieRepository.findSaisieBetween(startDate, stopDate), paramsEnfant,
				paramsEmploye);

	}

	@Override
	public void importerFichierSaisie(Integer numeroMois, Integer numeroAnnee, String fileName) throws IOException {

		// Ouverture du fichier en tant que Workbook et extraction des saisies
		Workbook workbook = excelFileBlo.openWorkbook(fileName);

		if (workbook != null) {

			try {
				Collection<SaisieJournaliere> saisiesJournalieres = excelFileBlo.extractDataFromWorkbook(workbook,
						numeroMois,
						numeroAnnee);

				// Consolidation des paramètrages et conversion en saisie entités
				Map<String, ParametrageEnfant> mapParamEnfants = parametrageBlo.getMapNomParamsEnfants();
				Collection<ParametrageEmploye> paramsEmployes = parametrageBlo.findAllEmployes();
				Collection<Saisie> saisies = consoliderSaisiesJournalieres(saisiesJournalieres, mapParamEnfants,
						paramsEmployes);

				// Save
				saisieRepository.saveAll(saisies);
			} catch (Exception e) {
				logger.error("Impossible d'importer le fichier ! Erreur : ");
				e.printStackTrace();
			} finally {
				workbook.close();
			}
		}

	}

	private Collection<Saisie> consoliderSaisiesJournalieres(Collection<SaisieJournaliere> saisiesJournalieres,
			Map<String, ParametrageEnfant> mapParamEnfants, Collection<ParametrageEmploye> paramsEmployes) {
		return saisiesJournalieres.stream()
				.map(saisieJourn -> {

					Saisie saisie = new Saisie();

					// Paramétrage
					lierParametrage(mapParamEnfants, paramsEmployes, saisieJourn, saisie);

					// Dates et heures
					calculerDateHeures(saisieJourn, saisie);

					// Autres données
					saisie.setAutresDeplacementKm(saisieJourn.getAutresDeplacementKm());
					saisie.setNbArEcole(saisieJourn.getNbArEcole());
					saisie.setNbDejeuners(saisieJourn.getNbDejeuners());
					saisie.setNbGouters(saisieJourn.getNbGouters());
					return saisie;
				}).collect(Collectors.toList());
	}

	private void calculerDateHeures(SaisieJournaliere saisieJourn, Saisie saisie) {

		// Date
		saisie.setDateSaisie(saisieJourn.getDateSaisie());

		// Heures
		if (StringUtils.isNotBlank(saisieJourn.getHeureArrivee())) {
			Date heureArrivee = DateUtils.fromLocalTimeAndDate(
					DateUtils.toLocalTime(saisieJourn.getHeureArrivee()), saisieJourn.getDateSaisie());
			saisie.setHeureArrivee(heureArrivee);
		}
		if (StringUtils.isNotBlank(saisieJourn.getHeureDepart())) {
			Date heureDepart = DateUtils.fromLocalTimeAndDate(
					DateUtils.toLocalTime(saisieJourn.getHeureDepart()),
					saisieJourn.getDateSaisie());
			saisie.setHeureDepart(heureDepart);
		}
	}

	private void lierParametrage(Map<String, ParametrageEnfant> mapParamEnfants,
			Collection<ParametrageEmploye> paramsEmployes, SaisieJournaliere saisieJourn, Saisie saisie) {

		// Param enfant
		ParametrageEnfant paramEnfant = mapParamEnfants.get(saisieJourn.getEnfant());
		if (paramEnfant != null) {
			saisie.setEnfantId(paramEnfant.get_id());
		} else {
			throw new ValidationException(ValidationExceptionsEnum.V006.toString(saisieJourn.getEnfant()));
		}

		// Param employé
		Optional<ParametrageEmploye> paramEmploye = paramsEmployes.stream()
				.filter(param -> {
					return StringUtils.equals(param.getNom(), saisieJourn.getEmploye());
				})
				.findFirst();
		if (paramEmploye != null) {
			saisie.setEmployeId((paramEmploye.isPresent()) ? paramEmploye.get().get_id() : null);
		} else {
			throw new ValidationException(ValidationExceptionsEnum.V004.toString(saisieJourn.getEmploye(), null));
		}
	}

	@Override
	public void supprimerSaisie(String identifiant) {
		if (StringUtils.isNotBlank(identifiant)) {
			saisieRepository.deleteById(new ObjectId(identifiant));
		}
	}

}
