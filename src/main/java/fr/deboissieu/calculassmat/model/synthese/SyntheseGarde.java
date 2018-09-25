package fr.deboissieu.calculassmat.model.synthese;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
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

	@Getter
	@Setter
	private Double nbHeuresNormalesContrat;

	@Getter
	@Setter
	private Double nbHeuresNormalesReelles;

	@Getter
	@Setter
	private Double nbHeuresComplementaires;

	@Getter
	@Setter
	private Double salaireHoraireNetHeureNormale;

	@Getter
	@Setter
	private Double salaireNetTotal;

	@Getter
	@Setter
	private Double indemnitesEntretien;

	@Getter
	@Setter
	private Double indemnitesRepas;

	@Getter
	@Setter
	private Double indemnitesKm;

	public SyntheseGarde(int mois, int annee) {
		this.mois = Integer.toString(mois);
		this.annee = Integer.toString(annee);
	}

}
