package fr.deboissieu.calculassmat.model.synthese;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Indemnites implements Serializable {

	private static final long serialVersionUID = -2926911096774447489L;

	private Double indemnitesEntretien;

	private Double indemnitesRepas;

	private Double indemnitesKm;

}
