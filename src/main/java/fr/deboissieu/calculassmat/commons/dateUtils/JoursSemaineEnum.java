package fr.deboissieu.calculassmat.commons.dateUtils;

import lombok.Getter;

public enum JoursSemaineEnum {

	LUNDI(1), MARDI(2), MERCREDI(3), JEUDI(4), VENDREDI(5), SAMEDI(6), DIMANCHE(7);

	@Getter
	private int jour;

	JoursSemaineEnum(int jour) {
		this.jour = jour;
	}
}
