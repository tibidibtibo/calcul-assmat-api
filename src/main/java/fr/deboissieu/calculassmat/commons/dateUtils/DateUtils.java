package fr.deboissieu.calculassmat.commons.dateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtils {

	public static final String DATE_FORMAT_PATTERN = "dd-MM-yyyy";

	public static final String TIME_FORMAT_PATTERN = "HH:mm";

	public static final String FUSEAU_HORAIRE = "GMT+2";

	/**
	 * Extraction du numéro du mois
	 * 
	 * @param Date
	 *            date
	 * @return {@link Integer} numéro du mois
	 */
	public static Integer getMonthNumber(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		return month;
	}

	/**
	 * To string
	 * 
	 * @param date
	 * @return {@link String} date en texte
	 */
	public static String toStringHour(Date date) {
		return DateFormatUtils.format(date, TIME_FORMAT_PATTERN);
	}

	/**
	 * Formatte la date selon les paramètres
	 * 
	 * @param date
	 * @param pattern
	 * @param fuseau
	 * @return {@link String} date en texte
	 */
	public static String formatDate(Date date, String pattern, TimeZone fuseau) {
		DateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(fuseau);
		String dateKey = format
				.format(org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.DATE));
		return dateKey;
	}

	/**
	 * Texte to LocalTime
	 * 
	 * @param timeStr
	 * @return {@link LocalTime} heure locale
	 */
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
	 * @return {@link Double} différence
	 */
	public static Double diff(LocalTime heureArrivee, LocalTime heureDepart) {
		Long diff = Duration.between(heureArrivee, heureDepart).toMinutes();
		return Math.abs(diff.doubleValue() / 60);
	}

	/**
	 * Retourne le numéro de jour dans la semaine
	 * 
	 * @param date
	 * @return {@link Integer} numéro de jour dans la semaine
	 */
	public static Integer getDayOfWeek(Date date) {
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		DayOfWeek dayOfWeek = localDate.getDayOfWeek();
		return dayOfWeek.getValue();
	}

	/**
	 * Build Date
	 * 
	 * @param annee
	 * @param mois
	 * @param jour
	 * @return {@link Date} date
	 */
	public static Date getDate(int annee, int mois, int jour) {
		LocalDate date = LocalDate.of(annee, mois, jour);
		return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Build time (date = now)
	 * 
	 * @param heures
	 * @param minutes
	 * @return {@link Date} time
	 */
	public static Date getTime(int heures, int minutes) {
		LocalTime time = LocalTime.of(heures, minutes);
		Instant instant = time
				.atDate(LocalDate.now())
				.atZone(ZoneId.systemDefault())
				.toInstant();

		return Date.from(instant);
	}

	/**
	 * Date à MM/YYYY + 1 mois - 1 ms
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public static Date toMaxMonth(Integer month, Integer year) {

		LocalDate monthEnd = LocalDate.of(year, month, 1).plusMonths(1).withDayOfMonth(1);
		return Date.from(monthEnd.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date getDateTime(Date dateSaisie, Date heureArrivee) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateSaisie);
		Date dateTime = new Date();
		// TODO Auto-generated method stub
		// FIXME
		// :
		// set
		// date
		// !
		return null;
	}

}
