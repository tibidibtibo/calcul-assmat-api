package fr.deboissieu.calculassmat.model.saisie;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SaisieJournaliere implements Serializable {

	private static final long serialVersionUID = -1115427582930036226L;

	private Date dateSaisie;

	private String employe;

	private String enfant;

	private String heureArrivee;

	private String heureDepart;

	private Integer nbDejeuners;

	private Integer nbGouters;

	private Integer nbArEcole;

	private Double autresDeplacementKm;

}
