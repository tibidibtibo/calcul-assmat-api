package fr.deboissieu.calculassmat.model.synthese;

import fr.deboissieu.calculassmat.commons.mathsutils.MathsUtils;
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

	public void roundValues() {
		if (this.heuresNormalesReelles != null) {
			this.heuresNormalesReelles = MathsUtils.roundTo2Digits(this.heuresNormalesReelles);
		}

		if (this.heuresNormalesContrat != null) {
			this.heuresNormalesContrat = MathsUtils.roundTo2Digits(this.heuresNormalesContrat);
		}

		if (this.heuresComplementaires != null) {
			this.heuresComplementaires = MathsUtils.roundTo2Digits(this.heuresComplementaires);
		}

	}
}
