package fr.deboissieu.calculassmat.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SaisieJournaliere {

	@Getter
	@Setter
	private Date horodatage;

	@Getter
	@Setter
	private Date dateSaisie;

	@Getter
	@Setter
	private String qui;

	@Getter
	@Setter
	private String action;

	@Getter
	@Setter
	private Date heureAction;

	@Getter
	@Setter
	private String repas;

	@Getter
	@Setter
	private String deplacements;

	@Getter
	@Setter
	private Integer autresDeplacementKm;

}
