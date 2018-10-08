package fr.deboissieu.calculassmat.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.bl.CalculBlo;
import fr.deboissieu.calculassmat.bl.ValidationBlo;
import fr.deboissieu.calculassmat.configuration.LogCall;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@RestController
@RequestMapping("/calcul")
public class CalculController {

	@Resource
	private CalculBlo calculBlo;

	@Resource
	private ValidationBlo validationBlo;

	@LogCall
	@RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "/{annee}/{mois}/{nomEmploye}")
	public SyntheseGarde calculer(@PathVariable("annee") String annee, @PathVariable("mois") String mois,
			@PathVariable("nomEmploye") String nomEmploye) throws Exception {
		int numeroMois = validationBlo.validerPathParamCalculMoisAnnee(mois);
		int numeroAnnee = validationBlo.validerPathParamCalculMoisAnnee(annee);
		String nomAssMat = validationBlo.validerPathParamNomAssmat(nomEmploye);
		return calculBlo.calculerSyntheseGarde(numeroMois, numeroAnnee, nomAssMat);
	}
}
