package fr.deboissieu.calculassmat.commons.excelfile;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.model.SaisieJournaliere;

public class ExcelFileRowMapper {

	public final static int HEADER_ROW = 0;

	public static Date cellToDate(Cell cell, String string) {
		if (cell != null) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String excelDate = cell.toString();
			System.out.println(excelDate);
			if (StringUtils.isNoneBlank(excelDate)) {
				System.out.println(DateUtils.fromExcelDate(Float.parseFloat(excelDate)));
				return new Date();

			}
		}
		return null;
	}

	public static SaisieJournaliere toSaisieJournaliere(Row row) {
		SaisieJournaliere saisieJournaliere = new SaisieJournaliere();
		saisieJournaliere.setHorodatage(cellToDate(getCell(row, ExcelFileColEnum.HORODATAGE), ""));
		return saisieJournaliere;
	}

	private static Cell getCell(Row row, ExcelFileColEnum colEnum) {
		return row != null ? row.getCell(colEnum.getColNum()) : null;
	}
}
