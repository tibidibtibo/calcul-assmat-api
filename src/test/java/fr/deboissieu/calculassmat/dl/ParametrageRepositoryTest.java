package fr.deboissieu.calculassmat.dl;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.deboissieu.calculassmat.dl.impl.ParametrageRepositoryImpl;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageGarde;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ParametrageRepositoryTest.Config.class })
public class ParametrageRepositoryTest {

	public static class Config {

		@Bean
		ParametrageRepository getParametrageRepository() {
			return new ParametrageRepositoryImpl();
		}
	}

	@Resource
	ParametrageRepository parametrageRepository;

	@Test
	public void devraitChargerLeParametrageGarde() {
		ParametrageGarde paramGarde = parametrageRepository.getParametrageGarde();
		assertNotNull(paramGarde);

		assertThat(paramGarde.getEnfants(), hasSize(2));
	}
}
