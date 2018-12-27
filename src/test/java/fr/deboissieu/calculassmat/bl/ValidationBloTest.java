package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.deboissieu.calculassmat.TestUtils;
import fr.deboissieu.calculassmat.bl.parametrage.ParametrageBlo;
import fr.deboissieu.calculassmat.bl.validation.ValidationBlo;
import fr.deboissieu.calculassmat.bl.validation.impl.ValidationBloImpl;
import fr.deboissieu.calculassmat.model.certification.CertificationRequest;
import fr.deboissieu.calculassmat.model.certification.SaisieCertification;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.saisie.SaisieEnfantDto;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ValidationBloTest.Config.class })
public class ValidationBloTest {

	public static class Config {

		@Bean
		ValidationBlo getValidationBlo() {
			return new ValidationBloImpl();
		}

		@Bean
		ParametrageBlo getParametrageBlo() {
			return Mockito.mock(ParametrageBlo.class);
		}

		@Bean
		Validator getValidator() {
			return Mockito.mock(Validator.class);
		}

	}

	@Resource
	ValidationBlo validationBlo;

	@Resource
	ParametrageBlo parametrageBloMock;

	@Before
	public void before() {
		Mockito.reset(parametrageBloMock);
	}

	@Test
	public void devraitValiderLesParametresDeLaRequeteCalcul() {
		assertThat(validationBlo.validerPathParamCalculMoisAnnee("001")).isEqualTo(1);
		assertThat(validationBlo.validerPathParamCalculMoisAnnee("10")).isEqualTo(10);
		assertThat(validationBlo.validerPathParamCalculMoisAnnee("-2")).isEqualTo(-2);
		assertThat(validationBlo.validerPathParamCalculMoisAnnee("2018")).isEqualTo(2018);
	}

	@Test
	public void devraitRefuserLesParametresDeLaRequeteCalcul() {

		String expectedMessage = "Erreur V-001 : Paramètre d'entrée invalide. Usage : année valide et mois de 1 à 12.";

		try {
			validationBlo.validerPathParamCalculMoisAnnee("abc");
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		try {
			validationBlo.validerPathParamCalculMoisAnnee("");
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		try {
			validationBlo.validerPathParamCalculMoisAnnee("  ");
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}
	}

	@Test
	public void devraitValiderLesDonneesAvantCalcul() {

		String expectedMessage = "Impossible de charger le paramétrage.";

		try {
			validationBlo.validerAvantCalcul(new ArrayList<>(), null);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		try {
			validationBlo.validerAvantCalcul(null, null);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}
	}

	@Test
	public void devraitValiderLeNom() {

		String expectedMessage = "Paramètre d'entrée invalide. Usage : nom de l'employé.";

		try {
			validationBlo.validerPathParamNomAssmat(" ");
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		try {
			validationBlo.validerPathParamNomAssmat("");
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		try {
			validationBlo.validerPathParamNomAssmat(null);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		doReturn(new ParametrageEmploye()).when(parametrageBloMock).findEmployeParId("nom");
		assertThat(validationBlo.validerPathParamNomAssmat("nom")).isEqualTo("nom");
	}

	@Test
	public void devraitValiderLeNomAvecRechercheEnBase() {

		String expectedMessage = "Erreur V-003 : Employé inconnu. - nom";

		doReturn(null).when(parametrageBloMock).findEmployeParId("nom");
		try {
			validationBlo.validerPathParamNomAssmat("nom");
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		doReturn(new ParametrageEmploye()).when(parametrageBloMock).findEmployeParId("nom");
		assertThat(validationBlo.validerPathParamNomAssmat("nom")).isEqualTo("nom");
	}

	@Test
	public void devraitValiderAvantArchivage() {

		String expectedMessage = "Données invalides pour l'archivage";

		try {
			validationBlo.validerAvantArchivage(null, TestUtils.buildSyntheseGarde(9, 2018, "employe"));
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		try {
			validationBlo.validerAvantArchivage(new ArrayList<>(), TestUtils.buildSyntheseGarde(9, 2018, "employe"));
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		try {
			Collection<SaisieJournaliere> saisie = new ArrayList<>();
			saisie.add(new SaisieJournaliere());
			validationBlo.validerAvantArchivage(saisie, null);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

	}

	@Test
	public void devraitValiderLaRequeteDeCreationSaisie() {

		String expectedMessage = "Saisie incorrecte";

		// Requête nulle
		try {
			SaisieRequest saisie = new SaisieRequest();
			validationBlo.validerSaisie(saisie);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		// Requête vide
		try {
			SaisieRequest saisie = new SaisieRequest();
			saisie.setSaisie(new ArrayList<>());
			validationBlo.validerSaisie(saisie);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		// Requête avec dateSaisie/enfant/employe nuls
		try {
			SaisieRequest saisie = new SaisieRequest();
			Collection<SaisieEnfantDto> saisies = new ArrayList<>();
			SaisieEnfantDto saisie1 = new SaisieEnfantDto();
			saisies.add(saisie1);
			saisie.setSaisie(saisies);
			validationBlo.validerSaisie(saisie);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		// Requête sans aucune heure
		try {
			SaisieRequest saisie = new SaisieRequest();
			Collection<SaisieEnfantDto> saisies = new ArrayList<>();
			SaisieEnfantDto saisie1 = new SaisieEnfantDto();
			saisie1.setDateSaisie(new Date());
			saisie1.setEmploye("employe");
			saisie1.setEnfant("enfant");
			saisies.add(saisie1);
			saisie.setSaisie(saisies);
			validationBlo.validerSaisie(saisie);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		// Requête valide
		try {
			SaisieRequest saisie = new SaisieRequest();
			Collection<SaisieEnfantDto> saisies = new ArrayList<>();
			SaisieEnfantDto saisie1 = new SaisieEnfantDto();
			saisie1.setDateSaisie(new Date());
			saisie1.setEmploye("employe");
			saisie1.setEnfant("enfant");
			saisie1.setHeureDepart(new Date());
			saisies.add(saisie1);
			saisie.setSaisie(saisies);
			validationBlo.validerSaisie(saisie);
		} catch (ValidationException ve) {
			fail();
		}

	}

	@Test
	public void devraitValiderLaRequeteDeCertification() {

		String expectedMessageVideNull = "Certification invalide : requête nulle ou vide !";
		String expectedMessageInvalide = "Certification invalide : saisie inconnue.";

		// Requete vide
		try {
			CertificationRequest requete = new CertificationRequest();
			validationBlo.validerCertification(requete);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessageVideNull);
		}

		// Requete nulle
		try {
			CertificationRequest requete = null;
			validationBlo.validerCertification(requete);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessageVideNull);
		}

		// Requete invalide
		try {
			CertificationRequest requete = new CertificationRequest();
			Collection<SaisieCertification> saisies = new ArrayList<>();
			SaisieCertification saisie1 = new SaisieCertification();
			saisie1.setId(null);
			saisies.add(saisie1);
			requete.setSaisies(saisies);

			validationBlo.validerCertification(requete);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessageInvalide);
		}

		// Requete valide
		try {
			CertificationRequest requete = new CertificationRequest();
			Collection<SaisieCertification> saisies = new ArrayList<>();
			SaisieCertification saisie1 = new SaisieCertification();
			saisie1.setId("abc");
			saisies.add(saisie1);
			requete.setSaisies(saisies);

			validationBlo.validerCertification(requete);

		} catch (Exception e) {
			fail();
		}

	}

}
