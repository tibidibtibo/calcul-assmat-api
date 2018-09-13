package fr.deboissieu.calculassmat.commons.excelfile;

import lombok.Getter;

public enum ExcelFileColEnum {

	HORODATAGE(0), DATE_SAISIE(1), QUI(2), ACTION(3), HEURE_ACTION(4), REPAS(5), DEPLACEMENTS(6), AUTRE_DEPLACEMENTS_KM(
			7);

	@Getter
	private int colNum;

	ExcelFileColEnum(int colNum) {
		this.colNum = colNum;
	}

}
