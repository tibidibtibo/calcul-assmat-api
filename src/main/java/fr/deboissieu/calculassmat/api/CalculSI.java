package fr.deboissieu.calculassmat.api;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ExcelFileBlo;

@Component
@Path("/calcul")
public class CalculSI {

	private static final Logger logger = LogManager.getLogger(CalculSI.class);

	@Resource
	private ExcelFileBlo excelFileBlo;

	@GET
	@Path("/{mois}")
	@Produces("application/json")
	public Response calculer(@PathParam("mois") String mois)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		logger.info("Requête reçue : {}", mois);
		excelFileBlo.readFile("test");
		return Response.ok(mois).build();
	}
}
