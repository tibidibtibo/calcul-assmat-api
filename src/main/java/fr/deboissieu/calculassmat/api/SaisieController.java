package fr.deboissieu.calculassmat.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.bl.saisie.SaisieBlo;
import fr.deboissieu.calculassmat.bl.validation.ValidationBlo;
import fr.deboissieu.calculassmat.configuration.LogCall;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

@RestController
@RequestMapping("/saisie")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SaisieController {

	@Resource
	private ValidationBlo validationBlo;

	@Resource
	private SaisieBlo saisieBlo;

	@LogCall
	@RequestMapping(produces = "application/json", method = {
			RequestMethod.POST })
	public void enregistreSaisie(SaisieRequest saisie) throws Exception {

		validationBlo.validerSaisie(saisie);
		saisieBlo.enregistrerSaisie(saisie);

	}

}
