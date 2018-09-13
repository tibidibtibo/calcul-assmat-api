package fr.deboissieu.calculassmat.bl.impl;

import javax.validation.ValidationException;

import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ValidationBlo;

@Component
public class ValidationBloImpl implements ValidationBlo {

	@Override
	public int validerPathParamMois(String pathParam) {
		try {
			return Integer.parseInt(pathParam);
		} catch (Exception e) {
			throw new ValidationException("Path Param invalide : " + pathParam + " - Erreur : " + e);
		}
	}

}
