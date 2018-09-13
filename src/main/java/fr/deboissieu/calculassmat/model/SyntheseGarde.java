package fr.deboissieu.calculassmat.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class SyntheseGarde implements Serializable {

	private static final long serialVersionUID = 622457511701268495L;

	@Getter
	@Setter
	private String mois;

	@Getter
	@Setter
	private String annee;

	@Getter
	@Setter
	private int nbJoursTravailles;
}
