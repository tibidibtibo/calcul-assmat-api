package fr.deboissieu.calculassmat.bl.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ExcelFileBlo;
import fr.deboissieu.calculassmat.commons.excelfile.ExcelFileRowMapper;
import fr.deboissieu.calculassmat.model.SaisieJournaliere;

@Component
public class ExcelFileBloImpl implements ExcelFileBlo {

	private static final Logger logger = LogManager.getLogger(ExcelFileBloImpl.class);

	@Override
	public Workbook openFile(String fileName) throws EncryptedDocumentException, InvalidFormatException, IOException {

		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());

		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(file);

		// Retrieving the number of sheets in the Workbook
		logger.info("Workbook has {} Sheets", workbook.getNumberOfSheets());

		return workbook;
	}

	@Override
	public Collection<SaisieJournaliere> extractDataFromWorkbook(Workbook workbook) {
		Sheet feuille1 = workbook.getSheetAt(0);

		Collection<SaisieJournaliere> data = new HashSet<>();

		Iterator<Row> rowIterator = feuille1.iterator();

		int counter = 0;
		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();
			if (counter > ExcelFileRowMapper.HEADER_ROW) {
				SaisieJournaliere saisieJournaliere = ExcelFileRowMapper.toSaisieJournaliere(row);
				if (saisieJournaliere != null) {
					data.add(saisieJournaliere);
				}
			}
			counter++;
		}
		return data;
	}

}
