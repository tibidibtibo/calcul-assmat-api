package fr.deboissieu.calculassmat.model.synthese;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NombreHeures {

	private Double heuresNormalesReelles;

	private Double heuresNormalesContrat;

	private Double heuresComplementaires;

	public void addHeuresNormalesContrat(Double amount) {
		this.heuresNormalesContrat += amount;
	}

	public void addHeuresNormalesReelles(Double amount) {
		this.heuresNormalesReelles += amount;
	}

	public void addHeuresComplementaires(Double amount) {
		this.heuresComplementaires += amount;
	}

	public NombreHeures() {
		this.heuresNormalesReelles = 0d;
		this.heuresNormalesContrat = 0d;
		this.heuresComplementaires = 0d;
	}
}
