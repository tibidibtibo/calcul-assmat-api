package fr.deboissieu.calculassmat.model.synthese;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Salaire implements Serializable {

	private static final long serialVersionUID = -162618944081405127L;

	private Double tauxHoraireNetHeureNormale;

	private Double tauxHoraireNetHeureComplementaire;

	private Double salaireNetMensualise;

	private Double salaireNetHeuresComplementaires;

	private Double congesPayes;

	private Double salaireNetTotal;

	public Double getSalaireMensuel(Indemnites indemnites) {
		return this.salaireNetTotal + indemnites.getIndemnitesEntretien()
				+ indemnites.getIndemnitesRepas()
				+ indemnites.getIndemnitesKm();
	}
}
