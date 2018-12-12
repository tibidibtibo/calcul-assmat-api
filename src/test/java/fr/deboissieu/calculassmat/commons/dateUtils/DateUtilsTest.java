package fr.deboissieu.calculassmat.commons.dateUtils;

import static org.assertj.core.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DateUtilsTest {

	private final static String DATE_FORMAT = "dd-MM-yyyy";
	private final static String DATETIME_FORMAT = "dd-MM-yyyy HH:mm:ss sss";

	@Test
	public void devraitDeterminerLeJourDeLaSemaine() throws ParseException {

		assertThat(DateUtils.getDayOfWeek(buildDate("10-09-2018", DATE_FORMAT))).isEqualTo(1);
		assertThat(DateUtils.getDayOfWeek(buildDate("20-09-2018", DATE_FORMAT))).isEqualTo(4);
		assertThat(DateUtils.getDayOfWeek(buildDate("31-08-2018", DATE_FORMAT))).isEqualTo(5);

	}

	private Date buildDate(String strDate, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(strDate);
	}

	@Test
	public void devraitCreerDateDebutMois() throws ParseException {
		assertThat(DateUtils.getDate(2018, 1, 1)).isEqualTo(buildDate("01-01-2018", DATE_FORMAT));
		assertThat(DateUtils.getDate(2018, 12, 1)).isEqualTo(buildDate("01-12-2018", DATE_FORMAT));
	}

	@Test
	public void devraitCreerDateFinMois() throws ParseException {
		assertThat(DateUtils.toMaxMonth(12, 2018)).isEqualTo(buildDate("01-01-2019 00:00:00 000", DATETIME_FORMAT));
		assertThat(DateUtils.toMaxMonth(4, 2019)).isEqualTo(buildDate("01-05-2019 00:00:00 000", DATETIME_FORMAT));
	}

}
