package fr.deboissieu.calculassmat.api;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.bl.referentiel.ReferentielBlo;
import fr.deboissieu.calculassmat.model.referentiel.TypeGardeDto;

@RestController
@RequestMapping("/referentiels")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReferentielController {

	@Resource
	private ReferentielBlo referentielBlo;

	@RequestMapping(method = { RequestMethod.GET }, path = "/typegarde")
	public Collection<TypeGardeDto> getTypesGarde() {
		return referentielBlo.getReferentielTypeGarde();
	}
}
