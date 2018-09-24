package fr.deboissieu.calculassmat.bl.impl;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.AssemblageSaisieBlo;
import fr.deboissieu.calculassmat.bl.CalculBlo;
import fr.deboissieu.calculassmat.bl.ExcelFileBlo;
import fr.deboissieu.calculassmat.bl.SyntheseBlo;
import fr.deboissieu.calculassmat.model.saisie.HorairesPersonnelsEtFrais;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@Component
public class CalculBloImpl implements CalculBlo {

	private static final Logger logger = LogManager.getLogger(CalculBloImpl.class);

	@Resource
	private ExcelFileBlo excelFileBlo;

	@Resource
	private AssemblageSaisieBlo assemblageSaisieBlo;

	@Resource
	private SyntheseBlo syntheseBlo;

	@Override
	public Response calculerSyntheseGarde(int mois) {
		try {

			Workbook workbook = excelFileBlo.openFile("testFiles/fichierTest.xlsx");

			Collection<SaisieJournaliere> donneesBrutes = excelFileBlo.extractDataFromWorkbook(workbook, mois);

			Map<String, HorairesPersonnelsEtFrais> donneesAsemblees = assemblageSaisieBlo
					.assemblerDonneesSaisies(donneesBrutes);

			SyntheseGarde syntheseGarde = syntheseBlo.calculerFraisMensuels(donneesAsemblees);

			return Response.ok(donneesAsemblees).build();

		} catch (Exception e) {
			logger.error("Impossible de traiter le fichier : {}", e);
		}
		return null;
	}

	@Override
	public Stream<SaisieJournaliere> streamCalculSynthese(int mois) {

		try {

			Workbook workbook = excelFileBlo.openFile("testFiles/fichierTest.xlsx");

			return excelFileBlo.streamWorkbook(workbook, mois);

		} catch (Exception e) {
			logger.error("Impossible de traiter le fichier : {}", e);
		}
		return null;
	}

}
