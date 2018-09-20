package fr.deboissieu.calculassmat.dl.impl;

import java.util.Collection;
import java.util.EnumMap;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import fr.deboissieu.calculassmat.dl.ParametrageRepository;
import fr.deboissieu.calculassmat.dl.mock.MockData;
import fr.deboissieu.calculassmat.model.ParametrageEnfant;

@Component
public class ParametrageRepositoryImpl implements ParametrageRepository {

	@Override
	public EnumMap<PrenomEnum, ParametrageEnfant> getParametrageEnfant() {
		EnumMap<PrenomEnum, ParametrageEnfant> parametres = new EnumMap<>(PrenomEnum.class);
		Collection<ParametrageEnfant> paramEnfants = MockData.getParametrageEnfant();
		if (CollectionUtils.isNotEmpty(paramEnfants)) {
			for (ParametrageEnfant param : paramEnfants) {
				parametres.put(param.getPrenom(), param);
			}
		}
		return parametres;
	}

}
