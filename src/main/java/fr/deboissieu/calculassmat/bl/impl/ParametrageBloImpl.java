package fr.deboissieu.calculassmat.bl.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ParametrageBlo;
import fr.deboissieu.calculassmat.dl.ParametrageRepository;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageGarde;

@Component
public class ParametrageBloImpl implements ParametrageBlo {

	@Resource
	ParametrageRepository parametrageRepository;

	@Override
	public ParametrageGarde getParametrageGarde() {
		return parametrageRepository.getParametrageGarde();
	}

	@Override
	public Collection<String> getListeNomsEnfants(ParametrageGarde paramGarde) {

		if (CollectionUtils.isEmpty(paramGarde.getEnfants())) {
			return Collections.emptyList();
		}

		return paramGarde.getEnfants()
				.stream()
				.map(ParametrageEnfant::getNom)
				.collect(Collectors.toList());

	}

	@Override
	public ParametrageEnfant getParamEnfant(ParametrageGarde paramGarde, final String nom) {
		if (CollectionUtils.isEmpty(paramGarde.getEnfants())) {
			return null;
		}
		return IterableUtils.find(paramGarde.getEnfants(), enfant -> nom.equals(enfant.getNom()));
	}

}
