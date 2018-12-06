package fr.deboissieu.calculassmat.model.synthese;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

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

	private String nomEmploye;

	public SyntheseGarde(int mois, int annee, String nomEmploye) {
		this.mois = Integer.toString(mois);
		this.annee = Integer.toString(annee);
		this.nomEmploye = nomEmploye;
	}

	public void calculerPaiementMensuel() {
		this.montantPaiementMensuel = this.salaire.getSalaireMensuel(this.indemnites);
	}

}
