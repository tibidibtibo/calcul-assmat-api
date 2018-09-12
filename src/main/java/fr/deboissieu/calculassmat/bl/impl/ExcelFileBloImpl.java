package fr.deboissieu.calculassmat.bl.impl;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ExcelFileBlo;

@Component
public class ExcelFileBloImpl implements ExcelFileBlo {

	private static final Logger logger = LogManager.getLogger(ExcelFileBloImpl.class);

	@Override
	public Workbook readFile(String fileName) throws EncryptedDocumentException, InvalidFormatException, IOException {

		final String SAMPLE_XLSX_FILE_PATH = "Suivi garde Louise et Joséphine.xlsx";

		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

		// Retrieving the number of sheets in the Workbook
		logger.info("Workbook has {} Sheets" + workbook.getNumberOfSheets());

		return workbook;
	}

}
