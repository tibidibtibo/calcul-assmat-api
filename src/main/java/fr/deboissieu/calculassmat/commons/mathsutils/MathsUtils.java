package fr.deboissieu.calculassmat.commons.mathsutils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathsUtils {

	public static double roundTo2Digits(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
