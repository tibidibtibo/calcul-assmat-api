package fr.deboissieu.calculassmat.commons.excelfile;

import lombok.Getter;

public enum ExcelFileColEnum {

	HORODATAGE(0), DATE_SAISIE(1), QUI(2), ACTION(3), HEURE_ACTION(4), DEJEUNER_LOUISE(5), DEJEUNER_JOSEPHINE(
			6), GOUTER_LOUISE(7), GOUTER_JOSEPHINE(8), DEPLACEMENTS_ECOLE_LOUISE(9), AUTRE_DEPLACEMENTS_KM(10);

	@Getter
	private int colNum;

	ExcelFileColEnum(int colNum) {
		this.colNum = colNum;
	}

}
