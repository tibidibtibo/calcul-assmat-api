package fr.deboissieu.calculassmat.bl.impl;

import java.time.LocalTime;
import java.util.ArrayList;
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
	public Collection<SyntheseGarde> calculerFraisMensuels(Collection<SaisieJournaliere> donneesSaisies, int mois,
			int annee, Map<String, ParametrageEnfant> mapParamEnfants) {

		Map<String, Collection<SaisieJournaliere>> saisieParEmploye = mapperParEmploye(donneesSaisies);
		Map<ParametrageEmploye, Collection<SaisieJournaliere>> mapEmployeSaisie = consoliderParamEmploye(
				saisieParEmploye);

		Collection<SyntheseGarde> syntheses = new ArrayList<>();

		for (Map.Entry<ParametrageEmploye, Collection<SaisieJournaliere>> entry : mapEmployeSaisie.entrySet()) {
			Collection<SaisieJournaliere> saisieEmploye = entry.getValue();
			ParametrageEmploye employe = entry.getKey();
			SyntheseGarde synthese = new SyntheseGarde(mois, annee);

			validationBlo.validerAvantCalcul(saisieEmploye, mapParamEnfants);

			synthese.setNbJoursTravailles(calculerJoursTravailles(saisieEmploye));

			// Nombre d'heures
			NombreHeures nbHeures = calculerNbHeures(saisieEmploye, mapParamEnfants);
			synthese.setNombreHeures(nbHeures);

			// Salaire
			Salaire salaire = calculerSalaire(employe, mapParamEnfants, nbHeures);
			synthese.setSalaire(salaire);

			// Indemnites
			Indemnites indemnites = calculerIndemnites(saisieEmploye, employe, nbHeures, mapParamEnfants);
			synthese.setIndemnites(indemnites);

			// Calcul du paiement total
			synthese.calculerPaiementMensuel();

			syntheses.add(synthese);
		}

		return syntheses;

	}

	private Map<ParametrageEmploye, Collection<SaisieJournaliere>> consoliderParamEmploye(
			Map<String, Collection<SaisieJournaliere>> saisieParEmploye) {
		Map<ParametrageEmploye, Collection<SaisieJournaliere>> mapEmployeSaisie = new HashMap<>();
		if (MapUtils.isNotEmpty(saisieParEmploye)) {
			for (Map.Entry<String, Collection<SaisieJournaliere>> entry : saisieParEmploye.entrySet()) {
				ParametrageEmploye parametrageEmploye = parametrageBlo.findEmployeParNom(entry.getKey());
				if (parametrageEmploye != null) {
					mapEmployeSaisie.put(parametrageEmploye, entry.getValue());
				}
			}
		}
		return mapEmployeSaisie;
	}

	private Map<String, Collection<SaisieJournaliere>> mapperParEmploye(Collection<SaisieJournaliere> donneesSaisies) {
		Map<String, Collection<SaisieJournaliere>> saisieParEmploye = new HashMap<>();
		if (CollectionUtils.isNotEmpty(donneesSaisies)) {
			for (SaisieJournaliere saisie : donneesSaisies) {
				if (saisieParEmploye.get(saisie.getEmploye()) != null) {
					saisieParEmploye.get(saisie.getEmploye()).add(saisie);
				} else {
					Collection<SaisieJournaliere> saisies = new ArrayList<>();
					saisies.add(saisie);
					saisieParEmploye.put(saisie.getEmploye(), saisies);
				}
			}
		}
		return saisieParEmploye;
	}

	private Indemnites calculerIndemnites(Collection<SaisieJournaliere> donneesSaisies, ParametrageEmploye employe,
			NombreHeures nbHeures,
			Map<String, ParametrageEnfant> mapParamEnfants) {
		Indemnites indemnites = new Indemnites();
		indemnites.setIndemnitesEntretien(calculerIndemnitesEntretien(employe, nbHeures));
		indemnites.setIndemnitesRepas(calculerIndemnitesRepas(donneesSaisies, employe));
		indemnites.setIndemnitesKm(calculerIndemnitesKm(donneesSaisies, mapParamEnfants, employe));
		return indemnites;
	}

	private Salaire calculerSalaire(ParametrageEmploye paramAssmat, Map<String, ParametrageEnfant> mapParamEnfants,
			NombreHeures nbHeures) {

		Salaire salaire = new Salaire();
		salaire.setTauxHoraireNetHeureNormale(paramAssmat.getTauxHoraireNormalNet());
		salaire.setTauxHoraireNetHeureComplementaire(paramAssmat.getTauxHoraireComplementaireNet());
		Double salaireMensualise = calculerSalaireNetMensualise(mapParamEnfants);
		salaire.setSalaireNetMensualise(salaireMensualise);

		Double salaireHeuresComplementaires = MathsUtils.roundTo2Digits(nbHeures.getHeuresComplementaires()
				* paramAssmat.getTauxHoraireComplementaireNet());
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
			Map<String, ParametrageEnfant> mapParamEnfants, ParametrageEmploye employe) {
		Double fraisKm = 0d;

		if (CollectionUtils.isNotEmpty(donneesSaisies)) {
			for (SaisieJournaliere saisie : donneesSaisies) {
				ParametrageEnfant paramEnfant = mapParamEnfants.get(saisie.getEnfant());
				if (paramEnfant != null && paramEnfant.getArEcoleKm() != null && saisie.getNbArEcole() != null) {
					fraisKm += saisie.getNbArEcole() * paramEnfant.getArEcoleKm() *
							employe.getIndemnitesKm();
				}
				if (saisie.getAutresDeplacementKm() != null) {
					fraisKm += saisie.getAutresDeplacementKm() * employe.getIndemnitesKm();
				}
			}
		}

		return MathsUtils.roundTo2Digits(fraisKm);
	}

	private Double calculerIndemnitesRepas(Collection<SaisieJournaliere> donneesSaisies, ParametrageEmploye employe) {

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

	private Double calculerIndemnitesEntretien(ParametrageEmploye employe, NombreHeures nbHeures) {

		Integer nbJourInf = 0;
		Integer nbJourSup = 0;

		Map<String, Double> nbHeuresJour = nbHeures.getNbreHeuresJour();
		for (Map.Entry<String, Double> entry : nbHeuresJour.entrySet()) {
			if (entry.getValue() < employe.getIndemnitesEntretien().getBorne()) {
				nbJourInf += 1;
			} else {
				nbJourSup += 1;
			}
		}

		return MathsUtils.roundTo2Digits(nbJourInf * employe.getIndemnitesEntretien().getIndemniteInf()
				+ nbJourSup * employe.getIndemnitesEntretien().getIndemniteSup());
	}

	private Double calculerSalaireNetMensualise(Map<String, ParametrageEnfant> mapParamEnfants) {

		Double salaireNet = 0d;
		for (ParametrageEnfant paramEnfant : mapParamEnfants.values()) {
			salaireNet += paramEnfant.getSalaireNetMensualise();
		}
		return MathsUtils.roundTo2Digits(salaireNet);

	}

	public NombreHeures calculerNbHeures(Collection<SaisieJournaliere> donneesSaisies,
			Map<String, ParametrageEnfant> mapParamEnfants) {

		NombreHeures nbHeures = new NombreHeures();
		nbHeures.setHeuresNormalesMensualisees(mapParamEnfants);

		if (CollectionUtils.isNotEmpty(donneesSaisies)) {

			for (SaisieJournaliere saisie : donneesSaisies) {

				Double tempsJour = 0d;

				LocalTime heureArrivee = DateUtils.toLocalTime(saisie.getHeureArrivee());
				LocalTime heureDepart = DateUtils.toLocalTime(saisie.getHeureDepart());

				ParametrageEnfant paramEnfant = mapParamEnfants.get(saisie.getEnfant());
				// Récupération heures normales du jour
				int jourSemaine = fr.deboissieu.calculassmat.commons.dateUtils.DateUtils
						.getDayOfWeek(saisie.getDateSaisie());
				Double heuresNormalesRef = paramEnfant.getHeuresNormales(jourSemaine);

				if (TypeGardeEnum.valueOf(paramEnfant.getTypeGarde()).equals(TypeGardeEnum.PERISCOLAIRE)) {
					tempsJour += calculTempsPeriscolaire(nbHeures, saisie, paramEnfant, heureArrivee,
							heureDepart, jourSemaine,
							heuresNormalesRef);
				} else if (TypeGardeEnum.valueOf(paramEnfant.getTypeGarde()).equals(TypeGardeEnum.TEMPS_PLEIN)) {
					tempsJour += calculerTempsPlein(nbHeures, saisie, heureArrivee, heureDepart,
							heuresNormalesRef);
				}

				nbHeures.updateNbHeuresJour(saisie, tempsJour);

			}
		}

		// Post traitement
		nbHeures.roundValues();

		return nbHeures;
	}

	private Double calculerTempsPlein(NombreHeures nbHeures, SaisieJournaliere saisie, LocalTime heureArrivee,
			LocalTime heureDepart, Double heuresNormalesRef) {
		Double heuresGarde = 0d;
		if (heureArrivee != null && heureDepart != null) {
			heuresGarde = DateUtils.diff(heureArrivee, heureDepart);
			nbHeures.addHeuresReelles(heuresGarde);
			nbHeures.addHeuresNormalesReelles(heuresNormalesRef);
			nbHeures.addHeuresComplementaires(
					calculerHeuresComplementaires(heuresGarde, heuresNormalesRef));
		} else {
			logger.error(TechniqueExceptionEnum.T001.getMessage(), saisie.getEnfant(),
					saisie.getDateSaisie());
		}
		return heuresGarde;
	}

	private Double calculTempsPeriscolaire(NombreHeures nbHeures, SaisieJournaliere saisie,
			ParametrageEnfant paramEnfant,
			LocalTime heureArrivee, LocalTime heureDepart, int jourSemaine, Double heuresNormalesRef) {
		HorairesEcole horairesJournaliers = paramEnfant.getHorairesEcole(jourSemaine);

		Double tempsJour = 0d;

		// Calcul temps de garde matin/soir
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

		// Calcul du temps de garde midi
		if (!horairesJournaliers.jourSansEcole() && saisie.getNbDejeuners() != null
				&& saisie.getNbDejeuners() > 0) {
			Double tempMidi = DateUtils
					.diff(horairesJournaliers.getHorairesJournaliersEcole().getDepartMatin(),
							horairesJournaliers.getHorairesJournaliersEcole().getArriveeAprem());
			tempsJour += tempMidi;
		}

		nbHeures.addHeuresReelles(tempsJour);
		nbHeures.addHeuresNormalesReelles(heuresNormalesRef);
		nbHeures.addHeuresComplementaires(calculerHeuresComplementaires(tempsJour, heuresNormalesRef));

		return tempsJour;
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
			String key = DateUtils.formatDate(saisie.getDateSaisie(), DateUtils.DATE_FORMAT_PATTERN,
					TimeZone.getDefault());
			mapJoursTravailles.put(key, saisie);
		}
		if (MapUtils.isNotEmpty(mapJoursTravailles)) {
			return mapJoursTravailles.values().size();
		}
		return null;
	}

}
