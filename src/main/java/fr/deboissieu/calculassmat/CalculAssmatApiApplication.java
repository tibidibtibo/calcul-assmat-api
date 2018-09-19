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
}
