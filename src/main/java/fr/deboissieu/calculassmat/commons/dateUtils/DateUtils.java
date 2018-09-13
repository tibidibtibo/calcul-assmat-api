package fr.deboissieu.calculassmat.commons.dateUtils;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

	public final static DateTimeFormatter FORMAT_HORODATAGE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static Date fromExcelDate(float excelDate) {
		return new Date(Math.round((excelDate - (25567 + 2)) * 86400) * 1000);
	}
}
