package fr.deboissieu.calculassmat.model.synthese;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SyntheseGarde implements Serializable {

	private static final long serialVersionUID = 622457511701268495L;

	private String mois;

	private String annee;

	private int nbJoursTravailles;

	private NombreHeures nombreHeures;

	private Salaire salaire;

	private Indemnites indemnites;

	private Double montantPaiementMensuel;

	public SyntheseGarde(int mois, int annee) {
		this.mois = Integer.toString(mois);
		this.annee = Integer.toString(annee);
	}

	public void calculerPaiementMensuel() {
		this.montantPaiementMensuel = this.salaire.getSalaireMensuel(this.indemnites);
	}

}
