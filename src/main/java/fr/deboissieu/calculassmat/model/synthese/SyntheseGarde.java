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

	private Double nbHeuresReelles;

	private Double nbHeuresComplementaires;

	private Double salaireHoraireNetHeureNormale;

	private Double salaireNetHeuresNormales;

	private Double salaireNetHeuresComplementaires;

	private Double salaireNetTotal;

	private Double congesPayes;

	private Double indemnitesEntretien;

	private Double indemnitesRepas;

	private Double indemnitesKm;

	private Double montantPaiementMensuel;

	public SyntheseGarde(int mois, int annee) {
		this.mois = Integer.toString(mois);
		this.annee = Integer.toString(annee);
	}

	public void calculerPaiementMensuel() {
		this.montantPaiementMensuel = this.salaireNetTotal + this.indemnitesEntretien + this.indemnitesRepas
				+ this.indemnitesKm;

	}

}
