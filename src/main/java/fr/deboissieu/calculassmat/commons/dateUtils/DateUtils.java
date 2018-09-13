package fr.deboissieu.calculassmat.commons.dateUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

	public final static DateTimeFormatter FORMAT_HORODATAGE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static Integer getMonthNumber(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		return month;
	}
}
