package fr.deboissieu.calculassmat.model.synthese;

import fr.deboissieu.calculassmat.commons.mathsutils.MathsUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NombreHeures {

	private Double heuresReelles;

	private Double heuresNormalesContrat;

	private Double heuresComplementaires;

	public void addHeuresNormalesContrat(Double amount) {
		this.heuresNormalesContrat += amount;
	}

	public void addHeuresReelles(Double amount) {
		this.heuresReelles += amount;
	}

	public void addHeuresComplementaires(Double amount) {
		this.heuresComplementaires += amount;
	}

	public NombreHeures() {
		this.heuresReelles = 0d;
		this.heuresNormalesContrat = 0d;
		this.heuresComplementaires = 0d;
	}

	public void roundValues() {
		if (this.heuresReelles != null) {
			this.heuresReelles = MathsUtils.roundTo2Digits(this.heuresReelles);
		}

		if (this.heuresNormalesContrat != null) {
			this.heuresNormalesContrat = MathsUtils.roundTo2Digits(this.heuresNormalesContrat);
		}

		if (this.heuresComplementaires != null) {
			this.heuresComplementaires = MathsUtils.roundTo2Digits(this.heuresComplementaires);
		}

	}
}
