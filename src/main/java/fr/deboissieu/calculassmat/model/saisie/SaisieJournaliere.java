package fr.deboissieu.calculassmat.model.saisie;

import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class SaisieJournaliere {

	@Getter
	@Setter
	private Date dateSaisie;

	@Getter
	@Setter
	private Set<String> qui;

	@Getter
	@Setter
	private String action;

	@Getter
	@Setter
	private String heureAction;

	@Getter
	@Setter
	private Set<String> repas;

	@Getter
	@Setter
	private Set<String> deplacements;

	@Getter
	@Setter
	private Integer autresDeplacementKm;

}
