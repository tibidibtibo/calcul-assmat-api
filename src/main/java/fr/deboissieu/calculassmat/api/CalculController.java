package fr.deboissieu.calculassmat.api;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.CalculBlo;
import fr.deboissieu.calculassmat.bl.ValidationBlo;
import fr.deboissieu.calculassmat.configuration.LogCall;

@Component
@Path("/calcul")
public class CalculController {

	private static final Logger logger = LogManager.getLogger(CalculController.class);

	@Resource
	private CalculBlo calculBlo;

	@Resource
	private ValidationBlo validationBlo;

	@GET
	@Path("/{annee}/{mois}")
	@Produces("application/json")
	@LogCall
	public Response calculer(@PathParam("annee") String annee, @PathParam("mois") String mois) {
		int numeroMois = validationBlo.validerPathParamCalculMoisAnnee(mois);
		int numeroAnnee = validationBlo.validerPathParamCalculMoisAnnee(annee);
		return calculBlo.calculerSyntheseGarde(numeroMois, numeroAnnee);
	}
}
