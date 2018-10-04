package fr.deboissieu.calculassmat.bl.impl;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ParametrageBlo;
import fr.deboissieu.calculassmat.bl.SyntheseBlo;
import fr.deboissieu.calculassmat.bl.ValidationBlo;
import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.commons.exceptions.TechniqueExceptionEnum;
import fr.deboissieu.calculassmat.commons.mathsutils.MathsUtils;
import fr.deboissieu.calculassmat.model.parametrage.HorairesEcole;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant.TypeGardeEnum;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.Indemnites;
import fr.deboissieu.calculassmat.model.synthese.NombreHeures;
import fr.deboissieu.calculassmat.model.synthese.Salaire;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@Component
public class SyntheseBloImpl implements SyntheseBlo {

	private static final Logger logger = LogManager.getLogger(SyntheseBloImpl.class);

	@Resource
	ParametrageBlo parametrageBlo;

	@Resource
	ValidationBlo validationBlo;

	@Override
	public SyntheseGarde calculerFraisMensuels(Collection<SaisieJournaliere> donneesSaisies, int mois,
			int annee, String nomAssMat) {

		SyntheseGarde synthese = new SyntheseGarde(mois, annee);

		ParametrageEmploye paramAssmat = parametrageBlo.findEmployeParNom(nomAssMat);
		Map<String, ParametrageEnfant> mapParamEnfants = parametrageBlo.findAllParamsEnfants();

		validationBlo.validerAvantCalcul(donneesSaisies, paramAssmat, mapParamEnfants);

		synthese.setNbJoursTravailles(calculerJoursTravailles(donneesSaisies));

		// Nombre d'heures
		NombreHeures nbHeures = calculerNbHeures(donneesSaisies, mapParamEnfants);
		synthese.setNombreHeures(nbHeures);

		// Salaire
		Salaire salaire = calculerSalaire(paramAssmat, mapParamEnfants, nbHeures);
		synthese.setSalaire(salaire);

		// Indemnites
		Indemnites indemnites = calculerIndemnites(donneesSaisies, paramAssmat, mapParamEnfants);
		synthese.setIndemnites(indemnites);

		// Calcul du paiement total
		synthese.calculerPaiementMensuel();

		return synthese;

	}

	private Indemnites calculerIndemnites(Collection<SaisieJournaliere> donneesSaisies, ParametrageEmploye paramAssmat,
			Map<String, ParametrageEnfant> mapParamEnfants) {
		Indemnites indemnites = new Indemnites();
		indemnites.setIndemnitesEntretien(calculerIndemnitesEntretien(donneesSaisies, paramAssmat));
		indemnites.setIndemnitesRepas(calculerIndemnitesRepas(donneesSaisies, paramAssmat));
		indemnites.setIndemnitesKm(calculerIndemnitesKm(donneesSaisies, paramAssmat, mapParamEnfants));
		return indemnites;
	}

	private Salaire calculerSalaire(ParametrageEmploye paramAssmat, Map<String, ParametrageEnfant> mapParamEnfants,
			NombreHeures nbHeures) {

		Salaire salaire = new Salaire();
		salaire.setTauxHoraireNetHeureNormale(paramAssmat.getTauxHoraireNormalNet());
		salaire.setTauxHoraireNetHeureComplementaire(paramAssmat.getTauxHoraireComplementaireNet());
		Double salaireMensualise = calculerSalaireNetMensualise(mapParamEnfants, paramAssmat, nbHeures);
		salaire.setSalaireNetMensualise(salaireMensualise);

		Double salaireHeuresComplementaires = nbHeures.getHeuresComplementaires()
				* paramAssmat.getTauxHoraireComplementaireNet();
		salaire.setSalaireNetHeuresComplementaires(salaireHeuresComplementaires);

		Double congesPayes = MathsUtils.roundTo2Digits(
				(salaireMensualise + salaireHeuresComplementaires) * paramAssmat.getTauxCongesPayes());
		salaire.setCongesPayes(congesPayes);

		Double salaireNetTotal = MathsUtils
				.roundTo2Digits(salaireMensualise + salaireHeuresComplementaires + congesPayes);
		salaire.setSalaireNetTotal(salaireNetTotal);

		return salaire;
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

	private Double calculerSalaireNetMensualise(Map<String, ParametrageEnfant> mapParamEnfants,
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

					if (horairesJournaliers.jourSansEcole()) {
						// Pas d'école ce jour = temps plein
						if (heureArrivee != null && heureDepart != null) {
							tempsJour += DateUtils.diff(heureArrivee, heureDepart);
						}
					} else {
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
					}
					// FIXME TDU : temps midi ?

					nbHeures.addHeuresReelles(tempsJour);
					nbHeures.addHeuresNormalesReelles(heuresNormalesRef);
					Double heuresComp = calculerHeuresComplementaires(tempsJour, heuresNormalesRef);
					nbHeures.addHeuresComplementaires(heuresComp);

					break;

				case TEMPS_PLEIN:
					if (heureArrivee != null && heureDepart != null) {
						Double heuresGarde = DateUtils.diff(heureArrivee, heureDepart);
						nbHeures.addHeuresReelles(heuresGarde);
						nbHeures.addHeuresNormalesReelles(heuresNormalesRef);
						nbHeures.addHeuresComplementaires(
								calculerHeuresComplementaires(heuresGarde, heuresNormalesRef));
					} else {
						logger.error(TechniqueExceptionEnum.T001.getMessage(), saisie.getPrenom(),
								saisie.getDateSaisie());
					}
					break;
				}

			}
		}

		nbHeures.setHeuresNormalesMensualisees(mapParamEnfants);

		nbHeures.roundValues();

		return nbHeures;
	}

	private Double calculerHeuresComplementaires(Double tempsJour, Double heuresNormalesRef) {
		if (tempsJour != null) {
			if (heuresNormalesRef != null) {
				Double difference = tempsJour - heuresNormalesRef;
				return difference > 0 ? difference : 0d;
			} else {
				return tempsJour;
			}
		}
		return 0d;
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
