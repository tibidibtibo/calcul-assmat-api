package fr.deboissieu.calculassmat.bl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.deboissieu.calculassmat.bl.impl.CalculBloImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CalculBloImplTest.Config.class })
public class CalculBloImplTest {

	public static class Config {

		@Bean
		CalculBlo getCalculBlo() {
			return new CalculBloImpl();
		}

		@Bean
		ExcelFileBlo getExcelFileBlo() {
			return Mockito.mock(ExcelFileBlo.class);
		}
	}

	@Mock
	ExcelFileBlo excelFileBloMock;

	@Resource
	CalculBlo calculBlo;

	@Before
	public void before() {
		Mockito.reset(excelFileBloMock);
	}

	// FIXME : faire les TU

	@Test
	public void devraitCalculerLaSyntheseMensuelle()
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		Mockito.doReturn(null).when(excelFileBloMock).openFile(Mockito.anyString());
		Mockito.doReturn(null).when(excelFileBloMock).extractDataFromWorkbook(Mockito.any(Workbook.class),
				Mockito.anyInt());

		Response response = calculBlo.calculerSyntheseGarde(9, 2018);

		assertNotNull(response);
	}
}
