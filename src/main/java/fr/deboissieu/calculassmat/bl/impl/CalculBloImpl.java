package fr.deboissieu.calculassmat.bl.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.CalculBlo;
import fr.deboissieu.calculassmat.bl.ExcelFileBlo;
import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import fr.deboissieu.calculassmat.model.FraisJournaliers;
import fr.deboissieu.calculassmat.model.HeuresPersonnelles;
import fr.deboissieu.calculassmat.model.HoraireUnitaire;
import fr.deboissieu.calculassmat.model.HoraireUnitaireAvecFrais;
import fr.deboissieu.calculassmat.model.HoraireUnitairePersonnel;
import fr.deboissieu.calculassmat.model.HorairesPersonnelsEtFrais;
import fr.deboissieu.calculassmat.model.HorairesPersonnelsUnitairesEtFrais;
import fr.deboissieu.calculassmat.model.HorairesUnitairesEtFraisDissocies;
import fr.deboissieu.calculassmat.model.SaisieJournaliere;

@Component
public class CalculBloImpl implements CalculBlo {

	private static final Logger logger = LogManager.getLogger(CalculBloImpl.class);

	@Resource
	private ExcelFileBlo excelFileBlo;

	@Override
	public Response calculerSyntheseGarde(int mois) {
		try {

			Workbook workbook = excelFileBlo.openFile("testFiles/fichierTest.xlsx");

			Collection<SaisieJournaliere> donneesBrutes = excelFileBlo.extractDataFromWorkbook(workbook, mois);

			Map<String, Collection<HoraireUnitaireAvecFrais>> mapDateHoraires = mapperParDate(donneesBrutes);

			Map<String, HorairesUnitairesEtFraisDissocies> mapHorairesEtFraisDissocies = dissocierFraisJournaliers(
					mapDateHoraires);

			Map<String, HorairesPersonnelsUnitairesEtFrais> mapHorairesPersoUnitairesEtFrais = mapperParPersonne(
					mapHorairesEtFraisDissocies);

			Map<String, HorairesPersonnelsEtFrais> mapHorairesPersoEtFrais = assemblerHoraires(
					mapHorairesPersoUnitairesEtFrais);

			// TODO TDU : calculs

			return Response.ok(mapHorairesPersoEtFrais).build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Map<String, HorairesPersonnelsEtFrais> assemblerHoraires(
			Map<String, HorairesPersonnelsUnitairesEtFrais> mapHorairesPersoUnitairesEtFrais) {

		Map<String, HorairesPersonnelsEtFrais> mapHorairesPersoEtFrais = new HashMap<>();

		if (MapUtils.isNotEmpty(mapHorairesPersoUnitairesEtFrais)) {
			for (String key : mapHorairesPersoUnitairesEtFrais.keySet()) {
				HorairesPersonnelsEtFrais horairePerso = new HorairesPersonnelsEtFrais();
				horairePerso.setFraisJournaliers(mapHorairesPersoUnitairesEtFrais.get(key).getFraisJournaliers());
				Collection<HeuresPersonnelles> heuresPersonnelles = calculerHeuresPersonelles(
						mapHorairesPersoUnitairesEtFrais.get(key).getHoraires());
				horairePerso.setHeuresPersonnelles(heuresPersonnelles);
				mapHorairesPersoEtFrais.put(key, horairePerso);
			}
		}

		return mapHorairesPersoEtFrais;
	}

	private Collection<HeuresPersonnelles> calculerHeuresPersonelles(Collection<HoraireUnitairePersonnel> horaires) {

		Map<PrenomEnum, HeuresPersonnelles> mapHorairesPersonnels = new HashMap<>();

		for (HoraireUnitairePersonnel horaireUnitaire : horaires) {
			PrenomEnum key = horaireUnitaire.getPrenom();
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
			for (String key : mapHorairesEtFraisDissocies.keySet()) {
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
				Collection<HoraireUnitairePersonnel> horairesUnitairesPerso = CollectionUtils
						.collect(horaireUnitaire.getPrenoms(), new Transformer<PrenomEnum, HoraireUnitairePersonnel>() {
							@Override
							public HoraireUnitairePersonnel transform(PrenomEnum prenom) {
								return HoraireUnitairePersonnel.of(horaireUnitaire, prenom);
							}

						});
				horairesPersonnels.addAll(horairesUnitairesPerso);
			}
		}
		return horairesPersonnels;
	}

	private Map<String, HorairesUnitairesEtFraisDissocies> dissocierFraisJournaliers(
			Map<String, Collection<HoraireUnitaireAvecFrais>> mapDateHoraires) {

		Map<String, HorairesUnitairesEtFraisDissocies> mapHorairesEtFraisDissocies = new HashMap<>();

		if (MapUtils.isNotEmpty(mapDateHoraires)) {
			for (String key : mapDateHoraires.keySet()) {
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
		return CollectionUtils.collect(horairesUnitairesEtFrais,
				new Transformer<HoraireUnitaireAvecFrais, HoraireUnitaire>() {

					@Override
					public HoraireUnitaire transform(HoraireUnitaireAvecFrais horaireAvecFrais) {
						return horaireAvecFrais.getHoraireUnitaire();
					}
				});
	}

	private FraisJournaliers extrairesFraisJournaliers(Collection<HoraireUnitaireAvecFrais> horairesUnitairesEtFrais) {

		HoraireUnitaireAvecFrais horaireAvecFraisNonNull = IterableUtils.find(horairesUnitairesEtFrais,
				new Predicate<HoraireUnitaireAvecFrais>() {

					@Override
					public boolean evaluate(HoraireUnitaireAvecFrais horaireAvecFrais) {
						return horaireAvecFrais != null && horaireAvecFrais.getFraisJournaliers() != null
								&& (horaireAvecFrais.getFraisJournaliers().getAutresDeplacementKm() != null
										|| CollectionUtils
												.isNotEmpty(horaireAvecFrais.getFraisJournaliers().getDeplacements())
										|| CollectionUtils
												.isNotEmpty(horaireAvecFrais.getFraisJournaliers().getRepas()));
					}
				});

		return horaireAvecFraisNonNull != null ? horaireAvecFraisNonNull.getFraisJournaliers() : null;
	}

	private Map<String, Collection<HoraireUnitaireAvecFrais>> mapperParDate(
			Collection<SaisieJournaliere> donneesBrutes) {
		if (CollectionUtils.isEmpty(donneesBrutes)) {
			return null;
		}
		Map<String, Collection<HoraireUnitaireAvecFrais>> mapDateHoraires = new HashMap<>();
		for (SaisieJournaliere saisie : donneesBrutes) {

			DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			format.setTimeZone(TimeZone.getTimeZone("GMT+2"));
			String dateKey = format.format(DateUtils.truncate(saisie.getDateSaisie(), Calendar.DATE));

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

	@Override
	public Response streamCalculSynthese(int mois) {

		try {

			Workbook workbook = excelFileBlo.openFile("testFiles/fichierTest.xlsx");

			Stream<SaisieJournaliere> dataStream = excelFileBlo.streamWorkbook(workbook, mois);

			// dataStream.map(
			//
			// );

			return Response.ok(dataStream).build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
