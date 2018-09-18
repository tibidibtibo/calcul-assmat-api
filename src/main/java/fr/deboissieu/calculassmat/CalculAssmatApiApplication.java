package fr.deboissieu.calculassmat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("fr.deboissieu.calculassmat.*")
public class CalculAssmatApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculAssmatApiApplication.class, args);
	}

	// @Bean(name = "enfantLouise")
	// public ParametrageEnfant
	// getParametrageLouise(@Value("louise.heure.normale.lundi") Float lundi,
	// @Value("louise.heure.normale.mardi") Float mardi,
	// @Value("louise.heure.normale.mercredi") Float mercredi,
	// @Value("louise.heure.normale.jeudi") Float jeudi,
	// @Value("louise.heure.normale.vendredi") Float vendredi,
	// @Value("louise.heure.normale.samedi") Float samedi,
	// @Value("louise.heure.normale.dimanche") Float dimanche) {
	// return new ParametrageEnfant(lundi, mardi, mercredi, jeudi, vendredi, samedi,
	// dimanche);
	// }
	//
	// @Bean(name = "enfantJosephine")
	// public ParametrageEnfant
	// getParametragejosephine(@Value("josephine.heure.normale.lundi") Float lundi,
	// @Value("josephine.heure.normale.mardi") Float mardi,
	// @Value("josephine.heure.normale.mercredi") Float mercredi,
	// @Value("josephine.heure.normale.jeudi") Float jeudi,
	// @Value("josephine.heure.normale.vendredi") Float vendredi,
	// @Value("josephine.heure.normale.samedi") Float samedi,
	// @Value("josephine.heure.normale.dimanche") Float dimanche) {
	// return new ParametrageEnfant(lundi, mardi, mercredi, jeudi, vendredi, samedi,
	// dimanche);
	// }
}
