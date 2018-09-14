package fr.deboissieu.calculassmat.commons.excelfile;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import lombok.Getter;

public enum DeplacementsEnum {
	AR_ECOLE("AR école"), AR_ECOLE_X2("AR école X2"), AR_ECOLE_X3("AR école X3"), AR_ECOLE_X4("AR école X4");

	@Getter
	private String deplacement;

	DeplacementsEnum(String deplacement) {
		this.deplacement = deplacement;
	}

	public static DeplacementsEnum fromDeplacement(String deplacement) {
		for (DeplacementsEnum enumValue : DeplacementsEnum.values()) {
			if (enumValue.getDeplacement().equalsIgnoreCase(deplacement)) {
				return enumValue;
			}
		}

		throw new IllegalArgumentException(
				String.format("There is no value with name '%s' in Enum %s", deplacement, DeplacementsEnum.class));
	}

	public static Set<DeplacementsEnum> fromDeplacement(Set<String> deplacements) {
		if (CollectionUtils.isNotEmpty(deplacements)) {
			return new HashSet<>(CollectionUtils.collect(deplacements, new Transformer<String, DeplacementsEnum>() {

				@Override
				public DeplacementsEnum transform(String deplacement) {
					return fromDeplacement(deplacement);
				}
			}));
		}
		return new HashSet<>();
	}

}
