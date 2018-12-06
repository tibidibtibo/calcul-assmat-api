package fr.deboissieu.calculassmat.bl.synthese;

import java.util.Collection;

import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

public interface CalculBlo {

	Collection<SyntheseGarde> calculerSyntheseGardeFromFilename(int mois, int annee, String filename)
			throws Exception;

}
