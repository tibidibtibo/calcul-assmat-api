package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

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

import fr.deboissieu.calculassmat.bl.impl.CalculBloImpl;
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
		ExcelFileBlo getExcelFileBlo() {
			return Mockito.mock(ExcelFileBlo.class);
		}

		@Bean
		SyntheseBlo getSyntheseBlo() {
			return Mockito.mock(SyntheseBlo.class);
		}
	}

	@Resource
	ExcelFileBlo excelFileBloMock;

	@Resource
	SyntheseBlo syntheseBloMock;

	@Resource
	CalculBlo calculBlo;

	@Before
	public void before() {
		Mockito.reset(excelFileBloMock, syntheseBloMock);
	}

	@Test
	public void devraitCalculerLaSyntheseMensuelle()
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		Mockito.doReturn(null).when(excelFileBloMock).openFile(Mockito.anyString());
		Mockito.doReturn(null).when(excelFileBloMock).extractDataFromWorkbook(Mockito.any(Workbook.class),
				Mockito.anyInt());

		SyntheseGarde synthese = new SyntheseGarde(9, 2018);
		Mockito.doReturn(synthese).when(syntheseBloMock).calculerFraisMensuels(Mockito.anyCollection(),
				Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());

		Response response = calculBlo.calculerSyntheseGarde(9, 2018, "nom");

		assertThat(response).isNotNull();
		SyntheseGarde syntheseResponse = (SyntheseGarde) response.getEntity();
		assertThat(syntheseResponse).isNotNull();
		assertThat(syntheseResponse.getAnnee()).isEqualTo("2018");
		assertThat(syntheseResponse.getMois()).isEqualTo("9");
	}

	@Test
	public void devraitPlanterACauseEchecDeChargementDuFichier()
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		Mockito.doThrow(new InvalidFormatException("Fichier pourri !")).when(excelFileBloMock)
				.openFile(Mockito.anyString());

		Response response = calculBlo.calculerSyntheseGarde(9, 2018, "nom");

		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(500);
	}
}
