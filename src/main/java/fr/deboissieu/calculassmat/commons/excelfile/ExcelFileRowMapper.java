package fr.deboissieu.calculassmat.commons.excelfile;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;

public class ExcelFileRowMapper {

	public final static int HEADER_ROW = 0;

	public static Date cellToDate(Cell cell) {
		return cell != null ? DateUtil.getJavaDate(cell.getNumericCellValue()) : null;
	}

	public static String cellToHour(Cell cell) {
		String heure = cell != null ? DateUtils.toStringHour(DateUtil.getJavaDate(cell.getNumericCellValue())) : null;
		return heure == null || "00:00".equals(heure) ? null : heure;
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

	private static Cell getCell(Row row, ExcelFileColEnum colEnum) {
		return row != null ? row.getCell(colEnum.getColNum()) : null;
	}

	public static SaisieJournaliere toSaisieJournaliere(Row row, Date dateSaisie) {
		SaisieJournaliere saisieJournaliere = new SaisieJournaliere();
		saisieJournaliere.setDateSaisie(dateSaisie);
		saisieJournaliere.setEmploye(cellToString(getCell(row, ExcelFileColEnum.EMPLOYE)));
		saisieJournaliere.setEnfant(cellToString(getCell(row, ExcelFileColEnum.ENFANT)));
		saisieJournaliere.setHeureArrivee(cellToHour(getCell(row, ExcelFileColEnum.HEURE_ARRIVEE)));
		saisieJournaliere.setHeureDepart(cellToHour(getCell(row, ExcelFileColEnum.HEURE_DEPART)));
		saisieJournaliere.setNbDejeuners(cellToInteger(getCell(row, ExcelFileColEnum.DEJEUNER)));
		saisieJournaliere.setNbGouters(cellToInteger(getCell(row, ExcelFileColEnum.GOUTER)));
		saisieJournaliere.setNbArEcole(cellToInteger(getCell(row, ExcelFileColEnum.DEPLACEMENTS_ECOLE)));
		saisieJournaliere.setAutresDeplacementKm(cellToDouble(getCell(row, ExcelFileColEnum.AUTRE_DEPLACEMENTS_KM)));
		return saisieJournaliere;
	}

	public static Date extraireDateSaisie(Row row) {
		Date horodatage = cellToDate(getCell(row, ExcelFileColEnum.HORODATAGE));
		Date dateSaisie = cellToDate(getCell(row, ExcelFileColEnum.DATE_SAISIE));
		return dateSaisie != null ? dateSaisie : horodatage;
	}

}
