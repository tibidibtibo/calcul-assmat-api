package fr.deboissieu.calculassmat.api;

import java.util.Collection;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.bl.ParametrageBlo;
import fr.deboissieu.calculassmat.commons.exceptions.ValidationExceptionsEnum;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmployeDto;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfantDto;

@RestController
@RequestMapping("/parametrage")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ParametrageController {

	@Resource
	ParametrageBlo parametrageBlo;

	@RequestMapping(method = { RequestMethod.GET }, path = "/employes")
	public Collection<ParametrageEmployeDto> getAllEmployes() {
		return ParametrageEmployeDto.from(parametrageBlo.getAllEmployes());
	}

	@RequestMapping(method = { RequestMethod.GET }, path = "/enfants")
	public Collection<ParametrageEnfantDto> getAllEnfants() {
		return ParametrageEnfantDto.from(parametrageBlo.getAllEnfants());
	}

	@RequestMapping(method = { RequestMethod.GET }, path = "/employes/:id")
	public ParametrageEmploye getOneEmploye(@PathVariable String id) {
		if (id != null) {
			return parametrageBlo.findEmployeParId(id);
		}
		throw new ValidationException(ValidationExceptionsEnum.V004.getMessage());
	}
}
