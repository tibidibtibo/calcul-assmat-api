package fr.deboissieu.calculassmat.bl.impl;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ParametrageBlo;
import fr.deboissieu.calculassmat.bl.ValidationBlo;
import fr.deboissieu.calculassmat.commons.exceptions.ValidationExceptionsEnum;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@Component
public class ValidationBloImpl implements ValidationBlo {

	@Resource
	ParametrageBlo parametrageBlo;

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

		if (paramAssmat == null || MapUtils.isEmpty(mapParamEnfants)) {
			throw new ValidationException(ValidationExceptionsEnum.V101.toString());
		}

		if (CollectionUtils.isEmpty(donneesSaisies)) {
			throw new ValidationException(ValidationExceptionsEnum.V103.toString());
		}

	}

	@Override
	public String validerPathParamNomAssmat(String nomEmploye) {
		if (StringUtils.isBlank(nomEmploye)) {
			throw new ValidationException(ValidationExceptionsEnum.V002.getMessage());
		}
		ParametrageEmploye employe = parametrageBlo.findEmployeParNom(nomEmploye);
		if (employe == null) {
			throw new ValidationException(
					ValidationExceptionsEnum.V003.toString(nomEmploye, new ValidationException()));
		}
		return nomEmploye;
	}

	@Override
	public void validerAvantArchivage(Collection<SaisieJournaliere> saisie, SyntheseGarde synthese) {
		if (CollectionUtils.isEmpty(saisie) || synthese == null) {
			throw new ValidationException(ValidationExceptionsEnum.V102.toString());
		}
	}

}
