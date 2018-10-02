package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

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
				DateUtils.getDate(2018, 9, 27),
				"enfant1", "07:45", "17:30", 0, 3.7d, 0, 1));
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 28),
				"enfant1", "08:00", "18:00", 2, 0d, 1, 1));

		SyntheseGarde synthese = syntheseBlo.calculerFraisMensuels(donneesSaisies, 9, 2018, "nom");

		assertThat(synthese).isNotNull();
		assertThat(synthese.getAnnee()).isEqualTo("2018");
		assertThat(synthese.getMois()).isEqualTo("9");
		assertThat(synthese.getNbJoursTravailles()).isEqualTo(2);

		assertThat(synthese.getNombreHeures()).isNotNull();
		assertThat(synthese.getNombreHeures().getHeuresNormalesMensualisees()).isEqualTo(10.1d);
		assertThat(synthese.getNombreHeures().getHeuresNormalesReelles()).isEqualTo(17d);
		assertThat(synthese.getNombreHeures().getHeuresReelles()).isEqualTo(19.75d);
		assertThat(synthese.getNombreHeures().getHeuresComplementaires()).isEqualTo(2.75d);

		assertThat(synthese.getSalaire()).isNotNull();
		assertThat(synthese.getSalaire().getSalaireNetMensualise()).isEqualTo(250d);
		assertThat(synthese.getSalaire().getSalaireNetHeuresComplementaires()).isEqualTo(7.975d);
		assertThat(synthese.getSalaire().getCongesPayes()).isEqualTo(25.8d);
		assertThat(synthese.getSalaire().getSalaireNetTotal()).isEqualTo(283.78d);
		assertThat(synthese.getSalaire().getSalaireMensuel(synthese.getIndemnites())).isEqualTo(292.29d);

		assertThat(synthese.getIndemnites()).isNotNull();
		assertThat(synthese.getIndemnites().getIndemnitesEntretien()).isEqualTo(3d);
		assertThat(synthese.getIndemnites().getIndemnitesKm()).isEqualTo(3.11d);
		assertThat(synthese.getIndemnites().getIndemnitesRepas()).isEqualTo(2.4d);

	}

	// FIXME : continuer les TU avec PERISCOLAIRE

}
