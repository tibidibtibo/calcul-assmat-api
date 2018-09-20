package fr.deboissieu.calculassmat.commons.dateUtils;

import static org.hamcrest.MatcherAssert.*;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DateUtilsTest {

	@Test
	public void devraitDeterminerLeJourDeLaSemaine() {
		assertThat(DateUtils.getDayOfWeek("10-09-2018"), Matchers.equalTo(1));
		assertThat(DateUtils.getDayOfWeek("20-09-2018"), Matchers.equalTo(4));
		assertThat(DateUtils.getDayOfWeek("31-08-2018"), Matchers.equalTo(5));

	}
}
