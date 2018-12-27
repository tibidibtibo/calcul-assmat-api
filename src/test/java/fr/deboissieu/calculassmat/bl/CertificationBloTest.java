package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.deboissieu.calculassmat.TestUtils;
import fr.deboissieu.calculassmat.bl.certification.CertificationBlo;
import fr.deboissieu.calculassmat.bl.certification.impl.CertificationBloImpl;
import fr.deboissieu.calculassmat.bl.synthese.SyntheseBlo;
import fr.deboissieu.calculassmat.dl.CertificationRepository;
import fr.deboissieu.calculassmat.dl.SaisieRepository;
import fr.deboissieu.calculassmat.model.certification.Certification;
import fr.deboissieu.calculassmat.model.certification.CertificationRequest;
import fr.deboissieu.calculassmat.model.certification.SaisieCertification;
import fr.deboissieu.calculassmat.model.saisie.Saisie;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CertificationBloTest.Config.class })
public class CertificationBloTest {

	@Resource
	CertificationBlo certificationBlo;

	@Resource
	CertificationRepository certifRepositoryMock;

	@Resource
	SaisieRepository saisieRepositoryMock;

	@Resource
	SyntheseBlo syntheseBloMock;

	@Before
	public void before() {
		Mockito.reset(saisieRepositoryMock);
		Mockito.reset(certifRepositoryMock);
		Mockito.reset(syntheseBloMock);
	}

	@Test
	public void devraitCertifierLeMois() {

		// Arrange
		CertificationRequest requete = new CertificationRequest();
		Collection<SaisieCertification> saisies = new ArrayList<>();
		SaisieCertification saisieCertif1 = new SaisieCertification();
		saisieCertif1.setId("5baff2462efb71c0790b6e11");
		saisies.add(saisieCertif1);
		requete.setSaisies(saisies);

		Saisie saisie1 = new Saisie();
		saisie1.set_id(new ObjectId("5baff2462efb71c0790b6e11"));
		saisie1.setNbDejeuners(7);
		doReturn(Arrays.asList(saisie1)).when(saisieRepositoryMock).findAllById(Mockito.anyCollection());

		Collection<SyntheseGarde> syntheses = new ArrayList<>();
		syntheses.add(TestUtils.buildSyntheseGarde(12, 2018, "employe1"));
		syntheses.add(TestUtils.buildSyntheseGarde(11, 2018, "employe2"));
		doReturn(syntheses).when(syntheseBloMock).calculerSynthese(Mockito.anyCollection(), Mockito.anyInt(),
				Mockito.anyInt());

		// Act
		certificationBlo.certifier(requete, 12, 2018);

		// Assert
		verify(certifRepositoryMock, times(1)).findByMonth(12, 2018);
		ArgumentCaptor<Certification> certifArgCaptor = ArgumentCaptor.forClass(Certification.class);
		verify(certifRepositoryMock, times(1)).save(certifArgCaptor.capture());
		assertThat(certifArgCaptor.getValue()).isNotNull();
		assertThat(certifArgCaptor.getValue().getMonth()).isEqualTo(12);
		assertThat(certifArgCaptor.getValue().getYear()).isEqualTo(2018);
		assertThat(certifArgCaptor.getValue().getSaisies()).isNotEmpty().hasSize(1);
		assertThat(certifArgCaptor.getValue().getSaisies()).extracting("id")
				.containsExactly("5baff2462efb71c0790b6e11");
		assertThat(certifArgCaptor.getValue().getSyntheses()).isNotEmpty().hasSize(2);
		assertThat(certifArgCaptor.getValue().getSyntheses()).extracting("mois").containsOnly("12", "11");
		assertThat(certifArgCaptor.getValue().getSyntheses()).extracting("annee").containsOnly("2018");
		assertThat(certifArgCaptor.getValue().getSyntheses()).extracting("nomEmploye").containsOnly("employe1",
				"employe2");

	}

	@Test
	public void neDevraitPasCertifierLeMois() {

		// Arrange
		CertificationRequest requete = new CertificationRequest();
		Collection<SaisieCertification> saisies = new ArrayList<>();
		SaisieCertification saisie1 = new SaisieCertification();
		saisie1.setId("abc");
		saisies.add(saisie1);
		requete.setSaisies(saisies);
		doReturn(new Certification()).when(certifRepositoryMock).findByMonth(12, 2018);

		// Act
		try {
			certificationBlo.certifier(requete, 12, 2018);
			fail();
		} catch (ValidationException ve) {
			// Assert
			assertThat(ve.getMessage())
					.contains("Erreur V-012 : Certification déjà créée ! - 12/2018");

		}

		// Assert
		verify(certifRepositoryMock, times(1)).findByMonth(12, 2018);
		verify(certifRepositoryMock, never()).save(Mockito.any(Certification.class));

	}

	public static class Config {

		@Bean
		CertificationBlo getCertificationBlo() {
			return new CertificationBloImpl();
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
		SyntheseBlo getSyntheseBlo() {
			return Mockito.mock(SyntheseBlo.class);
		}

	}

}
