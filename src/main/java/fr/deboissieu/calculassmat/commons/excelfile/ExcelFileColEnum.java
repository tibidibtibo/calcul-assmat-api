package fr.deboissieu.calculassmat.commons.excelfile;

import lombok.Getter;

public enum ExcelFileColEnum {

	HORODATAGE(0), DATE_SAISIE(1), QUI(2), HEURE_ARRIVEE(3), HEURE_DEPART(4), DEJEUNER(5), GOUTER(
			6), DEPLACEMENTS_ECOLE(7), AUTRE_DEPLACEMENTS_KM(8);

	@Getter
	private int colNum;

	ExcelFileColEnum(int colNum) {
		this.colNum = colNum;
	}

}
