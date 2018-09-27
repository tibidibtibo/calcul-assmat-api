package fr.deboissieu.calculassmat.bl.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.AssemblageSaisieBlo;
import fr.deboissieu.calculassmat.model.saisie.FraisJournaliers;
import fr.deboissieu.calculassmat.model.saisie.HeuresPersonnelles;
import fr.deboissieu.calculassmat.model.saisie.HoraireUnitaire;
import fr.deboissieu.calculassmat.model.saisie.HoraireUnitaireAvecFrais;
import fr.deboissieu.calculassmat.model.saisie.HoraireUnitairePersonnel;
import fr.deboissieu.calculassmat.model.saisie.HorairesPersonnelsEtFrais;
import fr.deboissieu.calculassmat.model.saisie.HorairesPersonnelsUnitairesEtFrais;
import fr.deboissieu.calculassmat.model.saisie.HorairesUnitairesEtFraisDissocies;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;

@Component
public class AssemblageSaisieBloImpl implements AssemblageSaisieBlo {

	@Override
	public Map<String, HorairesPersonnelsEtFrais> assemblerDonneesSaisies(
			Collection<SaisieJournaliere> saisieJournaliere) {
		Map<String, Collection<HoraireUnitaireAvecFrais>> mapDateHoraires = mapperParDate(saisieJournaliere);

		Map<String, HorairesUnitairesEtFraisDissocies> mapHorairesEtFraisDissocies = dissocierFraisJournaliers(
				mapDateHoraires);

		Map<String, HorairesPersonnelsUnitairesEtFrais> mapHorairesPersoUnitairesEtFrais = mapperParPersonne(
				mapHorairesEtFraisDissocies);

		return assemblerHoraires(mapHorairesPersoUnitairesEtFrais);
	}

	private Map<String, HorairesPersonnelsEtFrais> assemblerHoraires(
			Map<String, HorairesPersonnelsUnitairesEtFrais> mapHorairesPersoUnitairesEtFrais) {

		Map<String, HorairesPersonnelsEtFrais> mapHorairesPersoEtFrais = new HashMap<>();

		if (MapUtils.isNotEmpty(mapHorairesPersoUnitairesEtFrais)) {
			for (Map.Entry<String, HorairesPersonnelsUnitairesEtFrais> entry : mapHorairesPersoUnitairesEtFrais
					.entrySet()) {
				String key = entry.getKey();
				HorairesPersonnelsEtFrais horairePerso = new HorairesPersonnelsEtFrais();
				horairePerso.setFraisJournaliers(entry.getValue().getFraisJournaliers());
				Collection<HeuresPersonnelles> heuresPersonnelles = calculerHeuresPersonelles(
						entry.getValue().getHoraires());
				horairePerso.setHeuresPersonnelles(heuresPersonnelles);
				mapHorairesPersoEtFrais.put(key, horairePerso);
			}
		}

		return mapHorairesPersoEtFrais;
	}

	private Map<String, HorairesUnitairesEtFraisDissocies> dissocierFraisJournaliers(
			Map<String, Collection<HoraireUnitaireAvecFrais>> mapDateHoraires) {

		Map<String, HorairesUnitairesEtFraisDissocies> mapHorairesEtFraisDissocies = new HashMap<>();

		if (mapDateHoraires != null && MapUtils.isNotEmpty(mapDateHoraires)) {
			for (Map.Entry<String, Collection<HoraireUnitaireAvecFrais>> entry : mapDateHoraires.entrySet()) {
				String key = entry.getKey();
				HorairesUnitairesEtFraisDissocies horairesUnitairesEtFraisDissocies = new HorairesUnitairesEtFraisDissocies();
				horairesUnitairesEtFraisDissocies
						.setFraisJournaliers(extrairesFraisJournaliers(mapDateHoraires.get(key)));
				horairesUnitairesEtFraisDissocies.setHoraires(extrairesHorairesUnitaires(mapDateHoraires.get(key)));
				mapHorairesEtFraisDissocies.put(key, horairesUnitairesEtFraisDissocies);
			}
		}

		return mapHorairesEtFraisDissocies;
	}

	private Collection<HoraireUnitaire> extrairesHorairesUnitaires(
			Collection<HoraireUnitaireAvecFrais> horairesUnitairesEtFrais) {
		return CollectionUtils.collect(horairesUnitairesEtFrais, HoraireUnitaireAvecFrais::getHoraireUnitaire);

	}

	private FraisJournaliers extrairesFraisJournaliers(Collection<HoraireUnitaireAvecFrais> horairesUnitairesEtFrais) {

		FraisJournaliers fraisJournaliers = new FraisJournaliers();
		Double deplacementsKm = 0d;

		for (HoraireUnitaireAvecFrais horaire : horairesUnitairesEtFrais) {
			if (horaire.getFraisJournaliers() != null
					&& horaire.getFraisJournaliers().getAutresDeplacementKm() != null) {
				deplacementsKm += horaire.getFraisJournaliers().getAutresDeplacementKm();
			}
		}

		if (deplacementsKm.equals(0d)) {
			return null;
		}

		fraisJournaliers.setAutresDeplacementKm(deplacementsKm);
		return fraisJournaliers;
	}

	private Map<String, Collection<HoraireUnitaireAvecFrais>> mapperParDate(
			Collection<SaisieJournaliere> donneesBrutes) {

		if (CollectionUtils.isEmpty(donneesBrutes)) {
			return null;
		}
		Map<String, Collection<HoraireUnitaireAvecFrais>> mapDateHoraires = new HashMap<>();
		for (SaisieJournaliere saisie : donneesBrutes) {

			String dateKey = fr.deboissieu.calculassmat.commons.dateUtils.DateUtils.formatDate(saisie,
					fr.deboissieu.calculassmat.commons.dateUtils.DateUtils.DATE_FORMAT_PATTERN,
					TimeZone.getTimeZone(fr.deboissieu.calculassmat.commons.dateUtils.DateUtils.FUSEAU_HORAIRE));

			if (mapDateHoraires.get(dateKey) == null) {
				HoraireUnitaireAvecFrais horaireUnitaire = HoraireUnitaireAvecFrais.of(saisie);
				List<HoraireUnitaireAvecFrais> listeHoraires = new ArrayList<>();
				listeHoraires.add(horaireUnitaire);
				mapDateHoraires.put(dateKey, listeHoraires);
			} else {
				HoraireUnitaireAvecFrais horaireUnitaire = HoraireUnitaireAvecFrais.of(saisie);
				mapDateHoraires.get(dateKey).add(horaireUnitaire);
			}
		}
		return mapDateHoraires;
	}

	private Collection<HeuresPersonnelles> calculerHeuresPersonelles(Collection<HoraireUnitairePersonnel> horaires) {

		Map<String, HeuresPersonnelles> mapHorairesPersonnels = new HashMap<>();

		for (HoraireUnitairePersonnel horaireUnitaire : horaires) {
			String key = horaireUnitaire.getPrenom();
			if (mapHorairesPersonnels.get(key) == null) {
				HeuresPersonnelles heuresPersonnelles = HeuresPersonnelles.of(key, horaireUnitaire);
				mapHorairesPersonnels.put(key, heuresPersonnelles);
			} else {
				mapHorairesPersonnels.get(key).addHoraire(horaireUnitaire);
			}
		}

		return (MapUtils.isNotEmpty(mapHorairesPersonnels)) ? mapHorairesPersonnels.values() : new ArrayList<>();
	}

	private Map<String, HorairesPersonnelsUnitairesEtFrais> mapperParPersonne(
			Map<String, HorairesUnitairesEtFraisDissocies> mapHorairesEtFraisDissocies) {

		Map<String, HorairesPersonnelsUnitairesEtFrais> mapHorairesPersoEtFrais = new HashMap<>();

		if (MapUtils.isNotEmpty(mapHorairesEtFraisDissocies)) {
			for (Map.Entry<String, HorairesUnitairesEtFraisDissocies> entry : mapHorairesEtFraisDissocies.entrySet()) {
				String key = entry.getKey();
				HorairesPersonnelsUnitairesEtFrais horairesPersonnelsUnitairesEtFrais = new HorairesPersonnelsUnitairesEtFrais();
				horairesPersonnelsUnitairesEtFrais
						.setFraisJournaliers(mapHorairesEtFraisDissocies.get(key).getFraisJournaliers());
				Collection<HoraireUnitairePersonnel> horairesPersonnelsUnitaires = flattenParPersonne(
						mapHorairesEtFraisDissocies.get(key).getHoraires());
				horairesPersonnelsUnitairesEtFrais.setHoraires(horairesPersonnelsUnitaires);
				mapHorairesPersoEtFrais.put(key, horairesPersonnelsUnitairesEtFrais);
			}
		}
		return mapHorairesPersoEtFrais;
	}

	private Collection<HoraireUnitairePersonnel> flattenParPersonne(Collection<HoraireUnitaire> horaires) {
		Collection<HoraireUnitairePersonnel> horairesPersonnels = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(horaires)) {
			for (HoraireUnitaire horaireUnitaire : horaires) {
				Collection<HoraireUnitairePersonnel> horairesUnitairesPerso = CollectionUtils.collect(
						horaireUnitaire.getPrenoms(), prenom -> HoraireUnitairePersonnel.of(horaireUnitaire, prenom));
				horairesPersonnels.addAll(horairesUnitairesPerso);
			}
		}
		return horairesPersonnels;
	}

}
