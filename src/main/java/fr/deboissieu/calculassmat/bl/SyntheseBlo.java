package fr.deboissieu.calculassmat.bl;

import java.util.Collection;

import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

public interface SyntheseBlo {

	/**
	 * 
	 * @param annee
	 * @param mois
	 * @param nomAssMat
	 * @param donneesAsemblees
	 * @return
	 */
	SyntheseGarde calculerFraisMensuels(Collection<SaisieJournaliere> donneesSaisies, int mois, int annee,
			String nomAssMat);

}
