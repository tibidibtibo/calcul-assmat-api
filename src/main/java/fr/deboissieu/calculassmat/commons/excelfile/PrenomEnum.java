package fr.deboissieu.calculassmat.commons.excelfile;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import lombok.Getter;

public enum PrenomEnum {
	LOUISE("Louise"), JOSEPHINE("Joséphine");

	@Getter
	private String prenom;

	PrenomEnum(String prenom) {
		this.prenom = prenom;
	}

	public static PrenomEnum fromPrenom(String prenom) {
		for (PrenomEnum enumValue : PrenomEnum.values()) {
			if (enumValue.getPrenom().equalsIgnoreCase(prenom)) {
				return enumValue;
			}
		}

		throw new IllegalArgumentException(
				String.format("There is no value with name '%s' in Enum %s", prenom, PrenomEnum.class));
	}

	public static Set<PrenomEnum> fromPrenom(Set<String> setPrenoms) {
		if (CollectionUtils.isNotEmpty(setPrenoms)) {
			return new HashSet<>(CollectionUtils.collect(setPrenoms, new Transformer<String, PrenomEnum>() {

				@Override
				public PrenomEnum transform(String prenom) {
					return fromPrenom(prenom);
				}
			}));
		}
		return new HashSet<>();
	}
}
