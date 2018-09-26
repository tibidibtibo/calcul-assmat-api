package fr.deboissieu.calculassmat.model.synthese;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SyntheseGarde implements Serializable {

	private static final long serialVersionUID = 622457511701268495L;

	private String mois;

	private String annee;

	private int nbJoursTravailles;

	private Double nbHeuresNormalesContrat;

	private Double nbHeuresNormalesReelles;

	private Double nbHeuresComplementaires;

	private Double salaireHoraireNetHeureNormale;

	private Double salaireNetTotalSansConges;

	private Double congesPayes;

	private Double salaireNetTotal;

	private Double indemnitesEntretien;

	private Double indemnitesRepas;

	private Double indemnitesKm;

	public SyntheseGarde(int mois, int annee) {
		this.mois = Integer.toString(mois);
		this.annee = Integer.toString(annee);
	}

}
