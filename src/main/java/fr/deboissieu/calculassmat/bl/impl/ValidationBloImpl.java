package fr.deboissieu.calculassmat.bl.impl;

import java.util.Collection;
import java.util.Map;

import javax.validation.ValidationException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ValidationBlo;
import fr.deboissieu.calculassmat.commons.exceptions.ValidationExceptionsEnum;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;

@Component
public class ValidationBloImpl implements ValidationBlo {

	@Override
	public int validerPathParamCalculMoisAnnee(String pathParam) {
		try {
			return Integer.parseInt(pathParam);
		} catch (Exception e) {
			throw new ValidationException(ValidationExceptionsEnum.V001.toString(pathParam, e));
		}
	}

	@Override
	public void validerAvantCalcul(Collection<SaisieJournaliere> donneesSaisies, ParametrageEmploye paramAssmat,
			Map<String, ParametrageEnfant> mapParamEnfants) {
		if (CollectionUtils.isEmpty(donneesSaisies) || paramAssmat == null || MapUtils.isEmpty(mapParamEnfants)) {
			throw new ValidationException(ValidationExceptionsEnum.V101.getMessage());
		}
	}

}
