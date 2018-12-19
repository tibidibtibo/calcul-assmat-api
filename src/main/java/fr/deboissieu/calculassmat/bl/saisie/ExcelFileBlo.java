package fr.deboissieu.calculassmat.bl.saisie;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;

public interface ExcelFileBlo {

	/**
	 * Extraction des données saisies depuis le workbook
	 * 
	 * @param workbook
	 * @param mois
	 * @param annee
	 * @return
	 */
	Collection<SaisieJournaliere> extractDataFromWorkbook(Workbook workbook, int mois, int annee);

	/**
	 * Ouverture du workbook
	 * 
	 * @param file
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	Workbook openFileAsWorkbook(File file) throws InvalidFormatException, IOException;

	/**
	 * Ouverture d'un workbook
	 * 
	 * @param fileName
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	Workbook openWorkbook(String fileName) throws IOException, InvalidFormatException;
}
