package fr.deboissieu.calculassmat.bl.impl;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ParametrageBlo;
import fr.deboissieu.calculassmat.dl.ParamEmployeRepository;
import fr.deboissieu.calculassmat.dl.ParamEnfantRepository;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;

@Component
public class ParametrageBloImpl implements ParametrageBlo {

	@Resource
	ParamEnfantRepository paramEnfantRepository;

	@Resource
	ParamEmployeRepository paramEmployeRepository;

	public Map<String, ParametrageEnfant> findAllParamsEnfants() {
		Collection<ParametrageEnfant> paramsEnfant = paramEnfantRepository.findAll();
		if (CollectionUtils.isNotEmpty(paramsEnfant)) {

			return paramsEnfant
					.stream()
					.collect(Collectors.toMap(ParametrageEnfant::getNom, Function.identity()));
		}
		return null;
	}

	@Override
	public ParametrageEmploye findEmployeParNom(String nom) {
		return paramEmployeRepository.findByNom(nom);
	}

}
