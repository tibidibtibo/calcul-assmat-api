package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.IterableUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.deboissieu.calculassmat.bl.parametrage.ParametrageBlo;
import fr.deboissieu.calculassmat.bl.saisie.ExcelFileBlo;
import fr.deboissieu.calculassmat.bl.saisie.SaisieBlo;
import fr.deboissieu.calculassmat.bl.saisie.impl.SaisieBloImpl;
import fr.deboissieu.calculassmat.bl.synthese.SyntheseBlo;
import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.commons.filestorage.FileStorageService;
import fr.deboissieu.calculassmat.commons.mocks.ResourceMock;
import fr.deboissieu.calculassmat.commons.mocks.WorkbookMock;
import fr.deboissieu.calculassmat.dl.CertificationRepository;
import fr.deboissieu.calculassmat.dl.SaisieRepository;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.Saisie;
import fr.deboissieu.calculassmat.model.saisie.SaisieEnfantDto;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SaisieBloTest.Config.class })
public class SaisieBloTest {

	@Resource
	SaisieBlo saisieBlo;

	@Resource
	SaisieRepository saisieRepositoryMock;

	@Resource
	CertificationRepository certifRepositoryMock;

	@Resource
	SyntheseBlo syntheseBloMock;

	@Resource
	ExcelFileBlo excelFileBloMock;

	@Resource
	FileStorageService fileStorageServiceMock;

	@Resource
	ParametrageBlo parametrageBloMock;

	@Before
	public void before() {
		Mockito.reset(saisieRepositoryMock);
		Mockito.reset(certifRepositoryMock);
		Mockito.reset(parametrageBloMock);
		Mockito.reset(excelFileBloMock);
		Mockito.reset(fileStorageServiceMock);
		Mockito.reset(syntheseBloMock);
	}

	@Test
	public void devraitEnregistrerLaSaisie() {

		SaisieRequest saisie = new SaisieRequest();
		Collection<SaisieEnfantDto> saisies = new ArrayList<>();
		SaisieEnfantDto saisie1 = new SaisieEnfantDto();
		saisie1.setDateSaisie(new Date());
		saisie1.setEmploye("5baff2462efb71c0790b6e55");
		saisie1.setEnfant("abaff2462efb71c0790b6e44");
		saisies.add(saisie1);
		saisie.setSaisie(saisies);

		saisieBlo.enregistrerSaisie(saisie);

		Mockito.verify(saisieRepositoryMock, Mockito.times(1)).save(Mockito.any(Saisie.class));
	}

	@Test
	public void devraitRechercherLaSaisieParMoisAnnee() {

		// Arrange
		Collection<Saisie> saisiesToReturn = new ArrayList<>();
		Saisie saisie1 = new Saisie();
		saisie1.set_id(new ObjectId("5baff2462efb71c0790b6e55"));
		saisie1.setEnfantId(new ObjectId("abcff2462efb71c0790b6e55"));
		saisie1.setEmployeId(new ObjectId("defff2462efb71c0790b6e55"));
		saisiesToReturn.add(saisie1);
		doReturn(saisiesToReturn).when(saisieRepositoryMock).findSaisieBetween(Mockito.any(Date.class),
				Mockito.any(Date.class));
		Map<String, ParametrageEnfant> mapParamEnfants = new HashMap<>();
		ParametrageEnfant paramEnfant1 = new ParametrageEnfant();
//		paramEnfant1.set_id(); // TODO : finit test
		mapParamEnfants.put("abcff2462efb71c0790b6e55", paramEnfant1);
		Map<String, ParametrageEmploye> mapParamEmployes = new HashMap<>();
		doReturn(null).when(parametrageBloMock).getMapIdParamsEnfants();
		doReturn(null).when(parametrageBloMock).getMapIdParamsEmployes();

		// Act
		Collection<SaisieEnfantDto> saisies = saisieBlo.findSaisiesByMonth(12, 2019);

		// Assert
		assertThat(saisies).isNotEmpty().hasSize(1);
		assertThat(IterableUtils.get(saisies, 0)).isNotNull();
		assertThat(IterableUtils.get(saisies, 0).getId()).isEqualTo("5baff2462efb71c0790b6e55");

		Mockito.verify(saisieRepositoryMock, times(1)).findSaisieBetween(DateUtils.getDate(2019, 12, 1),
				DateUtils.getDate(2020, 1, 1));
	}

	@Test
	public void devraitImporterLeFichierDeSaisies() throws InvalidFormatException, IOException {

		// Arrange
		Workbook workbookMock = Mockito.spy(new WorkbookMock());
		Mockito.doReturn(workbookMock).when(excelFileBloMock).openWorkbook(Mockito.anyString());
		Collection<SaisieJournaliere> saisies = new ArrayList<>();
		SaisieJournaliere saisie1 = new SaisieJournaliere();
		saisie1.setEmploye("employe");
		saisie1.setEnfant("enfant");
		saisies.add(saisie1);
		Mockito.doReturn(saisies).when(excelFileBloMock).extractDataFromWorkbook(Mockito.any(Workbook.class),
				Mockito.anyInt(), Mockito.anyInt());
		ParametrageEmploye employe = new ParametrageEmploye();
		employe.setNom("employe");
		Mockito.doReturn(Arrays.asList(employe)).when(parametrageBloMock).getAllEmployes();
		ParametrageEnfant enfant = new ParametrageEnfant();
		enfant.setNom("enfant");
		Map<String, ParametrageEnfant> mapParamEnfant = new HashMap<>();
		mapParamEnfant.put("enfant", enfant);
		Mockito.doReturn(mapParamEnfant).when(parametrageBloMock).getMapNomParamsEnfants();
		org.springframework.core.io.Resource resource = new ResourceMock();
		Mockito.doReturn(resource).when(fileStorageServiceMock).loadFileAsResource(Mockito.anyString());

		// Act
		saisieBlo.importerFichierSaisie(12, 2018, "path");

		// Assert
		verify(workbookMock, times(1)).close();
		verify(excelFileBloMock, times(1)).openWorkbook("path");
		verify(excelFileBloMock, times(1)).extractDataFromWorkbook(workbookMock, 12, 2018);
		verify(parametrageBloMock, times(1)).getMapNomParamsEnfants();
		verify(parametrageBloMock, times(1)).findAllEmployes();
		verify(saisieRepositoryMock, times(1)).saveAll(Mockito.anyCollection());
	}

	@Test
	public void devraitSupprimerUneSaisie() {

		// Arrange
		String identifiant = "5baff2462efb71c0790b6e55";

		// Act
		saisieBlo.supprimerSaisie(identifiant);

		// Assert
		Mockito.verify(saisieRepositoryMock, times(1)).deleteById(new ObjectId(identifiant));

	}

	public static class Config {

		@Bean
		SaisieBlo getSaisieBlo() {
			return new SaisieBloImpl();
		}

		@Bean
		CertificationRepository getCertificationRepository() {
			return Mockito.mock(CertificationRepository.class);
		}

		@Bean
		SaisieRepository getSaisieRepository() {
			return Mockito.mock(SaisieRepository.class);
		}

		@Bean
		ParametrageBlo getParametrageBlo() {
			return Mockito.mock(ParametrageBlo.class);
		}

		@Bean
		SyntheseBlo getSyntheseBlo() {
			return Mockito.mock(SyntheseBlo.class);
		}

		@Bean
		ExcelFileBlo getExcelFileBlo() {
			return Mockito.mock(ExcelFileBlo.class);
		}

		@Bean
		FileStorageService getFileStorageService() {
			return Mockito.mock(FileStorageService.class);
		}

	}

}
