package fr.deboissieu.calculassmat.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.configuration.LogCall;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

@RestController
@RequestMapping("/saisie")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SaisieController {

	@LogCall
	@RequestMapping(produces = "application/json", method = {
			RequestMethod.POST })
	public void enregistreSaisie(SaisieRequest saisie) throws Exception {
		System.out.println(saisie);
	}

}
