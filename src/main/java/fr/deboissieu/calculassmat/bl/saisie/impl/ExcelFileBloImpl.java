package fr.deboissieu.calculassmat.bl.saisie.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.saisie.ExcelFileBlo;
import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.commons.excelfile.ExcelFileRowMapper;
import fr.deboissieu.calculassmat.commons.filestorage.FileStorageService;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;

@Component
public class ExcelFileBloImpl implements ExcelFileBlo {

	private static final Logger logger = LogManager.getLogger(ExcelFileBloImpl.class);

	@Resource
	FileStorageService fileStorageService;

	@Override
	public Workbook openFileAsWorkbook(File file) throws InvalidFormatException, IOException {

		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(file);

		// Retrieving the number of sheets in the Workbook
		logger.info("Ouverture du fichier '{}' - {} feuille(s)", file.getPath(), workbook.getNumberOfSheets());

		return workbook;
	}

	@Override
	public Collection<SaisieJournaliere> extractDataFromWorkbook(Workbook workbook, int mois, int annee) {
		Sheet feuille1 = workbook.getSheetAt(0);

		Collection<SaisieJournaliere> data = new HashSet<>();

		Iterator<Row> rowIterator = feuille1.iterator();

		int counter = 0;
		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();
			if (counter > ExcelFileRowMapper.HEADER_ROW) {
				Date dateSaisie = ExcelFileRowMapper.extraireDateSaisie(row);
				if (dateSaisie != null && DateUtils.getMonthNumber(dateSaisie).equals(mois)
						&& DateUtils.getYearNumber(dateSaisie).equals(annee)) {
					SaisieJournaliere saisieJournaliere = ExcelFileRowMapper.toSaisieJournaliere(row, dateSaisie);
					if (saisieJournaliere != null) {
						data.add(saisieJournaliere);
					}
				}
			}
			counter++;
		}
		return data;
	}

	@Override
	public Workbook openWorkbook(String filename) throws IOException, InvalidFormatException {
		try {
			org.springframework.core.io.Resource fileResource = fileStorageService.loadFileAsResource(filename);
			File file = fileResource.getFile();
			return openFileAsWorkbook(file);
		} catch (Exception e) {
			logger.error("Impossible de traiter le fichier : {}", e);
			throw e;
		}
	}
}
