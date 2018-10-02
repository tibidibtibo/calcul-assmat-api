package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.deboissieu.calculassmat.TestUtils;
import fr.deboissieu.calculassmat.bl.impl.SyntheseBloImpl;
import fr.deboissieu.calculassmat.bl.impl.ValidationBloImpl;
import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SyntheseBloTest.Config.class })
public class SyntheseBloTest {

	public static class Config {

		@Bean
		SyntheseBlo getSyntheseBlo() {
			return new SyntheseBloImpl();
		}

		@Bean
		ParametrageBlo getparametrageBlo() {
			return Mockito.mock(ParametrageBlo.class);
		}

		@Bean
		ValidationBlo getValidationBlo() {
			return new ValidationBloImpl();
		}
	}

	@Resource
	SyntheseBlo syntheseBlo;

	@Resource
	ParametrageBlo parametrageBloMock;

	@Test
	public void devraitCalculerLesInformationsDeSynthese() {

		ParametrageEmploye paramEmploye = TestUtils.getParametrageEmploye();
		Map<String, ParametrageEnfant> mapParamEnfant = TestUtils.getMapParamEnfant();

		doReturn(paramEmploye).when(parametrageBloMock).findEmployeParNom(Mockito.anyString());
		doReturn(mapParamEnfant).when(parametrageBloMock).findAllParamsEnfants();

		Collection<SaisieJournaliere> donneesSaisies = new ArrayList<>();
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 30),
				"enfant1", "07:45", "17:30", 0, 0d, 0, 0));

		SyntheseGarde synthese = syntheseBlo.calculerFraisMensuels(donneesSaisies, 9, 2018);

		assertThat(synthese).isNotNull();
		assertThat(synthese.getAnnee()).isEqualTo("2018");
		assertThat(synthese.getMois()).isEqualTo("9");
	}

	// FIXME : continuer les TU

}
