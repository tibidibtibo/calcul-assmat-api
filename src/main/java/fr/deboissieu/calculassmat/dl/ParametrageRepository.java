package fr.deboissieu.calculassmat.dl;

import java.util.EnumMap;

import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import fr.deboissieu.calculassmat.model.ParametrageEnfant;

public interface ParametrageRepository {

	EnumMap<PrenomEnum, ParametrageEnfant> getParametrageEnfant();
}
