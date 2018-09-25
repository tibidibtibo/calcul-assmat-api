package fr.deboissieu.calculassmat.commons.dateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;

public class DateUtils {

	private static final Logger logger = LogManager.getLogger(DateUtils.class);

	public static final String DATE_FORMAT_PATTERN = "dd-MM-yyyy";
	public static final String TIME_FORMAT_PATTERN = "HH:mm";

	public static final String FUSEAU_HORAIRE = "GMT+2";

	public static Integer getMonthNumber(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		return month;
	}

	public static String toStringHour(Date date) {
		return DateFormatUtils.format(date, TIME_FORMAT_PATTERN);
	}

	public static String formatDate(SaisieJournaliere saisie, String pattern, TimeZone fuseau) {
		DateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(fuseau);
		String dateKey = format
				.format(org.apache.commons.lang3.time.DateUtils.truncate(saisie.getDateSaisie(), Calendar.DATE));
		return dateKey;
	}

	public static LocalTime toLocalTime(String timeStr) {
		if (StringUtils.isBlank(timeStr)) {
			return null;
		}
		return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN));
	}

	/**
	 * Différence entre deux heures - en heures (décimal)
	 * 
	 * @param heureArrivee
	 * @param heureDepart
	 * @return
	 */
	public static Double diff(LocalTime heureArrivee, LocalTime heureDepart) {
		Long diff = Duration.between(heureArrivee, heureDepart).toMinutes();
		return diff.doubleValue() / 60;
	}

	public static Integer getDayOfWeek(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		DayOfWeek dayOfWeek = localDate.getDayOfWeek();
		return dayOfWeek.getValue();
	}
}
