package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.deboissieu.calculassmat.TestUtils;
import fr.deboissieu.calculassmat.bl.impl.CalculBloImpl;
import fr.deboissieu.calculassmat.commons.filestorage.FileStorageService;
import fr.deboissieu.calculassmat.commons.mocks.ResourceMock;
import fr.deboissieu.calculassmat.commons.mocks.WorkbookMock;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CalculBloTest.Config.class })
public class CalculBloTest {

	public static class Config {

		@Bean
		CalculBlo getCalculBlo() {
			return new CalculBloImpl();
		}

		@Bean
		ParametrageBlo getParametrageBlo() {
			return Mockito.mock(ParametrageBlo.class);
		}

		@Bean
		ExcelFileBlo getExcelFileBlo() {
			return Mockito.mock(ExcelFileBlo.class);
		}

		@Bean
		SyntheseBlo getSyntheseBlo() {
			return Mockito.mock(SyntheseBlo.class);
		}

		@Bean
		ArchivesBlo getArchivesBlo() {
			return Mockito.mock(ArchivesBlo.class);
		}

		@Bean
		FileStorageService getFileStorageService() {
			return Mockito.mock(FileStorageService.class);
		}
	}

	@Resource
	ExcelFileBlo excelFileBloMock;

	@Resource
	SyntheseBlo syntheseBloMock;

	@Resource
	FileStorageService fileStorageServiceMock;

	@Resource
	CalculBlo calculBlo;

	@Resource
	ParametrageBlo parametrageBloMock;

	@Before
	public void before() {
		Mockito.reset(excelFileBloMock, syntheseBloMock);
	}

	@Test
	public void devraitCalculerLaSyntheseMensuelle()
			throws Exception {

		// Arrange
		ParametrageEmploye paramEmploye = TestUtils.getParametrageEmploye();
		Map<String, ParametrageEnfant> mapParamEnfant = getMapParamEnfant2();

		doReturn(paramEmploye).when(parametrageBloMock).findEmployeParId(Mockito.anyString());
		doReturn(mapParamEnfant).when(parametrageBloMock).findAllParamsEnfants();

		Mockito.doReturn(new WorkbookMock()).when(excelFileBloMock).openFileAsWorkbook(null);
		Mockito.doReturn(Arrays.asList(new WorkbookMock())).when(excelFileBloMock).extractDataFromWorkbook(
				Mockito.any(Workbook.class),
				Mockito.anyInt());
		org.springframework.core.io.Resource resource = new ResourceMock();
		Mockito.doReturn(resource).when(fileStorageServiceMock).loadFileAsResource(Mockito.anyString());

		Collection<SyntheseGarde> syntheses = new ArrayList<>();
		SyntheseGarde synthese = new SyntheseGarde(9, 2018, "employe");
		syntheses.add(synthese);
		Mockito.doReturn(syntheses).when(syntheseBloMock).calculerFraisMensuels(Mockito.anyCollection(),
				Mockito.anyInt(), Mockito.anyInt(), Mockito.any(Map.class));

		Collection<SyntheseGarde> syntheseResponse = calculBlo.calculerSyntheseGardeFromFilename(9, 2018, "path");

		assertThat(syntheseResponse).isNotEmpty().hasSize(1);
		assertThat(syntheseResponse).extracting("annee").contains("2018");
		assertThat(syntheseResponse).extracting("mois").contains("9");
	}

	@Test
	public void devraitPlanterACauseEchecDeChargementDuFichier()
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		Mockito.doReturn(Arrays.asList(new WorkbookMock())).when(excelFileBloMock).extractDataFromWorkbook(
				Mockito.any(Workbook.class),
				Mockito.anyInt());
		org.springframework.core.io.Resource resource = new ResourceMock();
		Mockito.doReturn(resource).when(fileStorageServiceMock).loadFileAsResource(Mockito.anyString());
		Mockito.doThrow(new InvalidFormatException("Fichier pourri !")).when(excelFileBloMock)
				.openFileAsWorkbook(null);

		try {
			calculBlo.calculerSyntheseGardeFromFilename(9, 2018, "path");
			fail();
		} catch (Exception e) {
			assertThat(e).isNotNull();
		}

	}

	public static Map<String, ParametrageEnfant> getMapParamEnfant2() {
		Map<String, ParametrageEnfant> mapParamEnfants = new HashMap<>();

		ParametrageEnfant enfant2 = TestUtils.buildParametrageEnfant("enfant2", "PERISCOLAIRE", 104d, 2d, 3.2d);
		enfant2.setHeuresNormales(TestUtils.getHeuresNormales(2d, 1d, 0d, 2d, 1d, 0d, 0d));
		enfant2.setHorairesEcole(TestUtils.getHorairesEcole());
		mapParamEnfants.put(enfant2.getNom(), enfant2);

		return mapParamEnfants;
	}
}
