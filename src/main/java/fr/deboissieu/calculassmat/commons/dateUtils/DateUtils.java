package fr.deboissieu.calculassmat.commons.dateUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtils {

	public static Integer getMonthNumber(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		return month;
	}

	public static String toStringHour(Date date) {
		return DateFormatUtils.format(date, "HH:mm");
	}

	public static Double diff(String heureArrivee, String heureDepart) {
		DateTimeFormatter formatHeure = DateTimeFormatter.ofPattern("HH:mm");
		ZonedDateTime timeArrivee = ZonedDateTime.parse(heureArrivee, formatHeure);
		ZonedDateTime timeDepart = ZonedDateTime.parse(heureDepart, formatHeure);
		return Duration.between(timeArrivee, timeDepart).toMinutes() / 60;

	}
}
