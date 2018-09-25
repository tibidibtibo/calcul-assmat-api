package fr.deboissieu.calculassmat.bl;

import java.util.Map;

import fr.deboissieu.calculassmat.model.saisie.HorairesPersonnelsEtFrais;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

public interface SyntheseBlo {

	/**
	 * 
	 * @param annee
	 * @param mois
	 * @param donneesAsemblees
	 * @return
	 */
	SyntheseGarde calculerFraisMensuels(Map<String, HorairesPersonnelsEtFrais> mapHorairesParDate, int mois, int annee);

}
