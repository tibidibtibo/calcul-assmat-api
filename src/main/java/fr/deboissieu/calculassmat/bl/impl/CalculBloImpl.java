package fr.deboissieu.calculassmat.bl.impl;

import java.util.Collection;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.CalculBlo;
import fr.deboissieu.calculassmat.bl.ExcelFileBlo;
import fr.deboissieu.calculassmat.bl.SyntheseBlo;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@Component
public class CalculBloImpl implements CalculBlo {

	private static final Logger logger = LogManager.getLogger(CalculBloImpl.class);

	@Resource
	private ExcelFileBlo excelFileBlo;

	@Resource
	private SyntheseBlo syntheseBlo;

	@Override
	public Response calculerSyntheseGarde(int mois, int annee, String nomAssMat) {
		try {

			// FIXME : pour l'instant lecture du fichier dans le classpath, ensuite envoyé
			// en attachement
			Workbook workbook = excelFileBlo.openFile("testFiles/suivi_garde_test.xlsx");

			Collection<SaisieJournaliere> donneesSaisies = excelFileBlo.extractDataFromWorkbook(workbook, mois);

			SyntheseGarde syntheseGarde = syntheseBlo.calculerFraisMensuels(donneesSaisies, mois, annee, nomAssMat);

			return Response.ok(syntheseGarde).build();

		} catch (Exception e) {
			logger.error("Impossible de traiter le fichier : {}", e);
			return Response.serverError().build();
		}
	}

}
