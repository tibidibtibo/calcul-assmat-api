package fr.deboissieu.calculassmat.model.saisie;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaisieEnfantRequest implements Serializable {

	private static final long serialVersionUID = -3964371014259791586L;

	@NotNull
	private String enfant;

	@NotNull
	private String employe;

	private Integer autreKm;

	private Integer nbArEcole;

	private Integer nbDejeuner;

	private Integer nbGouter;

	private Boolean saisie;

	@NotNull
	private Date heureArrivee;

	@NotNull
	private Date heureDepart;

	public static SaisieJournaliere toSaisieJournaliere(SaisieEnfantRequest saisieRequest) {
		SaisieJournaliere saisie = new SaisieJournaliere();
		// TODO : mapping
		return saisie;
	}
}
