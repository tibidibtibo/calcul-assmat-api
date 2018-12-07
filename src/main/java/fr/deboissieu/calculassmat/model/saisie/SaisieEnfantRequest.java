package fr.deboissieu.calculassmat.model.saisie;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaisieEnfantRequest implements Serializable {

	private static final long serialVersionUID = -3964371014259791586L;

	private String employe;

	private Integer autreKm;

	private Integer nbArEcole;

	private Integer nbDejeuner;

	private Integer nbGouter;

	private Boolean saisie;

	private Date heureArrivee;

	private Date heureDepart;
}
