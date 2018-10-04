package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
	public void devraitCalculerLesInformationsDeSynthesePourTempsPlein() {

		ParametrageEmploye paramEmploye = TestUtils.getParametrageEmploye();
		Map<String, ParametrageEnfant> mapParamEnfant = getMapParamEnfant1();

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

	@Test
	public void devraitCalculerLesInformationsDeSynthesePourPeriscolaire() {

		ParametrageEmploye paramEmploye = TestUtils.getParametrageEmploye();
		Map<String, ParametrageEnfant> mapParamEnfant = getMapParamEnfant2();

		doReturn(paramEmploye).when(parametrageBloMock).findEmployeParNom(Mockito.anyString());
		doReturn(mapParamEnfant).when(parametrageBloMock).findAllParamsEnfants();

		Collection<SaisieJournaliere> donneesSaisies = new ArrayList<>();
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 25),
				"enfant2", "07:00", null, 1, 0d, 0, 0)); // 0 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 26),
				"enfant2", "15:00", "17:00", 0, 4d, 0, 1)); // 2 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 27),
				"enfant2", null, "17:30", 1, 0d, 0, 1)); // 0 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 28),
				"enfant2", "08:00", "18:00", 2, 0d, 1, 1)); // 1.5 hc

		SyntheseGarde synthese = syntheseBlo.calculerFraisMensuels(donneesSaisies, 9, 2018, "nom");

		assertThat(synthese).isNotNull();
		assertThat(synthese.getAnnee()).isEqualTo("2018");
		assertThat(synthese.getMois()).isEqualTo("9");
		assertThat(synthese.getNbJoursTravailles()).isEqualTo(4);

		assertThat(synthese.getNombreHeures()).isNotNull();
		assertThat(synthese.getNombreHeures().getHeuresNormalesMensualisees()).isEqualTo(2d);
		assertThat(synthese.getNombreHeures().getHeuresNormalesReelles()).isEqualTo(4d);
		assertThat(synthese.getNombreHeures().getHeuresReelles()).isEqualTo(7.5d);
		assertThat(synthese.getNombreHeures().getHeuresComplementaires()).isEqualTo(3.5d);

		assertThat(synthese.getSalaire()).isNotNull();
		assertThat(synthese.getSalaire().getSalaireNetMensualise()).isEqualTo(250d);
		assertThat(synthese.getSalaire().getSalaireNetHeuresComplementaires()).isEqualTo(3.5d);
		assertThat(synthese.getSalaire().getCongesPayes()).isEqualTo(25.8d);
		assertThat(synthese.getSalaire().getSalaireNetTotal()).isEqualTo(283.78d);
		assertThat(synthese.getSalaire().getSalaireMensuel(synthese.getIndemnites())).isEqualTo(292.29d);

		assertThat(synthese.getIndemnites()).isNotNull();
		assertThat(synthese.getIndemnites().getIndemnitesEntretien()).isEqualTo(3d);
		assertThat(synthese.getIndemnites().getIndemnitesKm()).isEqualTo(3.11d);
		assertThat(synthese.getIndemnites().getIndemnitesRepas()).isEqualTo(2.4d);

	}

	public static Map<String, ParametrageEnfant> getMapParamEnfant1() {
		Map<String, ParametrageEnfant> mapParamEnfants = new HashMap<>();

		ParametrageEnfant enfant1 = TestUtils.buildParametrageEnfant("enfant1", "TEMPS_PLEIN", 250d, 10.1d, 0d);
		enfant1.setHeuresNormales(TestUtils.getHeuresNormales(9d, 9d, 0d, 8d, 9d, 0d, 0d));
		mapParamEnfants.put(enfant1.getNom(), enfant1);

		return mapParamEnfants;
	}

	public static Map<String, ParametrageEnfant> getMapParamEnfant2() {
		Map<String, ParametrageEnfant> mapParamEnfants = new HashMap<>();

		ParametrageEnfant enfant2 = TestUtils.buildParametrageEnfant("enfant2", "PERISCOLAIRE", 104d, 2d, 3.2d);
		enfant2.setHeuresNormales(TestUtils.getHeuresNormales(2d, 1d, 0d, 2d, 1d, 0d, 0d));
		enfant2.setHorairesEcole(TestUtils.getHorairesEcole());
		mapParamEnfants.put(enfant2.getNom(), enfant2);

		return mapParamEnfants;
	}

	// FIXME : continuer les TU avec TEMPS PLEIN + PERISCOLAIRE

}
