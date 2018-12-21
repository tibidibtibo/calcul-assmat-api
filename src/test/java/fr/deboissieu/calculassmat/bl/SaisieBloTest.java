package fr.deboissieu.calculassmat.bl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

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
import fr.deboissieu.calculassmat.dl.SaisieRepository;
import fr.deboissieu.calculassmat.model.saisie.Saisie;
import fr.deboissieu.calculassmat.model.saisie.SaisieEnfantDto;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SaisieBloTest.Config.class })
public class SaisieBloTest {

	@Resource
	SaisieBlo saisieBlo;

	@Resource
	SaisieRepository saisieRepositoryMock;

	@Before
	public void before() {
		Mockito.reset(saisieRepositoryMock);
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
		// Collection<SaisieEnfantDto> findSaisiesByMonth(Integer month, Integer year);
		// TODO
	}

	@Test
	public void devraitImporterLeFichierDeSaisies() {
		// void importerFichierSaisie(Integer numeroMois, Integer numeroAnnee, String
		// fileName)
		// TODO
	}

	@Test
	public void devraitSupprimerUneSaisie() {
		// void supprimerSaisie(String identifiant);
		// TODO
	}

	public static class Config {

		@Bean
		SaisieBlo getSaisieBlo() {
			return new SaisieBloImpl();
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
		ExcelFileBlo getExcelFileBlo() {
			return Mockito.mock(ExcelFileBlo.class);
		}

	}

}
