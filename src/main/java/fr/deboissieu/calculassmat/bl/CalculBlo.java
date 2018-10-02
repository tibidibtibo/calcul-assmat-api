package fr.deboissieu.calculassmat.bl;

import javax.ws.rs.core.Response;

public interface CalculBlo {

	Response calculerSyntheseGarde(int mois, int numeroAnnee, String nomAssMat);

}
