package fr.deboissieu.calculassmat.bl;

import java.util.Map;

import fr.deboissieu.calculassmat.model.saisie.HorairesPersonnelsEtFrais;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

public interface SyntheseBlo {

	/**
	 * 
	 * @param donneesAsemblees
	 * @return
	 */
	SyntheseGarde calculerFraisMensuels(Map<String, HorairesPersonnelsEtFrais> mapHorairesParDate);

}
