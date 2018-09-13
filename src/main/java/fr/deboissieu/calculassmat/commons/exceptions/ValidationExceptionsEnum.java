package fr.deboissieu.calculassmat.commons.exceptions;

import lombok.Getter;
import lombok.Setter;

public enum ValidationExceptionsEnum {

	V001("V-001", "Paramètre d'entrée invalide. Usage : mois de 1 à 12");

	@Getter
	@Setter
	private String code;

	@Getter
	@Setter
	private String message;

	ValidationExceptionsEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String toString(String pathParam, Exception exception) {
		return "Erreur " + this.code + " : " + this.message + " - Variable saisie : " + pathParam + " / " + exception;
	}
}
