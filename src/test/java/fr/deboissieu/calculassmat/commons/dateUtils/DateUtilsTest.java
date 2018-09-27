package fr.deboissieu.calculassmat.commons.dateUtils;

import static org.hamcrest.MatcherAssert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DateUtilsTest {

	@Test
	public void devraitDeterminerLeJourDeLaSemaine() throws ParseException {
		assertThat(DateUtils.getDayOfWeek(buildDate("10-09-2018")), Matchers.equalTo(1));
		assertThat(DateUtils.getDayOfWeek(buildDate("20-09-2018")), Matchers.equalTo(4));
		assertThat(DateUtils.getDayOfWeek(buildDate("31-08-2018")), Matchers.equalTo(5));

	}

	private Date buildDate(String strDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		return formatter.parse(strDate);
	}
}
