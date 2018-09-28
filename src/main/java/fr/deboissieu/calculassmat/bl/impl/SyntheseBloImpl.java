package fr.deboissieu.calculassmat.bl.impl;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.NombreHeures;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@Component
public class SyntheseBloImpl implements SyntheseBlo {

	private static final Logger logger = LogManager.getLogger(SyntheseBloImpl.class);

	@Resource
	ParametrageBlo parametrageBlo;

	@Override
	public SyntheseGarde calculerFraisMensuels(Collection<SaisieJournaliere> donneesSaisies, int mois,
			int annee) {

		SyntheseGarde synthese = new SyntheseGarde(mois, annee);

		ParametrageGarde paramGarde = parametrageBlo.getParametrageGarde();

		NombreHeures nbHeures = calculerNbHeures(donneesSaisies, paramGarde);

		// FIXME : lien employé <> enfant
		ParametrageEmploye employe = IterableUtils.get(paramGarde.getEmployes(), 0);

		synthese.setNbJoursTravailles(calculerJoursTravailles(donneesSaisies, paramGarde));
		synthese.setNbHeuresNormalesReelles(nbHeures.getHeuresNormalesReelles());
		synthese.setNbHeuresNormalesContrat(nbHeures.getHeuresNormalesContrat());
		synthese.setNbHeuresComplementaires(nbHeures.getHeuresComplementaires());
		synthese.setSalaireHoraireNetHeureNormale(employe.getSalaireNetHoraire());

		Double salaireNetSansConges = calculerSalaireNetSansConges(paramGarde.getEnfants(), employe, nbHeures);
		synthese.setSalaireNetTotalSansConges(salaireNetSansConges);

		Double congesPayes = salaireNetSansConges * employe.getTauxCongesPayes();
		synthese.setCongesPayes(congesPayes);
		synthese.setSalaireNetTotal(salaireNetSansConges + congesPayes);

		synthese.setIndemnitesEntretien(calculerIndemnitesEntretien(donneesSaisies, employe));

		synthese.setIndemnitesRepas(calculerIndemnitesRepas(donneesSaisies, employe));

		synthese.setIndemnitesKm(calculerIndemnitesKm(donneesSaisies, employe, paramGarde));

		return synthese;
	}

	private Double calculerIndemnitesKm(Collection<SaisieJournaliere> donneesSaisies,
			ParametrageEmploye employe, ParametrageGarde paramGarde) {
		Double fraisKm = 0d;

		if (CollectionUtils.isNotEmpty(donneesSaisies)) {

			for (SaisieJournaliere saisie : donneesSaisies) {
				if (paramGarde.getEnfant(saisie.getPrenom()) != null) {
					Double kmEcole = paramGarde.getEnfant(saisie.getPrenom()).getArEcoleKm();
					fraisKm += saisie.getNbArEcole() * kmEcole * employe.getIndemnitesKm();
				}
				fraisKm += saisie.getAutresDeplacementKm() * employe.getIndemnitesKm();
			}
		}

		return fraisKm;
	}

	private Double calculerIndemnitesRepas(Collection<SaisieJournaliere> donneesSaisies,
			ParametrageEmploye employe) {

		Double fraisRepas = 0d;

		if (CollectionUtils.isNotEmpty(donneesSaisies)) {

			for (SaisieJournaliere saisie : donneesSaisies) {
				fraisRepas += saisie.getNbDejeuners() * employe.getFraisDejeuner();
				fraisRepas += saisie.getNbGouters() * employe.getFraisGouter();
			}
		}

		return fraisRepas;
	}

	private Double calculerIndemnitesEntretien(Collection<SaisieJournaliere> donneesSaisies,
			ParametrageEmploye employe) {

		Integer nbJours = 0;

		if (CollectionUtils.isNotEmpty(donneesSaisies)) {
			nbJours = donneesSaisies.size();
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

	private NombreHeures calculerNbHeures(Collection<SaisieJournaliere> donneesSaisies,
			ParametrageGarde paramGarde) {

		NombreHeures nbHeures = new NombreHeures();

		if (CollectionUtils.isNotEmpty(donneesSaisies)) {

			for (SaisieJournaliere saisie : donneesSaisies) {

				ParametrageEnfant paramEnfant = parametrageBlo.getParamEnfant(paramGarde, saisie.getPrenom());

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
						tempsJour += DateUtils.diff(heureArrivee,
								horairesJournaliers.getHorairesJournaliersEcole().getArriveeMatin());
					}
					if (heureDepart != null) {
						tempsJour += DateUtils
								.diff(horairesJournaliers.getHorairesJournaliersEcole().getDepartAprem(),
										heureDepart);
					}
					// FIXME TDU : temps midi ?

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
						logger.error("Impossible de calculer les horaires de {} le {}.", saisie.getPrenom(),
								saisie.getDateSaisie());
					}
					break;
				}

			}
		}
		return nbHeures;
	}

	private Integer calculerJoursTravailles(Collection<SaisieJournaliere> donneesSaisies,
			ParametrageGarde paramGarde) {
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
