package fr.deboissieu.calculassmat.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.deboissieu.calculassmat.bl.CalculBlo;
import fr.deboissieu.calculassmat.bl.ValidationBlo;
import fr.deboissieu.calculassmat.commons.filestorage.FileStorageService;
import fr.deboissieu.calculassmat.configuration.LogCall;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@RestController
@RequestMapping("/calcul")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CalculController {

	@Resource
	private CalculBlo calculBlo;

	@Resource
	private ValidationBlo validationBlo;

	@Resource
	private FileStorageService fileStorageService;

	@LogCall
	@RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "/{annee}/{mois}/{nomEmploye}")
	public SyntheseGarde calculer(@PathVariable("annee") String annee, @PathVariable("mois") String mois,
			@PathVariable("nomEmploye") String nomEmploye) throws Exception {
		int numeroMois = validationBlo.validerPathParamCalculMoisAnnee(mois);
		int numeroAnnee = validationBlo.validerPathParamCalculMoisAnnee(annee);
		String nomAssMat = validationBlo.validerPathParamNomAssmat(nomEmploye);
		return calculBlo.calculerSyntheseGarde(numeroMois, numeroAnnee, nomAssMat);
	}

	@LogCall
	@RequestMapping(produces = "application/json", method = { RequestMethod.OPTIONS,
			RequestMethod.POST }, value = "/file/{annee}/{mois}/{nomEmploye}")
	public SyntheseGarde calculerFichier(@RequestParam("fichier") MultipartFile multipartFile,
			@PathVariable("annee") String annee, @PathVariable("mois") String mois,
			@PathVariable("nomEmploye") String nomEmploye) throws Exception {

		String fileName = fileStorageService.storeFile(multipartFile);

		int numeroMois = validationBlo.validerPathParamCalculMoisAnnee(mois);
		int numeroAnnee = validationBlo.validerPathParamCalculMoisAnnee(annee);
		String nomAssMat = validationBlo.validerPathParamNomAssmat(nomEmploye);

		return calculBlo.calculerSyntheseGardeFromFilename(numeroMois, numeroAnnee, nomAssMat, fileName);
	}
}
