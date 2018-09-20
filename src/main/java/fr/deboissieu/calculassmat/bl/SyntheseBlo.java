package fr.deboissieu.calculassmat.bl;

import java.util.Map;

import fr.deboissieu.calculassmat.model.HorairesPersonnelsEtFrais;
import fr.deboissieu.calculassmat.model.SyntheseGarde;

public interface SyntheseBlo {

	/**
	 * 
	 * @param donneesAsemblees
	 * @return
	 */
	SyntheseGarde calculerFraisMensuels(Map<String, HorairesPersonnelsEtFrais> mapHorairesParDate);

}
