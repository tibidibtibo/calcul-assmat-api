package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Validator;

import org.apache.commons.collections4.IterableUtils;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.deboissieu.calculassmat.TestUtils;
import fr.deboissieu.calculassmat.bl.parametrage.ParametrageBlo;
import fr.deboissieu.calculassmat.bl.synthese.SyntheseBlo;
import fr.deboissieu.calculassmat.bl.synthese.impl.SyntheseBloImpl;
import fr.deboissieu.calculassmat.bl.validation.ValidationBlo;
import fr.deboissieu.calculassmat.bl.validation.impl.ValidationBloImpl;
import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.dl.ParamEmployeRepository;
import fr.deboissieu.calculassmat.model.parametrage.IndemnitesEntretien;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.Saisie;
import fr.deboissieu.calculassmat.model.synthese.GroupeEmployeSaisies;
import fr.deboissieu.calculassmat.model.synthese.NombreHeures;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SyntheseBloTest.Config.class })
public class SyntheseBloTest {

	@Resource
	SyntheseBlo syntheseBlo;

	@Resource
	SyntheseBloImpl syntheseBloImpl;

	@Resource
	ParametrageBlo parametrageBloMock;

	@Resource
	ParamEmployeRepository paramEmployeRepositoryMock;

	@Before
	public void before() {
		Mockito.reset(parametrageBloMock, paramEmployeRepositoryMock);
	}

	@Test
	public void devraitCalculerLesInformationsDeSynthesePourTempsPlein() {

		// Arrange
		Map<ObjectId, ParametrageEnfant> mapParamEnfant = TestUtils.getMapParamEnfant1();
		doReturn(mapParamEnfant).when(parametrageBloMock).getMapObjectIdParamsEnfants();
		initEmployesMocks();

		Collection<Saisie> donneesSaisies = new ArrayList<>();
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 27),
				TestUtils.idEnfant1, "07:45", "17:30", 0, 3.7d, 0, 1, TestUtils.idEmploye1));
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 28),
				TestUtils.idEnfant1, "08:00", "18:00", 2, 0d, 1, 1, TestUtils.idEmploye1));

		// Act
		Collection<SyntheseGarde> syntheses = syntheseBlo.calculerSynthese(donneesSaisies, 9, 2018);

		// Assert
		SyntheseGarde synthese = IterableUtils.get(syntheses, 0);
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
		assertThat(synthese.getSalaire().getSalaireNetHeuresComplementaires()).isEqualTo(7.97d);
		assertThat(synthese.getSalaire().getCongesPayes()).isEqualTo(25.8d);
		assertThat(synthese.getSalaire().getSalaireNetTotal()).isEqualTo(283.77d);
		assertThat(synthese.getSalaire().getSalaireMensuel(synthese.getIndemnites())).isEqualTo(295.28d);

		assertThat(synthese.getIndemnites()).isNotNull();
		assertThat(synthese.getIndemnites().getIndemnitesEntretien()).isEqualTo(6d);
		assertThat(synthese.getIndemnites().getIndemnitesKm()).isEqualTo(3.11d);
		assertThat(synthese.getIndemnites().getIndemnitesRepas()).isEqualTo(2.4d);

	}

	@Test
	public void devraitCalculerLesInformationsDeSynthesePourPeriscolaire() {

		// Arrange
		initEmployesMocks();
		Map<ObjectId, ParametrageEnfant> mapParamEnfant = TestUtils.getMapParamEnfant2();
		doReturn(mapParamEnfant).when(parametrageBloMock).getMapObjectIdParamsEnfants();

		Collection<Saisie> donneesSaisies = new ArrayList<>();
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 25),
				TestUtils.idEnfant2, "07:00", null, 1, 0d, 0, 0, TestUtils.idEmploye1)); // 1 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 26),
				TestUtils.idEnfant2, "15:00", "17:00", 0, 4d, 1, 1, TestUtils.idEmploye1)); // 2 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 27),
				TestUtils.idEnfant2, null, "17:30", 1, 0d, 0, 1, TestUtils.idEmploye1)); // 0 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 28),
				TestUtils.idEnfant2, "08:00", "18:00", 2, 0d, 1, 1, TestUtils.idEmploye1)); // 1.5 hc

		// Act
		Collection<SyntheseGarde> syntheses = syntheseBlo.calculerSynthese(donneesSaisies, 9, 2018);

		// Assert
		SyntheseGarde synthese = IterableUtils.get(syntheses, 0);
		assertThat(synthese).isNotNull();
		assertThat(synthese.getAnnee()).isEqualTo("2018");
		assertThat(synthese.getMois()).isEqualTo("9");
		assertThat(synthese.getNbJoursTravailles()).isEqualTo(4);

		assertThat(synthese.getNombreHeures()).isNotNull();
		assertThat(synthese.getNombreHeures().getHeuresNormalesMensualisees()).isEqualTo(2d);
		assertThat(synthese.getNombreHeures().getHeuresNormalesReelles()).isEqualTo(4d);
		assertThat(synthese.getNombreHeures().getHeuresReelles()).isEqualTo(8.5d);
		assertThat(synthese.getNombreHeures().getHeuresComplementaires()).isEqualTo(5.5d);

		assertThat(synthese.getSalaire()).isNotNull();
		assertThat(synthese.getSalaire().getSalaireNetMensualise()).isEqualTo(104d);
		assertThat(synthese.getSalaire().getSalaireNetHeuresComplementaires()).isEqualTo(15.95d);
		assertThat(synthese.getSalaire().getCongesPayes()).isEqualTo(12.0d);
		assertThat(synthese.getSalaire().getSalaireNetTotal()).isEqualTo(131.95d);
		assertThat(synthese.getSalaire().getSalaireMensuel(synthese.getIndemnites())).isEqualTo(158.16d);

		assertThat(synthese.getIndemnites()).isNotNull();
		assertThat(synthese.getIndemnites().getIndemnitesEntretien()).isEqualTo(8d);
		assertThat(synthese.getIndemnites().getIndemnitesKm()).isEqualTo(14.11d);
		assertThat(synthese.getIndemnites().getIndemnitesRepas()).isEqualTo(4.1d);

	}

	@Test
	public void devraitCalculerLesInformationsDeSynthesePourTempsPleinEtPeriscolaire() {

		// Arrange
		initEmployesMocks();
		Map<ObjectId, ParametrageEnfant> mapParamEnfant = new HashMap<>();
		mapParamEnfant.putAll(TestUtils.getMapParamEnfant1());
		mapParamEnfant.putAll(TestUtils.getMapParamEnfant2());
		doReturn(mapParamEnfant).when(parametrageBloMock).getMapObjectIdParamsEnfants();

		Collection<Saisie> donneesSaisies = new ArrayList<>();

		// Enfant 1
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 25),
				TestUtils.idEnfant1, "07:00", "18:30", 0, 3.7d, 0, 1, TestUtils.idEmploye1)); // 2.5 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 27),
				TestUtils.idEnfant1, "07:45", "17:30", 0, 3.7d, 0, 1, TestUtils.idEmploye1)); // 1.75 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 28),
				TestUtils.idEnfant1, "08:00", "18:00", 0, 0d, 1, 1, TestUtils.idEmploye1)); // 1 hc

		// Enfant 2
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 25),
				TestUtils.idEnfant2, "07:00", null, 1, 0d, 0, 0, TestUtils.idEmploye1)); // 1 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 26),
				TestUtils.idEnfant2, "15:00", "17:00", 0, 4d, 0, 1, TestUtils.idEmploye1)); // 2 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 27),
				TestUtils.idEnfant2, null, "17:30", 1, 0d, 0, 1, TestUtils.idEmploye1)); // 0 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 28),
				TestUtils.idEnfant2, "08:00", "18:00", 2, 0d, 1, 1, TestUtils.idEmploye1)); // 1.5 hc

		// Act
		Collection<SyntheseGarde> syntheses = syntheseBlo.calculerSynthese(donneesSaisies, 9, 2018);

		// Assert
		SyntheseGarde synthese = IterableUtils.get(syntheses, 0);
		assertThat(synthese).isNotNull();
		assertThat(synthese.getAnnee()).isEqualTo("2018");
		assertThat(synthese.getMois()).isEqualTo("9");
		assertThat(synthese.getNbJoursTravailles()).isEqualTo(4);

		assertThat(synthese.getNombreHeures()).isNotNull();
		assertThat(synthese.getNombreHeures().getHeuresNormalesMensualisees()).isEqualTo(12.1d);
		assertThat(synthese.getNombreHeures().getHeuresNormalesReelles()).isEqualTo(30d);
		assertThat(synthese.getNombreHeures().getHeuresReelles()).isEqualTo(39.75d);
		assertThat(synthese.getNombreHeures().getHeuresComplementaires()).isEqualTo(10.75d);

		assertThat(synthese.getSalaire()).isNotNull();
		assertThat(synthese.getSalaire().getSalaireNetMensualise()).isEqualTo(354d);
		assertThat(synthese.getSalaire().getSalaireNetHeuresComplementaires()).isEqualTo(31.18d);
		assertThat(synthese.getSalaire().getCongesPayes()).isEqualTo(38.52d);
		assertThat(synthese.getSalaire().getSalaireNetTotal()).isEqualTo(423.7d);
		assertThat(synthese.getSalaire().getSalaireMensuel(synthese.getIndemnites())).isEqualTo(461.23);

		assertThat(synthese.getIndemnites()).isNotNull();
		assertThat(synthese.getIndemnites().getIndemnitesEntretien()).isEqualTo(11d);
		assertThat(synthese.getIndemnites().getIndemnitesKm()).isEqualTo(20.33d);
		assertThat(synthese.getIndemnites().getIndemnitesRepas()).isEqualTo(6.2d);

	}

	@Test
	public void devraitCalculerLesHoraires() {

		// Arrange
		Map<ObjectId, ParametrageEnfant> mapParamEnfant = new HashMap<>();
		mapParamEnfant.putAll(TestUtils.getMapParamEnfant1());
		mapParamEnfant.putAll(TestUtils.getMapParamEnfant2());

		Collection<Saisie> donneesSaisies = new ArrayList<>();
		// Enfant 1
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 25),
				TestUtils.idEnfant1, "07:00", "18:30", 0, 3.7d, 0, 1, TestUtils.idEmploye1)); // 2.5 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 27),
				TestUtils.idEnfant1, "07:45", "17:30", 0, 3.7d, 0, 1, TestUtils.idEmploye1)); // 1.75 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 28),
				TestUtils.idEnfant1, "08:00", "18:00", 0, 0d, 1, 1, TestUtils.idEmploye1)); // 1 hc

		// Enfant 2
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 25),
				TestUtils.idEnfant2, "07:00", null, 1, 0d, 0, 0, TestUtils.idEmploye1)); // 1 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 26),
				TestUtils.idEnfant2, "15:00", "17:00", 0, 4d, 1, 1, TestUtils.idEmploye1)); // 2 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 27),
				TestUtils.idEnfant2, null, "17:30", 1, 0d, 0, 1, TestUtils.idEmploye1)); // 0 hc
		donneesSaisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 28),
				TestUtils.idEnfant2, "08:00", "18:00", 2, 0d, 1, 1, TestUtils.idEmploye1)); // 1.5 hc

		// Act
		NombreHeures nbHeures = syntheseBloImpl.calculerNbHeures(donneesSaisies,
				TestUtils.getParametrageEmploye("employe1", TestUtils.objectIdEmploye1),
				mapParamEnfant);

		// Assert
		assertThat(nbHeures).isNotNull();
		assertThat(nbHeures.getHeuresNormalesMensualisees()).isEqualTo(12.1d);
		assertThat(nbHeures.getHeuresComplementaires()).isEqualTo(10.75d);
		assertThat(nbHeures.getHeuresNormalesReelles()).isEqualTo(30d);
		assertThat(nbHeures.getHeuresReelles()).isEqualTo(39.75d);

	}

	private void initEmployesMocks() {
		Mockito.reset(paramEmployeRepositoryMock);

		ParametrageEmploye paramEmploye1 = initParamEmploye("test", "employe1", TestUtils.objectIdEmploye1);
		doReturn(paramEmploye1).when(paramEmployeRepositoryMock).findBy_id(TestUtils.objectIdEmploye1);

		ParametrageEmploye paramEmploye2 = initParamEmploye("test", "employe2", TestUtils.objectIdEmploye2);
		doReturn(paramEmploye2).when(paramEmployeRepositoryMock).findBy_id(TestUtils.objectIdEmploye2);
	}

	private ParametrageEmploye initParamEmploye(String prenom, String nom, ObjectId id) {
		ParametrageEmploye paramEmploye = new ParametrageEmploye();

		paramEmploye.set_id(id);
		paramEmploye.setPrenom(prenom);
		paramEmploye.setNom(nom);
		paramEmploye.setTauxHoraireComplementaireNet(2.90d);
		paramEmploye.setTauxCongesPayes(0.10d);
		paramEmploye.setIndemnitesKm(0.84d);
		paramEmploye.setIndemnitesEntretien(new IndemnitesEntretien(8d, 2d, 3d));
		paramEmploye.setFraisDejeuner(1d);
		paramEmploye.setFraisGouter(0.7d);

		return paramEmploye;
	}

	@Test
	public void devraitGrouperLesSaisiesParEmploye() {
		// Arrange
		Collection<Saisie> saisies = new ArrayList<>();
		saisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 25), TestUtils.idEnfant1, "07:00", "18:30", 0, 3.7d, 0, 1,
				TestUtils.idEmploye1)); // 2.5
		saisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 27),
				TestUtils.idEnfant2, "07:45", "17:30", 0, 3.7d, 0, 1, TestUtils.idEmploye1));
		saisies.add(TestUtils.buildSaisie(
				DateUtils.getDate(2018, 9, 28),
				TestUtils.idEnfant1, "08:00", "18:00", 0, 0d, 1, 1, TestUtils.idEmploye2));

		ParametrageEmploye employe1 = TestUtils.getParametrageEmploye("employe1", TestUtils.objectIdEmploye1);
		Mockito.doReturn(employe1).when(paramEmployeRepositoryMock)
				.findBy_id(TestUtils.objectIdEmploye1);
		ParametrageEmploye employe2 = TestUtils.getParametrageEmploye("employe2", TestUtils.objectIdEmploye2);
		Mockito.doReturn(employe2).when(paramEmployeRepositoryMock)
				.findBy_id(TestUtils.objectIdEmploye2);

		// Act
		Collection<GroupeEmployeSaisies> groupes = syntheseBloImpl.groupByParamEmployes(saisies);

		// Assert
		assertThat(groupes).isNotEmpty().hasSize(2);
		assertThat(groupes).extracting("paramEmploye.nom").contains("employe1", "employe2");

		GroupeEmployeSaisies groupeEmp1 = groupes.stream()
				.filter(group -> group.getParamEmploye().get_id().equals(TestUtils.objectIdEmploye1))
				.findAny().orElse(null);
		assertThat(groupeEmp1).isNotNull();
		assertThat(groupeEmp1.getSaisies()).isNotEmpty().hasSize(2);
		assertThat(groupeEmp1.getSaisies()).extracting("enfantId").containsOnly(TestUtils.objectIdEnfant1,
				TestUtils.objectIdEnfant2);

		GroupeEmployeSaisies groupeEmp2 = groupes.stream()
				.filter(group -> group.getParamEmploye().get_id().equals(TestUtils.objectIdEmploye2))
				.findAny().orElse(null);
		assertThat(groupeEmp2).isNotNull();
		assertThat(groupeEmp2.getSaisies()).isNotEmpty().hasSize(1);
		assertThat(groupeEmp2.getSaisies()).extracting("enfantId").containsExactly(TestUtils.objectIdEnfant1);

	}

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
		ParamEmployeRepository getParamEmployeRepository() {
			return Mockito.mock(ParamEmployeRepository.class);
		}

		@Bean
		ValidationBlo getValidationBlo() {
			return new ValidationBloImpl();
		}

		@Bean
		Validator getValidator() {
			return Mockito.mock(Validator.class);
		}
	}
}
