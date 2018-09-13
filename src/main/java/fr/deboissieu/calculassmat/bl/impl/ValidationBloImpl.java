package fr.deboissieu.calculassmat.bl.impl;

import javax.validation.ValidationException;

import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ValidationBlo;
import fr.deboissieu.calculassmat.commons.exceptions.ValidationExceptionsEnum;

@Component
public class ValidationBloImpl implements ValidationBlo {

	@Override
	public int validerPathParamMois(String pathParam) {
		try {
			return Integer.parseInt(pathParam);
		} catch (Exception e) {
			throw new ValidationException(ValidationExceptionsEnum.V001.toString(pathParam, e));
		}
	}

}
