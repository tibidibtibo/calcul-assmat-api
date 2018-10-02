package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.deboissieu.calculassmat.bl.impl.ValidationBloImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ValidationBloTest.Config.class })
public class ValidationBloTest {

	public static class Config {

		@Bean
		ValidationBlo getValidationBlo() {
			return new ValidationBloImpl();
		}
	}

	@Resource
	ValidationBlo validationBlo;

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

}
