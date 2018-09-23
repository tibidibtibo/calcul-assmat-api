package fr.deboissieu.calculassmat.dl;

import java.util.EnumMap;

import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import fr.deboissieu.calculassmat.model.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageGarde;

public interface ParametrageRepository {

	EnumMap<PrenomEnum, ParametrageEnfant> getParametrageEnfant();

	ParametrageGarde getParametrageGarde();
}
