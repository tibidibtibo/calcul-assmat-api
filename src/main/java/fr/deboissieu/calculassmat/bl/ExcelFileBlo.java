package fr.deboissieu.calculassmat.bl;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelFileBlo {

	Workbook readFile(String fileName) throws EncryptedDocumentException, InvalidFormatException, IOException;
}
