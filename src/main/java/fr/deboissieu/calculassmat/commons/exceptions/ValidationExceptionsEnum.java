package fr.deboissieu.calculassmat.commons.exceptions;

import lombok.Getter;
import lombok.Setter;

public enum ValidationExceptionsEnum {

	V001("V-001", "Paramètre d'entrée invalide. Usage : année valide et mois de 1 à 12."), V101("V-101",
			"Données invalides pour le calcul de la synthèse.");

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
