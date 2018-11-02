package fr.deboissieu.calculassmat.bl.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ArchivesBlo;
import fr.deboissieu.calculassmat.bl.CalculBlo;
import fr.deboissieu.calculassmat.bl.ExcelFileBlo;
import fr.deboissieu.calculassmat.bl.ParametrageBlo;
import fr.deboissieu.calculassmat.bl.SyntheseBlo;
import fr.deboissieu.calculassmat.commons.filestorage.FileStorageService;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@Component
public class CalculBloImpl implements CalculBlo {

	private static final Logger logger = LogManager.getLogger(CalculBloImpl.class);

	@Resource
	private ExcelFileBlo excelFileBlo;

	@Resource
	private SyntheseBlo syntheseBlo;

	@Resource
	ArchivesBlo archivesBlo;

	@Resource
	ParametrageBlo parametrageBlo;

	@Resource
	FileStorageService fileStorageService;

	@Override
	public SyntheseGarde calculerSyntheseGardeFromFilename(int mois, int annee, String idAssmat, String filename)
			throws Exception {

		Workbook workbook = openWorkbook(filename);

		SyntheseGarde syntheseGarde = new SyntheseGarde(mois, annee);
		Exception exception = null;

		try {
			Collection<SaisieJournaliere> donneesSaisies = excelFileBlo.extractDataFromWorkbook(workbook, mois);

			ParametrageEmploye paramAssmat = parametrageBlo.findEmployeParId(idAssmat);
			Map<String, ParametrageEnfant> mapParamEnfants = parametrageBlo.findAllParamsEnfants();

			syntheseGarde = syntheseBlo.calculerFraisMensuels(donneesSaisies, mois, annee, paramAssmat,
					mapParamEnfants);
			archivesBlo.archiverTraitement(donneesSaisies, syntheseGarde, mois, annee, paramAssmat, mapParamEnfants);
		} catch (Exception e) {
			logger.error("Impossible de traiter le fichier : {}", e);
			exception = e;
		} finally {
			workbook.close();
		}

		if (exception != null) {
			throw exception;
		}
		return syntheseGarde;
	}

	private Workbook openWorkbook(String filename) throws IOException, InvalidFormatException {
		try {
			org.springframework.core.io.Resource fileResource = fileStorageService.loadFileAsResource(filename);
			File file = fileResource.getFile();
			return excelFileBlo.openFileAsWorkbook(file);
		} catch (Exception e) {
			logger.error("Impossible de traiter le fichier : {}", e);
			throw e;
		}
	}

}
