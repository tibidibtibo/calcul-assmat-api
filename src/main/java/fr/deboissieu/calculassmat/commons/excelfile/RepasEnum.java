package fr.deboissieu.calculassmat.commons.excelfile;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import lombok.Getter;

public enum RepasEnum {

	DEJEUNER("Déjeuner"), DEJEUNER_X2("Déjeuner X2"), GOUTER("Goûter"), GOUTER_X2("Goûter X2");

	@Getter
	private String repas;

	RepasEnum(String repas) {
		this.repas = repas;
	}

	public static RepasEnum fromRepas(String repas) {
		for (RepasEnum enumValue : RepasEnum.values()) {
			if (enumValue.getRepas().equalsIgnoreCase(repas)) {
				return enumValue;
			}
		}

		throw new IllegalArgumentException(
				String.format("There is no value with name '%s' in Enum %s", repas, RepasEnum.class));
	}

	public static Set<RepasEnum> fromRepas(Set<String> setRepas) {
		if (CollectionUtils.isNotEmpty(setRepas)) {
			return new HashSet<>(CollectionUtils.collect(setRepas, new Transformer<String, RepasEnum>() {

				@Override
				public RepasEnum transform(String repas) {
					return fromRepas(repas);
				}
			}));
		}
		return new HashSet<>();
	}
}
