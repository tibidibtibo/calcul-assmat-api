package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.deboissieu.calculassmat.bl.impl.ValidationBloImpl;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;

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

		String expectedMessage = "Données invalides pour le calcul de la synthèse.";

		try {
			validationBlo.validerAvantCalcul(new ArrayList<>(), null, null);
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		try {
			validationBlo.validerAvantCalcul(null, null, null);
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

		doReturn(new ParametrageEmploye()).when(parametrageBloMock).findEmployeParNom("nom");
		assertThat(validationBlo.validerPathParamNomAssmat("nom")).isEqualTo("nom");
	}

	@Test
	public void devraitValiderLeNomAvecRechercheEnBase() {

		String expectedMessage = "Erreur V-003 : Employé inconnu. - Variable saisie : nom";

		doReturn(null).when(parametrageBloMock).findEmployeParNom("nom");
		try {
			validationBlo.validerPathParamNomAssmat("nom");
			fail();
		} catch (ValidationException ve) {
			assertThat(ve.getMessage())
					.contains(expectedMessage);
		}

		doReturn(new ParametrageEmploye()).when(parametrageBloMock).findEmployeParNom("nom");
		assertThat(validationBlo.validerPathParamNomAssmat("nom")).isEqualTo("nom");
	}

}
