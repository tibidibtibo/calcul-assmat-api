package fr.deboissieu.calculassmat.model.saisie;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SaisieJournaliere {

	private Date dateSaisie;

	private String prenom;

	private String heureArrivee;

	private String heureDepart;

	private Integer nbDejeuners;

	private Integer nbGouters;

	private Integer nbArEcole;

	private Double autresDeplacementKm;

}
