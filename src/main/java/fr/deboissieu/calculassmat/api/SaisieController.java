package fr.deboissieu.calculassmat.api;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.bl.saisie.SaisieBlo;
import fr.deboissieu.calculassmat.bl.validation.ValidationBlo;
import fr.deboissieu.calculassmat.configuration.LogCall;
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

	@LogCall
	@RequestMapping(produces = "application/json", method = {
			RequestMethod.POST })
	public void enregistrerSaisie(@RequestBody SaisieRequest request) throws Exception {
		validationBlo.validerSaisie(request);
		saisieBlo.enregistrerSaisie(request);
	}

	@LogCall
	@RequestMapping(produces = "application/json", method = { RequestMethod.GET }, path = "/{year}/{month}")
	public Collection<SaisieEnfantDto> findSaisieMonthYear(@PathVariable String month, @PathVariable String year)
			throws Exception {
		Integer mois = validationBlo.validerPathParamCalculMoisAnnee(month);
		Integer annee = validationBlo.validerPathParamCalculMoisAnnee(year);
		return saisieBlo.findSaisiesByMonth(mois, annee);
	}

}
