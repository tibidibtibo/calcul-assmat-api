package fr.deboissieu.calculassmat.model;

import java.util.EnumMap;

import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import lombok.Getter;
import lombok.Setter;

public class NombreJoursTravailles {

	@Getter
	@Setter
	private int nbJoursTotal;

	@Getter
	@Setter
	private EnumMap<PrenomEnum, Integer> nbJoursParPersonne;
}
