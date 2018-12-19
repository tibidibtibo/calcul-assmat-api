package fr.deboissieu.calculassmat.bl.saisie.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.parametrage.ParametrageBlo;
import fr.deboissieu.calculassmat.bl.saisie.ExcelFileBlo;
import fr.deboissieu.calculassmat.bl.saisie.SaisieBlo;
import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.dl.SaisieRepository;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.Saisie;
import fr.deboissieu.calculassmat.model.saisie.SaisieEnfantDto;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

@Component
public class SaisieBloImpl implements SaisieBlo {

	@Resource
	SaisieRepository saisieRepository;

	@Resource
	ExcelFileBlo excelFileBlo;

	@Resource
	ParametrageBlo parametrageBlo;

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
		return SaisieEnfantDto.toSaisieEnfantDto(saisieRepository.findSaisieBetween(startDate, stopDate));

	}

	@Override
	public void importerFichierSaisie(Integer numeroMois, Integer numeroAnnee, String fileName)
			throws InvalidFormatException, IOException {

		// Ouverture du fichier en tant que Workbook et extraction des saisies
		Workbook workbook = excelFileBlo.openWorkbook(fileName);

		try {

			Collection<SaisieJournaliere> saisiesJournalieres = excelFileBlo.extractDataFromWorkbook(workbook,
					numeroMois,
					numeroAnnee);

			// Consolidation des paramètrages et conversion en saisie entités
			Map<String, ParametrageEnfant> mapParamEnfants = parametrageBlo.findAllParamsEnfants();
			Collection<ParametrageEmploye> paramsEmployes = parametrageBlo.findAllEmployes();
			Collection<Saisie> saisies = consoliderSaisiesJournalieres(saisiesJournalieres, mapParamEnfants,
					paramsEmployes);

			// Save
			saisieRepository.saveAll(saisies);
		} catch (Exception e) {
			throw e;
		} finally {
			// Close workbook
			workbook.close();
		}

	}

	private Collection<Saisie> consoliderSaisiesJournalieres(Collection<SaisieJournaliere> saisiesJournalieres,
			Map<String, ParametrageEnfant> mapParamEnfants, Collection<ParametrageEmploye> paramsEmployes) {
		return saisiesJournalieres.stream()
				.map(saisieJourn -> {

					Saisie saisie = new Saisie();

					// Param enfant
					ParametrageEnfant paramEnfant = mapParamEnfants.get(saisieJourn.getEnfant());
					saisie.setEnfantId(paramEnfant.get_id());

					// Param employé
					ParametrageEmploye paramEmploye = paramsEmployes.stream()
							.filter(param -> {
								return StringUtils.equals(param.getNom(), saisieJourn.getEmploye());
							})
							.findFirst().get();
					saisie.setEmployeId(paramEmploye.get_id());

					// Dates / heures
					saisie.setDateSaisie(saisieJourn.getDateSaisie());
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

					// Autres données
					saisie.setAutresDeplacementKm(saisieJourn.getAutresDeplacementKm());
					saisie.setNbArEcole(saisieJourn.getNbArEcole());
					saisie.setNbDejeuners(saisieJourn.getNbDejeuners());
					saisie.setNbGouters(saisieJourn.getNbGouters());
					return saisie;
				}).collect(Collectors.toList());
	}

}
