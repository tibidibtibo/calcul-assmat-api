package fr.deboissieu.calculassmat.bl.synthese;

import java.util.Collection;

import fr.deboissieu.calculassmat.model.saisie.Saisie;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

public interface SyntheseBlo {

	/**
	 * Calcul de la synthèse depuis une liste de saisies
	 * 
	 * @param donneesSaisies
	 * @param mois
	 * @param annee
	 * @return
	 */
	Collection<SyntheseGarde> calculerSynthese(Collection<Saisie> donneesSaisies, int mois, int annee);
}
