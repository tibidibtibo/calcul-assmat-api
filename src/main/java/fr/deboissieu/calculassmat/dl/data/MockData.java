package fr.deboissieu.calculassmat.dl.data;

import java.util.EnumMap;

import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import fr.deboissieu.calculassmat.model.ParametrageEnfant;

public class MockData {

	public static EnumMap<PrenomEnum, ParametrageEnfant> getParametrageEnfant() {
		EnumMap<PrenomEnum, ParametrageEnfant> parametres = new EnumMap<>(PrenomEnum.class);
		parametres.put(PrenomEnum.LOUISE,
				new ParametrageEnfant(PrenomEnum.LOUISE, 1.08f, 1.08f, 0f, 1.08f, 0f, 0f, 0f));
		parametres.put(PrenomEnum.JOSEPHINE,
				new ParametrageEnfant(PrenomEnum.LOUISE, 9.25f, 9.25f, 0f, 9.25f, 8f, 0f, 0f));
		return parametres;
	}
}
