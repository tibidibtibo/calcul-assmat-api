package fr.deboissieu.calculassmat.bl.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
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
import fr.deboissieu.calculassmat.model.FraisJournaliers;
import fr.deboissieu.calculassmat.model.HoraireUnitaire;
import fr.deboissieu.calculassmat.model.HoraireUnitaireAvecFrais;
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

			Map<Date, Collection<HoraireUnitaireAvecFrais>> mapDateHoraires = mapperParDate(donneesBrutes);

			Map<Date, HorairesUnitairesEtFraisDissocies> mapHorairesEtFraisDissocies = dissocierFraisJournaliers(
					mapDateHoraires);
			// TODO TDU : dissocier frais journaliers
			// TODO TDU : mapper par personne
			// TODO TDU : assembler horaires

			// TODO TDU : calculs
			return Response.ok(mapHorairesEtFraisDissocies).build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Map<Date, HorairesUnitairesEtFraisDissocies> dissocierFraisJournaliers(
			Map<Date, Collection<HoraireUnitaireAvecFrais>> mapDateHoraires) {

		Map<Date, HorairesUnitairesEtFraisDissocies> mapHorairesEtFraisDissocies = new HashMap<>();

		if (MapUtils.isNotEmpty(mapDateHoraires)) {
			for (Date key : mapDateHoraires.keySet()) {
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

		HoraireUnitaireAvecFrais horaireAvecFraisNonNull = CollectionUtils.find(horairesUnitairesEtFrais,
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

	private Map<Date, Collection<HoraireUnitaireAvecFrais>> mapperParDate(Collection<SaisieJournaliere> donneesBrutes) {
		if (CollectionUtils.isEmpty(donneesBrutes)) {
			return null;
		}
		Map<Date, Collection<HoraireUnitaireAvecFrais>> mapDateHoraires = new HashMap<>();
		for (SaisieJournaliere saisie : donneesBrutes) {
			Date dateKey = DateUtils.truncate(saisie.getDateSaisie(), Calendar.DATE);
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

}
