package fr.deboissieu.calculassmat.commons.excelfile;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;

public class ExcelFileRowMapper {

	public final static int HEADER_ROW = 0;

	public final static String LIST_SEPARATOR = ", ";

	public static Date cellToDate(Cell cell) {
		return cell != null ? DateUtil.getJavaDate(cell.getNumericCellValue()) : null;
	}

	public static String cellToHour(Cell cell) {
		return cell != null ? DateUtils.toStringHour(DateUtil.getJavaDate(cell.getNumericCellValue())) : null;
	}

	public static String cellToString(Cell cell) {
		return cell != null ? cell.getStringCellValue() : null;
	}

	public static Integer cellToInteger(Cell cell) {
		if (cell != null) {
			Double doubleValue = cell.getNumericCellValue();
			return doubleValue.intValue();
		}
		return null;
	}

	public static Double cellToDouble(Cell cell) {
		if (cell != null) {
			return cell.getNumericCellValue();
		}
		return null;
	}

	private static Set<String> cellToStringSet(Cell cell, String separator) {
		if (cell != null) {
			String stringCell = cellToString(cell);
			if (StringUtils.isNoneBlank(stringCell)) {
				String[] cellArray = stringCell.split(separator);
				return new HashSet<>(Arrays.asList(cellArray));
			}
		}
		return null;
	}

	private static Cell getCell(Row row, ExcelFileColEnum colEnum) {
		return row != null ? row.getCell(colEnum.getColNum()) : null;
	}

	public static SaisieJournaliere toSaisieJournaliere(Row row, Date dateSaisie) {
		SaisieJournaliere saisieJournaliere = new SaisieJournaliere();
		saisieJournaliere.setDateSaisie(dateSaisie);
		saisieJournaliere.setQui(cellToStringSet(getCell(row, ExcelFileColEnum.QUI), LIST_SEPARATOR));
		saisieJournaliere.setAction(cellToString(getCell(row, ExcelFileColEnum.ACTION)));
		saisieJournaliere.setHeureAction(cellToHour(getCell(row, ExcelFileColEnum.HEURE_ACTION)));
		saisieJournaliere.setNbDejeunersLouise(cellToInteger(getCell(row, ExcelFileColEnum.DEJEUNER_LOUISE)));
		saisieJournaliere.setNbDejeunersJosephine(cellToInteger(getCell(row, ExcelFileColEnum.DEJEUNER_JOSEPHINE)));
		saisieJournaliere.setNbGoutersLouise(cellToInteger(getCell(row, ExcelFileColEnum.GOUTER_LOUISE)));
		saisieJournaliere.setNbGoutersJosephine(cellToInteger(getCell(row, ExcelFileColEnum.GOUTER_JOSEPHINE)));
		saisieJournaliere.setNbArEcoleLouise(cellToInteger(getCell(row, ExcelFileColEnum.DEPLACEMENTS_ECOLE_LOUISE)));
		saisieJournaliere.setAutresDeplacementKm(cellToDouble(getCell(row, ExcelFileColEnum.AUTRE_DEPLACEMENTS_KM)));
		return saisieJournaliere;
	}

	public static Date extraireDateSaisie(Row row) {
		Date horodatage = cellToDate(getCell(row, ExcelFileColEnum.HORODATAGE));
		Date dateSaisie = cellToDate(getCell(row, ExcelFileColEnum.DATE_SAISIE));
		return dateSaisie != null ? dateSaisie : horodatage;
	}

}
