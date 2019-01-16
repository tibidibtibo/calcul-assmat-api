package fr.deboissieu.calculassmat.commons;

import java.util.Arrays;

import lombok.Getter;

/**
 * Valeurs de "typeGarde"
 */
public enum TypeGardeEnum {
	TEMPS_PLEIN("TEMPS_PLEIN", "Temps plein"), PERISCOLAIRE("PERISCOLAIRE", "Périscolaire");

	@Getter
	private String typeGarde;

	@Getter
	private String libelle;

	TypeGardeEnum(String typeGarde, String libelle) {
		this.typeGarde = typeGarde;
		this.libelle = libelle;
	}

	public static TypeGardeEnum fromString(final String value) {
		return Arrays.stream(values()).filter(enumEntry -> enumEntry.typeGarde.equalsIgnoreCase(value)).findFirst()
				.orElse(null);
	}
}
