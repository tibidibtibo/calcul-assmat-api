package fr.deboissieu.calculassmat.bl;

import java.io.IOException;
import java.util.Collection;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import fr.deboissieu.calculassmat.model.SaisieJournaliere;

public interface ExcelFileBlo {

	Workbook openFile(String fileName) throws EncryptedDocumentException, InvalidFormatException, IOException;

	Collection<SaisieJournaliere> extractDataFromWorkbook(Workbook workbook);
}
