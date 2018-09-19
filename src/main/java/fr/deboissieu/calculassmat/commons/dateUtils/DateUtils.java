package fr.deboissieu.calculassmat.commons.dateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.deboissieu.calculassmat.model.SaisieJournaliere;

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

	public static Double diff(String heureArrivee, String heureDepart) {
		if (StringUtils.isNoneBlank(heureArrivee) && StringUtils.isNoneBlank(heureDepart)) {
			LocalTime timeArrivee = LocalTime.parse(heureArrivee, DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN));
			LocalTime timeDepart = LocalTime.parse(heureDepart, DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN));
			Long diff = Duration.between(timeArrivee, timeDepart).toMinutes();
			return diff.doubleValue() / 60;
		}
		return null;
	}

	public static Integer getDayOfWeek(String dateString) {
		try {
			Date date = org.apache.commons.lang3.time.DateUtils.parseDate(dateString, DATE_FORMAT_PATTERN);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String dayOfWeek = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
			return Integer.parseInt(dayOfWeek);
		} catch (ParseException e) {
			logger.error("Impossible de déterminer le jour de la semaine : {}", e);
		}
		return null;
	}
}
