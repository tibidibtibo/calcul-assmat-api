package fr.deboissieu.calculassmat.bl;

import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

public interface CalculBlo {

	SyntheseGarde calculerSyntheseGarde(int mois, int numeroAnnee, String nomAssMat) throws Exception;

	SyntheseGarde calculerSyntheseGardeFromFilename(int mois, int annee, String nomAssMat, String filename)
			throws Exception;

}
