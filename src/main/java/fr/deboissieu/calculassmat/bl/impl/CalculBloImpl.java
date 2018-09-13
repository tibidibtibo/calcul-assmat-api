package fr.deboissieu.calculassmat.bl.impl;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.CalculBlo;
import fr.deboissieu.calculassmat.bl.ExcelFileBlo;
import fr.deboissieu.calculassmat.model.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.SyntheseGarde;

@Component
public class CalculBloImpl implements CalculBlo {

	private static final Logger logger = LogManager.getLogger(CalculBloImpl.class);

	@Resource
	private ExcelFileBlo excelFileBlo;

	@Override
	public SyntheseGarde calculerSyntheseGarde(int mois) {
		try {
			Workbook workbook = excelFileBlo.openFile("testFiles/fichierTest.xlsx");
			Collection<SaisieJournaliere> synthese = excelFileBlo.extractDataFromWorkbook(workbook);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SyntheseGarde("SEPTEMBRE", "2018", 10);
	}

}
