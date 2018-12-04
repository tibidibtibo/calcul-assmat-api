package fr.deboissieu.calculassmat.bl;

import java.util.Collection;
import java.util.Map;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
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
			Map<String, ParametrageEnfant> mapParamEnfants);

}
