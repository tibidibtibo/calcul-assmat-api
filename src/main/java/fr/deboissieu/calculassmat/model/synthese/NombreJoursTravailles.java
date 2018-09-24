package fr.deboissieu.calculassmat.model.synthese;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class NombreJoursTravailles {

	@Getter
	@Setter
	private int nbJoursTotal;

	@Getter
	@Setter
	private Map<String, Integer> nbJoursParPersonne;
}
