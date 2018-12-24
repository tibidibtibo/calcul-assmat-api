package fr.deboissieu.calculassmat.bl.validation.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.parametrage.ParametrageBlo;
import fr.deboissieu.calculassmat.bl.validation.ValidationBlo;
import fr.deboissieu.calculassmat.commons.exceptions.ValidationExceptionsEnum;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@Component
public class ValidationBloImpl implements ValidationBlo {

	@Resource
	Validator validator;

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
	public void validerAvantCalcul(Collection<SaisieJournaliere> donneesSaisies,
			Map<String, ParametrageEnfant> mapParamEnfants) {

		if (MapUtils.isEmpty(mapParamEnfants)) {
			throw new ValidationException(ValidationExceptionsEnum.V101.toString());
		}

		if (CollectionUtils.isEmpty(donneesSaisies)) {
			throw new ValidationException(ValidationExceptionsEnum.V103.toString());
		}

	}

	@Override
	public String validerPathParamNomAssmat(String idEmploye) {
		if (StringUtils.isBlank(idEmploye)) {
			throw new ValidationException(ValidationExceptionsEnum.V002.getMessage());
		}
		ParametrageEmploye employe = parametrageBlo.findEmployeParId(idEmploye);
		if (employe == null) {
			throw new ValidationException(
					ValidationExceptionsEnum.V003.toString(idEmploye, null));
		}
		return idEmploye;
	}

	@Override
	public void validerAvantArchivage(Collection<SaisieJournaliere> saisie, SyntheseGarde synthese) {
		if (CollectionUtils.isEmpty(saisie) || synthese == null) {
			throw new ValidationException(ValidationExceptionsEnum.V102.toString());
		}
	}

	@Override
	public void validerSaisie(SaisieRequest saisie) {

		// Validation de la requête (null ou vide)
		if (saisie == null || saisie != null && CollectionUtils.isEmpty(saisie.getSaisie())) {
			throw new ValidationException(ValidationExceptionsEnum.V005.toString());
		}

		// Validation primaire du contenu (objets obligatoires dans la requête)
		Set<ConstraintViolation<SaisieRequest>> violations = validator.validate(saisie);
		if (CollectionUtils.isNotEmpty(violations)) {
			String listeViolations = violationsToString(violations);
			throw new ValidationException(ValidationExceptionsEnum.V005.toString(listeViolations, new Exception()));
		}

		// Validation de la cohérence de saisie (au moins une heure saisie, employé,
		// enfant...)
		saisie.getSaisie().stream().forEach(s -> {
			if (StringUtils.isBlank(s.getEmploye()) || s.getEnfant() == null
					|| (s.getHeureArrivee() == null && s.getHeureDepart() == null)) {
				throw new ValidationException(ValidationExceptionsEnum.V005.toString());
			}
		});

		// Si on arrive là c'est ok !
	}

	private String violationsToString(Set<ConstraintViolation<SaisieRequest>> violations) {
		List<String> listeViolations = violations.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		return StringUtils.join(listeViolations, " | ");
	}

}
