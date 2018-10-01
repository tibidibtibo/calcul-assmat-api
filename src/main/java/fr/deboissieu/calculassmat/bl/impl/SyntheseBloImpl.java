package fr.deboissieu.calculassmat.bl.impl;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ParametrageBlo;
import fr.deboissieu.calculassmat.bl.SyntheseBlo;
import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.commons.exceptions.ValidationExceptionsEnum;
import fr.deboissieu.calculassmat.commons.mathsutils.MathsUtils;
import fr.deboissieu.calculassmat.dl.ParamEmployeRepository;
import fr.deboissieu.calculassmat.dl.ParamEnfantRepository;
import fr.deboissieu.calculassmat.model.parametrage.HorairesEcole;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant.TypeGardeEnum;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.NombreHeures;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@Component
public class SyntheseBloImpl implements SyntheseBlo {

	private static final Logger logger = LogManager.getLogger(SyntheseBloImpl.class);

	@Resource
	ParametrageBlo parametrageBlo;

	@Resource
	ParamEnfantRepository paramEnfantRepository;

	@Resource
	ParamEmployeRepository paramEmployeRepository;

	@Override
	public SyntheseGarde calculerFraisMensuels(Collection<SaisieJournaliere> donneesSaisies, int mois,
			int annee) {

		SyntheseGarde synthese = new SyntheseGarde(mois, annee);

		ParametrageEmploye paramAssmat = paramEmployeRepository.findByNom("maternelle");
		Map<String, ParametrageEnfant> mapParamEnfants = ParamEnfantRepository
				.findParamsEnfants(paramEnfantRepository.findAll());

		if (CollectionUtils.isNotEmpty(donneesSaisies) && paramAssmat != null && MapUtils.isNotEmpty(mapParamEnfants)) {

			NombreHeures nbHeures = calculerNbHeures(donneesSaisies, mapParamEnfants);

			synthese.setNbJoursTravailles(calculerJoursTravailles(donneesSaisies));
			synthese.setNbHeuresReelles(nbHeures.getHeuresReelles());
			synthese.setNbHeuresNormalesContrat(nbHeures.getHeuresNormalesContrat());
			synthese.setNbHeuresComplementaires(nbHeures.getHeuresComplementaires());
			synthese.setSalaireHoraireNetHeureNormale(paramAssmat.getSalaireNetHoraire());

			Double salaireMensualise = calculerSalaireNetSansConges(mapParamEnfants, paramAssmat, nbHeures);
			synthese.setSalaireNetHeuresNormales(salaireMensualise);

			Double salaireHeuresComplementaires = nbHeures.getHeuresComplementaires()
					* paramAssmat.getSalaireNetHoraire();
			synthese.setSalaireNetHeuresComplementaires(salaireHeuresComplementaires);

			Double congesPayes = MathsUtils.roundTo2Digits(
					(salaireMensualise + salaireHeuresComplementaires) * paramAssmat.getTauxCongesPayes());
			synthese.setCongesPayes(congesPayes);

			Double salaireNetTotal = MathsUtils
					.roundTo2Digits(salaireMensualise + salaireHeuresComplementaires + congesPayes);
			synthese.setSalaireNetTotal(salaireNetTotal);

			synthese.setIndemnitesEntretien(calculerIndemnitesEntretien(donneesSaisies, paramAssmat));

			synthese.setIndemnitesRepas(calculerIndemnitesRepas(donneesSaisies, paramAssmat));

			synthese.setIndemnitesKm(calculerIndemnitesKm(donneesSaisies, paramAssmat, mapParamEnfants));

			synthese.calculerPaiementMensuel();

			return synthese;
		}
		throw new ValidationException(ValidationExceptionsEnum.V101.getMessage());
	}

	private Double calculerIndemnitesKm(Collection<SaisieJournaliere> donneesSaisies,
			ParametrageEmploye employe, Map<String, ParametrageEnfant> mapParamEnfants) {
		Double fraisKm = 0d;

		if (CollectionUtils.isNotEmpty(donneesSaisies)) {

			for (SaisieJournaliere saisie : donneesSaisies) {
				ParametrageEnfant paramEnfant = mapParamEnfants.get(saisie.getPrenom());
				if (paramEnfant != null && paramEnfant.getArEcoleKm() != null && saisie.getNbArEcole() != null) {
					fraisKm += saisie.getNbArEcole() * paramEnfant.getArEcoleKm() * employe.getIndemnitesKm();
				}
				if (saisie.getAutresDeplacementKm() != null) {
					fraisKm += saisie.getAutresDeplacementKm() * employe.getIndemnitesKm();
				}
			}
		}

		return MathsUtils.roundTo2Digits(fraisKm);
	}

	private Double calculerIndemnitesRepas(Collection<SaisieJournaliere> donneesSaisies,
			ParametrageEmploye employe) {

		Double fraisRepas = 0d;

		if (CollectionUtils.isNotEmpty(donneesSaisies)) {

			for (SaisieJournaliere saisie : donneesSaisies) {
				if (saisie.getNbDejeuners() != null) {
					fraisRepas += saisie.getNbDejeuners() * employe.getFraisDejeuner();
				}
				if (saisie.getNbGouters() != null) {
					fraisRepas += saisie.getNbGouters() * employe.getFraisGouter();
				}
			}
		}

		return MathsUtils.roundTo2Digits(fraisRepas);
	}

	private Double calculerIndemnitesEntretien(Collection<SaisieJournaliere> donneesSaisies,
			ParametrageEmploye employe) {

		Integer nbJours = 0;

		if (CollectionUtils.isNotEmpty(donneesSaisies)) {
			nbJours = donneesSaisies.size();
		}

		return MathsUtils.roundTo2Digits(nbJours * employe.getIndemnitesEntretien());
	}

	private Double calculerSalaireNetSansConges(Map<String, ParametrageEnfant> mapParamEnfants,
			ParametrageEmploye employe,
			NombreHeures nbHeures) {

		Double salaireNet = 0d;

		// Salaire mensualisé
		for (ParametrageEnfant paramEnfant : mapParamEnfants.values()) {
			salaireNet += paramEnfant.getSalaireNetMensualise();
		}

		return MathsUtils.roundTo2Digits(salaireNet);

	}

	private NombreHeures calculerNbHeures(Collection<SaisieJournaliere> donneesSaisies,
			Map<String, ParametrageEnfant> mapParamEnfants) {

		NombreHeures nbHeures = new NombreHeures();

		if (CollectionUtils.isNotEmpty(donneesSaisies)) {

			for (SaisieJournaliere saisie : donneesSaisies) {

				ParametrageEnfant paramEnfant = mapParamEnfants.get(saisie.getPrenom());

				LocalTime heureArrivee = DateUtils.toLocalTime(saisie.getHeureArrivee());
				LocalTime heureDepart = DateUtils.toLocalTime(saisie.getHeureDepart());

				// Récupération heures normales du jour
				int jourSemaine = fr.deboissieu.calculassmat.commons.dateUtils.DateUtils
						.getDayOfWeek(saisie.getDateSaisie());
				Double heuresNormalesRef = paramEnfant.getHeuresNormales(jourSemaine);

				switch (TypeGardeEnum.valueOf(paramEnfant.getTypeGarde())) {
				case PERISCOLAIRE:

					HorairesEcole horairesJournaliers = paramEnfant.getHorairesEcole(jourSemaine);
					Double tempsJour = 0d;
					if (heureArrivee != null) {
						Double temps1 = DateUtils.diff(heureArrivee,
								horairesJournaliers.getHorairesJournaliersEcole().getArriveeMatin());
						tempsJour += temps1;
					}
					if (heureDepart != null) {
						Double temps2 = DateUtils
								.diff(horairesJournaliers.getHorairesJournaliersEcole().getDepartAprem(),
										heureDepart);
						tempsJour += temps2;
					}
					// FIXME TDU : temps midi ?

					nbHeures.addHeuresReelles(tempsJour);
					nbHeures.addHeuresNormalesContrat(heuresNormalesRef);

					Double differencePeriscolaire = tempsJour - heuresNormalesRef;
					if (differencePeriscolaire > 0) {
						nbHeures.addHeuresComplementaires(differencePeriscolaire);
					}

					break;

				case TEMPS_PLEIN:
					if (heureArrivee != null && heureDepart != null) {

						Double heuresGarde = DateUtils.diff(heureArrivee, heureDepart);

						nbHeures.addHeuresReelles(heuresGarde);
						nbHeures.addHeuresNormalesContrat(heuresNormalesRef);

						Double difference = heuresGarde - heuresNormalesRef;
						if (difference > 0) {
							nbHeures.addHeuresComplementaires(difference);
						}

					} else {
						logger.error("Impossible de calculer les horaires de {} le {}.", saisie.getPrenom(),
								saisie.getDateSaisie());
					}
					break;
				}

			}
		}

		nbHeures.roundValues();

		return nbHeures;
	}

	private Integer calculerJoursTravailles(Collection<SaisieJournaliere> donneesSaisies) {
		Map<String, SaisieJournaliere> mapJoursTravailles = new HashMap<>();
		for (SaisieJournaliere saisie : donneesSaisies) {
			String key = DateUtils.formatDate(saisie.getDateSaisie(), "dd-MM-yyyy", TimeZone.getDefault());
			mapJoursTravailles.put(key, saisie);
		}
		if (MapUtils.isNotEmpty(mapJoursTravailles)) {
			return mapJoursTravailles.values().size();
		}
		return null;
	}

}
