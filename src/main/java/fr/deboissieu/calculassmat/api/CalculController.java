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
import fr.deboissieu.calculassmat.model.synthese.ResultatCalcul;

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
	@RequestMapping(produces = "application/json", method = {
			RequestMethod.POST }, value = "/file/{annee}/{mois}")
	public ResultatCalcul calculerFichier(@RequestParam("fichier") MultipartFile multipartFile,
			@PathVariable("annee") String annee, @PathVariable("mois") String mois) throws Exception {

		String fileName = fileStorageService.storeFile(multipartFile);

		Integer numeroMois = validationBlo.validerPathParamCalculMoisAnnee(mois);
		Integer numeroAnnee = validationBlo.validerPathParamCalculMoisAnnee(annee);

		return new ResultatCalcul(numeroMois.toString(), numeroAnnee.toString(),
				calculBlo.calculerSyntheseGardeFromFilename(numeroMois, numeroAnnee, fileName));
	}
}
