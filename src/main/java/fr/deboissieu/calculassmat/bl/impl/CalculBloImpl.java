package fr.deboissieu.calculassmat.bl.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import fr.deboissieu.calculassmat.bl.CalculBlo;
import fr.deboissieu.calculassmat.bl.ExcelFileBlo;
import fr.deboissieu.calculassmat.model.HoraireUnitaireAvecFrais;
import fr.deboissieu.calculassmat.model.SaisieJournaliere;

@Component
public class CalculBloImpl implements CalculBlo {

	private static final Logger logger = LogManager.getLogger(CalculBloImpl.class);

	@Resource
	private ExcelFileBlo excelFileBlo;

	@Override
	public Collection<SaisieJournaliere> calculerSyntheseGarde(int mois) {
		try {

			Workbook workbook = excelFileBlo.openFile("testFiles/fichierTest.xlsx");

			Collection<SaisieJournaliere> donneesBrutes = excelFileBlo.extractDataFromWorkbook(workbook, mois);

			Map<Date, Collection<HoraireUnitaireAvecFrais>> mapDateHoraires = mapperParDate(donneesBrutes);

			// TODO TDU : dissocier frais journaliers
			// TODO TDU : mapper par personne
			// TODO TDU : assembler horaires

			// TODO TDU : calculs
			return donneesBrutes;
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
