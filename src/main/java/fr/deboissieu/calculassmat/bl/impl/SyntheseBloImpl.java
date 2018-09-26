package fr.deboissieu.calculassmat.bl.impl;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ParametrageBlo;
import fr.deboissieu.calculassmat.bl.SyntheseBlo;
import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.model.parametrage.HorairesEcole;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant.TypeGardeEnum;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageGarde;
import fr.deboissieu.calculassmat.model.saisie.HeuresPersonnelles;
import fr.deboissieu.calculassmat.model.saisie.HorairesPersonnelsEtFrais;
import fr.deboissieu.calculassmat.model.synthese.NombreHeures;
import fr.deboissieu.calculassmat.model.synthese.NombreJoursTravailles;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@Component
public class SyntheseBloImpl implements SyntheseBlo {

	private static final Logger logger = LogManager.getLogger(SyntheseBloImpl.class);

	@Resource
	ParametrageBlo parametrageBlo;

	@Override
	public SyntheseGarde calculerFraisMensuels(Map<String, HorairesPersonnelsEtFrais> mapHorairesParDate, int mois,
			int annee) {

		SyntheseGarde synthese = new SyntheseGarde(mois, annee);

		ParametrageGarde paramGarde = parametrageBlo.getParametrageGarde();

		NombreJoursTravailles nbJoursTravailles = calculerJoursTravailles(mapHorairesParDate, paramGarde);

		NombreHeures nbHeures = calculerNbHeures(mapHorairesParDate, paramGarde);

		// FIXME : temporaire
		Double arEcole = paramGarde.getEnfant("Louise").getArEcoleKm();

		for (ParametrageEmploye employe : paramGarde.getEmployes()) {

			synthese.setNbJoursTravailles(nbJoursTravailles.getNbJoursTotal());
			synthese.setNbHeuresNormalesReelles(nbHeures.getHeuresNormalesReelles());
			synthese.setNbHeuresNormalesContrat(nbHeures.getHeuresNormalesContrat());
			synthese.setNbHeuresComplementaires(nbHeures.getHeuresComplementaires());
			synthese.setSalaireHoraireNetHeureNormale(employe.getSalaireNetHoraire());

			Double salaireNetSansConges = calculerSalaireNetSansConges(paramGarde.getEnfants(), employe, nbHeures);
			synthese.setSalaireNetTotalSansConges(salaireNetSansConges);

			Double congesPayes = salaireNetSansConges * employe.getTauxCongesPayes();
			synthese.setCongesPayes(congesPayes);
			synthese.setSalaireNetTotal(salaireNetSansConges + congesPayes);

			synthese.setIndemnitesEntretien(calculerIndemnitesEntretien(mapHorairesParDate, employe));

			synthese.setIndemnitesRepas(calculerIndemnitesRepas(mapHorairesParDate, employe));

			synthese.setIndemnitesKm(calculerIndemnitesKm(mapHorairesParDate, employe, arEcole));
		}

		return synthese;
	}

	private Double calculerIndemnitesKm(Map<String, HorairesPersonnelsEtFrais> mapHorairesParDate,
			ParametrageEmploye employe, Double arEcole) {
		Double fraisKm = 0d;

		// FIXME : pb de km ecole-nounou - comme pour les repas, distinguer les frais
		// par enfant
		for (HorairesPersonnelsEtFrais horaires : mapHorairesParDate.values()) {
			if (horaires != null && horaires.getFraisJournaliers() != null) {
				if (horaires.getFraisJournaliers().getArEcole() != null) {
					fraisKm += horaires.getFraisJournaliers().getArEcole() * employe.getIndemnitesKm() * arEcole;
				}
				if (horaires.getFraisJournaliers().getAutresDeplacementKm() != null) {
					fraisKm += horaires.getFraisJournaliers().getAutresDeplacementKm() * employe.getIndemnitesKm();
				}
			}
		}

		return fraisKm;
	}

	private Double calculerIndemnitesRepas(Map<String, HorairesPersonnelsEtFrais> mapHorairesParDate,
			ParametrageEmploye employe) {

		Double fraisRepas = 0d;

		for (HorairesPersonnelsEtFrais horaires : mapHorairesParDate.values()) {
			if (horaires != null && horaires.getFraisJournaliers() != null) {
				if (horaires.getFraisJournaliers().getNbDejeuners() != null) {
					fraisRepas += horaires.getFraisJournaliers().getNbDejeuners() * employe.getFraisDejeuner();
				}
				if (horaires.getFraisJournaliers().getNbGouters() != null) {
					fraisRepas += horaires.getFraisJournaliers().getNbGouters() * employe.getFraisGouter();
				}

			}
		}

		return fraisRepas;
	}

	private Double calculerIndemnitesEntretien(Map<String, HorairesPersonnelsEtFrais> mapHorairesParDate,
			ParametrageEmploye employe) {

		Integer nbJours = 0;

		for (HorairesPersonnelsEtFrais horaires : mapHorairesParDate.values()) {
			for (HeuresPersonnelles heure : horaires.getHeuresPersonnelles()) {
				nbJours += 1;
			}
		}

		return nbJours * employe.getIndemnitesEntretien();
	}

	private Double calculerSalaireNetSansConges(Collection<ParametrageEnfant> enfants, ParametrageEmploye employe,
			NombreHeures nbHeures) {

		Double salaireNet = 0d;

		// Salaire mensualisé
		for (ParametrageEnfant paramEnfant : enfants) {
			salaireNet += paramEnfant.getSalaireNetMensualise();
		}

		// Heures complémentaires
		salaireNet += nbHeures.getHeuresComplementaires() * employe.getSalaireNetHoraire();

		// Congés payés
		Double congesPayes = salaireNet * employe.getTauxCongesPayes();

		return salaireNet + congesPayes;

	}

	private NombreHeures calculerNbHeures(Map<String, HorairesPersonnelsEtFrais> donneesAssemblees,
			ParametrageGarde paramGarde) {

		NombreHeures nbHeures = new NombreHeures();

		if (donneesAssemblees != null && MapUtils.isNotEmpty(donneesAssemblees)) {

			for (Map.Entry<String, HorairesPersonnelsEtFrais> entry : donneesAssemblees.entrySet()) {

				if (CollectionUtils.isNotEmpty(entry.getValue().getHeuresPersonnelles())) {

					for (HeuresPersonnelles heures : entry.getValue().getHeuresPersonnelles()) {

						ParametrageEnfant paramEnfant = parametrageBlo.getParamEnfant(paramGarde, heures.getPrenom());

						LocalTime heureArrivee = DateUtils.toLocalTime(heures.getHeureArrivee());
						LocalTime heureDepart = DateUtils.toLocalTime(heures.getHeureDepart());

						// Récupération heures normales du jour
						int jourSemaine = fr.deboissieu.calculassmat.commons.dateUtils.DateUtils
								.getDayOfWeek(entry.getKey());
						Double heuresNormalesRef = paramEnfant.getHeuresNormales(jourSemaine);

						switch (TypeGardeEnum.valueOf(paramEnfant.getTypeGarde())) {
						case PERISCOLAIRE:

							HorairesEcole horairesJournaliers = paramEnfant.getHorairesEcole(jourSemaine);
							Double tempsJour = 0d;
							if (heureArrivee != null) {
								tempsJour += DateUtils.diff(heureArrivee,
										horairesJournaliers.getHorairesJournaliersEcole().getArriveeMatin());
							}
							if (heureDepart != null) {
								tempsJour += DateUtils
										.diff(horairesJournaliers.getHorairesJournaliersEcole().getDepartAprem(),
												heureDepart);
							}
							// TODO TDU : temps midi ?

							nbHeures.addHeuresNormalesReelles(tempsJour);
							nbHeures.addHeuresNormalesContrat(heuresNormalesRef);

							Double differencePeriscolaire = tempsJour - heuresNormalesRef;
							if (differencePeriscolaire > 0) {
								nbHeures.addHeuresComplementaires(differencePeriscolaire);
							}

							break;

						case TEMPS_PLEIN:
							if (heureArrivee != null && heureDepart != null) {

								Double heuresGarde = DateUtils.diff(heureArrivee, heureDepart);

								nbHeures.addHeuresNormalesReelles(heuresGarde);
								nbHeures.addHeuresNormalesContrat(heuresNormalesRef);

								Double difference = heuresGarde - heuresNormalesRef;
								if (difference > 0) {
									nbHeures.addHeuresComplementaires(difference);
								}

							} else {
								logger.error("Impossible de calculer les horaires de {} le {}.", heures.getPrenom(),
										entry.getKey());
							}
							break;
						}

					}
				}
			}
		}
		return nbHeures;
	}

	private NombreJoursTravailles calculerJoursTravailles(Map<String, HorairesPersonnelsEtFrais> donneesAssemblees,
			ParametrageGarde paramGarde) {
		NombreJoursTravailles nbJourTravailles = new NombreJoursTravailles();
		nbJourTravailles.setNbJoursTotal(donneesAssemblees.size());
		nbJourTravailles.setNbJoursParPersonne(calculerJoursTravaillesParPersonne(donneesAssemblees, paramGarde));
		return nbJourTravailles;
	}

	private Map<String, Integer> calculerJoursTravaillesParPersonne(
			Map<String, HorairesPersonnelsEtFrais> donneesAssemblees, ParametrageGarde paramGarde) {
		Map<String, Integer> mapJoursParPersonne = new HashMap<>();

		if (donneesAssemblees != null && MapUtils.isNotEmpty(donneesAssemblees)) {
			for (Map.Entry<String, HorairesPersonnelsEtFrais> entry : donneesAssemblees.entrySet()) {
				for (String prenom : parametrageBlo.getListeNomsEnfants(paramGarde)) {
					HeuresPersonnelles horaire = IterableUtils.find(entry.getValue().getHeuresPersonnelles(),
							heures -> prenom.equals(heures.getPrenom()));
					completerMapJours(mapJoursParPersonne, prenom, horaire);

				}
			}
		}
		return mapJoursParPersonne;
	}

	private void completerMapJours(Map<String, Integer> mapJoursParPersonne, String prenom,
			HeuresPersonnelles horaire) {
		if (horaire != null) {
			if (mapJoursParPersonne.get(prenom) != null) {
				mapJoursParPersonne.put(prenom, mapJoursParPersonne.get(prenom) + 1);
			} else {
				mapJoursParPersonne.put(prenom, 1);
			}
		}
	}

}
