package fr.deboissieu.calculassmat.commons.exceptions;

import lombok.Getter;
import lombok.Setter;

public enum ValidationExceptionsEnum {

	V001("V-001", "Paramètre d'entrée invalide. Usage : année valide et mois de 1 à 12."),
	V002("V-002", "Paramètre d'entrée invalide. Usage : nom de l'employé."),
	V003("V-003", "Employé inconnu."),
	V004("V-004", "Impossible de récupérer l'employé dans le paramétage."),
	V005("V-005", "Saisie incorrecte"),
	V006("V-006", "Impossible de récupérer l'enfant dans le paramétrage."),

	V010("V-010", "Certification invalide : requête nulle ou vide !"),
	V011("V-011", "Certification invalide : saisie inconnue."),
	V012("V-012", "Certification déjà créée !"),
	V013("V-013", "Certification introuvable !"),

	V101("V-101", "Impossible de charger le paramétrage."),
	V102("V-102", "Données invalides pour l'archivage."),
	V103("V-103", "Aucunes données pour le mois sélectionné.");

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

	public String toString(String variable, Exception exception) {
		return "Erreur " + this.code + " : " + this.message + " - Variable : " + variable + " / " + exception;
	}

	public String toString(String customMsg) {
		return "Erreur " + this.code + " : " + this.message + " - " + customMsg;
	}

	public String toString() {
		return "Erreur " + this.code + " : " + this.message;
	}
}
