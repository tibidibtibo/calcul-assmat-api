package fr.deboissieu.calculassmat.model;

import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import lombok.Getter;
import lombok.Setter;

public class HeuresPersonnelles {

	@Getter
	@Setter
	private PrenomEnum prenom;

	@Getter
	@Setter
	private Heures heures;
}
