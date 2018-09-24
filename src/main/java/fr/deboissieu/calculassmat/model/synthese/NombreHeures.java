package fr.deboissieu.calculassmat.model.synthese;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NombreHeures {

	private float heuresNormales;

	private float heuresComplementaires;

	public void addHeuresNormales(float amount) {
		this.heuresNormales += amount;
	}

	public void addHeuresComplementaires(float amount) {
		this.heuresComplementaires += amount;
	}

	public NombreHeures() {
		this.heuresNormales = 0f;
		this.heuresComplementaires = 0f;
	}
}
