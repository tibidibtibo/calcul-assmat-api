package fr.deboissieu.calculassmat.api;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.deboissieu.calculassmat.bl.saisie.SaisieBlo;
import fr.deboissieu.calculassmat.bl.validation.ValidationBlo;
import fr.deboissieu.calculassmat.commons.filestorage.FileStorageService;
import fr.deboissieu.calculassmat.configuration.LogCall;
import fr.deboissieu.calculassmat.model.certification.CertificationRequest;
import fr.deboissieu.calculassmat.model.saisie.SaisieEnfantDto;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

@RestController
@RequestMapping("/saisie")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SaisieController {

	@Resource
	private ValidationBlo validationBlo;

	@Resource
	private SaisieBlo saisieBlo;

	@Resource
	private FileStorageService fileStorageService;

	@LogCall
	@RequestMapping(produces = "application/json", method = {
			RequestMethod.POST })
	public void enregistrerSaisie(@RequestBody SaisieRequest request) throws Exception {
		validationBlo.validerSaisie(request);
		saisieBlo.enregistrerSaisie(request);
	}

	@LogCall
	@RequestMapping(produces = "application/json", method = { RequestMethod.DELETE }, path = "/{identifiant}")
	public void supprimerSaisie(@PathVariable String identifiant) throws Exception {
		saisieBlo.supprimerSaisie(identifiant);
	}

	@LogCall
	@RequestMapping(produces = "application/json", method = { RequestMethod.GET }, path = "/{year}/{month}")
	public Collection<SaisieEnfantDto> findSaisieMonthYear(@PathVariable String month, @PathVariable String year)
			throws Exception {
		Integer mois = validationBlo.validerPathParamCalculMoisAnnee(month);
		Integer annee = validationBlo.validerPathParamCalculMoisAnnee(year);
		return saisieBlo.findSaisiesByMonth(mois, annee);
	}

	@LogCall
	@RequestMapping(produces = "application/json", method = {
			RequestMethod.POST }, path = "/certification/{year}/{month}")
	public void certifier(@PathVariable String month, @PathVariable String year,
			@RequestBody CertificationRequest request)
			throws Exception {
		Integer mois = validationBlo.validerPathParamCalculMoisAnnee(month);
		Integer annee = validationBlo.validerPathParamCalculMoisAnnee(year);
		validationBlo.validerCertification(request);
		saisieBlo.certifier(request, mois, annee);
	}

	@LogCall
	@RequestMapping(produces = "application/json", method = {
			RequestMethod.POST }, value = "/file/{annee}/{mois}")
	public void importFichier(@RequestParam("fichier") MultipartFile multipartFile,
			@PathVariable("annee") String annee, @PathVariable("mois") String mois) throws Exception {

		String fileName = fileStorageService.storeFile(multipartFile);

		Integer numeroMois = validationBlo.validerPathParamCalculMoisAnnee(mois);
		Integer numeroAnnee = validationBlo.validerPathParamCalculMoisAnnee(annee);

		saisieBlo.importerFichierSaisie(numeroMois, numeroAnnee, fileName);
	}

}
