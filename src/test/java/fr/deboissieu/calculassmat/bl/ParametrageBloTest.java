package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

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
import fr.deboissieu.calculassmat.bl.parametrage.impl.ParametrageBloImpl;
import fr.deboissieu.calculassmat.dl.ParamEmployeRepository;
import fr.deboissieu.calculassmat.dl.ParamEnfantRepository;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ParametrageBloTest.Config.class })
public class ParametrageBloTest {

	public static class Config {

		@Bean
		ParametrageBlo getParametrageBlo() {
			return new ParametrageBloImpl();
		}

		@Bean
		ParamEnfantRepository getParamEnfantRepository() {
			return Mockito.mock(ParamEnfantRepository.class);
		}

		@Bean
		ParamEmployeRepository getParamEmployeRepository() {
			return Mockito.mock(ParamEmployeRepository.class);
		}
	}

	@Resource
	ParametrageBlo parametrageBlo;

	@Resource
	ParamEnfantRepository paramEnfantRepoMock;

	@Resource
	ParamEmployeRepository paramEmployeRepoMock;

	@Before
	public void before() {
		Mockito.reset(paramEnfantRepoMock, paramEmployeRepoMock);
	}

	@Test
	public void devraitExtraireLeParametrageEnfant() {

		Collection<ParametrageEnfant> listeEnfants = new ArrayList<>();
		ParametrageEnfant enfant1 = TestUtils.buildParametrageEnfant(TestUtils.objectIdEnfant1, "enfant1", "type1");
		enfant1.setEmployes(Arrays.asList(TestUtils.buildEmployeInfo(TestUtils.objectIdEmploye1, 0d, 0d, 10d, null)));
		ParametrageEnfant enfant2 = TestUtils.buildParametrageEnfant(TestUtils.objectIdEnfant2, "enfant2", "type2");
		enfant2.setEmployes(Arrays.asList(TestUtils.buildEmployeInfo(TestUtils.objectIdEmploye2, 0d, 4d, 12d, null)));
		ParametrageEnfant enfant3 = TestUtils.buildParametrageEnfant(TestUtils.objectIdEnfant3, "enfant3", null);
		enfant3.setEmployes(Arrays.asList(TestUtils.buildEmployeInfo(TestUtils.objectIdEmploye1, 0d, 2d, 14d, null)));
		listeEnfants.addAll(Arrays.asList(enfant1, enfant2, enfant3));
		doReturn(listeEnfants).when(paramEnfantRepoMock).findAll();

		Map<String, ParametrageEnfant> mapParamEnfant = parametrageBlo.getMapIdParamsEnfants();

		assertThat(mapParamEnfant).isNotEmpty().hasSize(3);
		assertThat(mapParamEnfant.values()).extracting("nom").contains("enfant1", "enfant2", "enfant3");
		assertThat(mapParamEnfant.values()).extracting("typeGarde").contains("type1", "type2", null);
		assertThat(mapParamEnfant.values()).extracting("employes").hasSize(3);

		verify(paramEnfantRepoMock, times(1)).findAll();
	}

	@Test
	public void neDevraitPasExtraireDeParametrageEnfant() {

		doReturn(null).when(paramEnfantRepoMock).findAll();

		Map<String, ParametrageEnfant> mapParamEnfant = parametrageBlo.getMapIdParamsEnfants();

		assertThat(mapParamEnfant).isNull();

		verify(paramEnfantRepoMock, times(1)).findAll();
	}

	@Test
	public void devraitTrouverLEmployeParNom() {

		ParametrageEmploye employe = new ParametrageEmploye();
		employe.setPrenom("employeTest");
		doReturn(employe).when(paramEmployeRepoMock).findBy_id(Mockito.any(ObjectId.class));

		ParametrageEmploye paramEmploye = parametrageBlo.findEmployeParId("5baff2462efb71c0790b6e55");

		assertThat(paramEmploye).isNotNull();
		assertThat(paramEmploye.getPrenom()).isEqualTo("employeTest");

		verify(paramEmployeRepoMock, times(1)).findBy_id(Mockito.any(ObjectId.class));
	}

	@Test
	public void neDevraitPasTrouverDEmploye() {

		doReturn(null).when(paramEmployeRepoMock).findBy_id(Mockito.any(ObjectId.class));

		ParametrageEmploye paramEmploye = parametrageBlo.findEmployeParId("5baff2462efb71c0790b6e55");

		assertThat(paramEmploye).isNull();

		verify(paramEmployeRepoMock, times(1)).findBy_id(Mockito.any(ObjectId.class));
	}
}
