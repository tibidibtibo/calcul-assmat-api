package fr.deboissieu.calculassmat.model.saisie;

import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SaisieJournaliere {

	private Date dateSaisie;

	private Set<String> qui;

	private String action;

	private String heureAction;

	private Integer nbDejeunersLouise;

	private Integer nbDejeunersJosephine;

	private Integer nbGoutersLouise;

	private Integer nbGoutersJosephine;

	private Integer nbArEcoleLouise;

	private Double autresDeplacementKm;

}
