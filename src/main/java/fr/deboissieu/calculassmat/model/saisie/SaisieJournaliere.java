package fr.deboissieu.calculassmat.model.saisie;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SaisieJournaliere extends SaisieBase implements Serializable {

	private static final long serialVersionUID = -1115427582930036226L;

	private String employe;

	private String enfant;

	private String heureArrivee;

	private String heureDepart;

}
