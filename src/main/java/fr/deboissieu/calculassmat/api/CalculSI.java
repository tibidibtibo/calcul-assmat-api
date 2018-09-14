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
public class CalculSI {

	private static final Logger logger = LogManager.getLogger(CalculSI.class);

	@Resource
	private CalculBlo calculBlo;

	@Resource
	private ValidationBlo validationBlo;

	@GET
	@Path("/{mois}")
	@Produces("application/json")
	@LogCall
	public Response calculer(@PathParam("mois") String mois) {
		int numeroMois = validationBlo.validerPathParamMois(mois);
		return calculBlo.calculerSyntheseGarde(numeroMois);
	}
}
