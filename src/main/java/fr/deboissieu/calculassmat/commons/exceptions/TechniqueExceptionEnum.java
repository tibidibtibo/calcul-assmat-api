package fr.deboissieu.calculassmat.commons.exceptions;

import lombok.Getter;
import lombok.Setter;

public enum TechniqueExceptionEnum {

	T001("T-001", "Impossible de calculer les horaires de {} le {}.");

	@Getter
	@Setter
	private String code;

	@Getter
	@Setter
	private String message;

	TechniqueExceptionEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String toString(Exception exception) {
		return "Erreur " + this.code + " : " + this.message + " - Erreur : " + exception;
	}

}
