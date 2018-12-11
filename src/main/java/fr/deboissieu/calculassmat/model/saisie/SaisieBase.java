package fr.deboissieu.calculassmat.model.saisie;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SaisieBase {

	private Date dateSaisie;

	private Integer nbDejeuners;

	private Integer nbGouters;

	private Integer nbArEcole;

	private Double autresDeplacementKm;

}
