package fr.deboissieu.calculassmat.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Heures {

	@Getter
	@Setter
	private Date heureArrivee;

	@Getter
	@Setter
	private Date heureDepart;
}
