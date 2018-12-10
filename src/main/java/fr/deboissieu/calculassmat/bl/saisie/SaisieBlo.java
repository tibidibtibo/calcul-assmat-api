package fr.deboissieu.calculassmat.bl.saisie;

import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

public interface SaisieBlo {

	/**
	 * Enregistrement d'une saisie
	 * 
	 * @param saisie
	 */
	void enregistrerSaisie(SaisieRequest saisie);
}
