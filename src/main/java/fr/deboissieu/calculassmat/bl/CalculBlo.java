package fr.deboissieu.calculassmat.bl;

import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

public interface CalculBlo {

	SyntheseGarde calculerSyntheseGarde(int mois, int numeroAnnee, String nomAssMat) throws Exception;

}
