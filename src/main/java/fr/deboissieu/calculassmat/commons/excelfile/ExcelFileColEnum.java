package fr.deboissieu.calculassmat.commons.excelfile;

import lombok.Getter;

public enum ExcelFileColEnum {

	HORODATAGE(0),
	DATE_SAISIE(1),
	EMPLOYE(2),
	ENFANT(3),
	HEURE_ARRIVEE(4),
	HEURE_DEPART(5),
	DEJEUNER(6),
	GOUTER(7),
	DEPLACEMENTS_ECOLE(8),
	AUTRE_DEPLACEMENTS_KM(9);

	@Getter
	private int colNum;

	ExcelFileColEnum(int colNum) {
		this.colNum = colNum;
	}

}
