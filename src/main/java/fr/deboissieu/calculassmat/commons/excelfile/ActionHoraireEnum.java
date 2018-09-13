package fr.deboissieu.calculassmat.commons.excelfile;

import lombok.Getter;

public enum ActionHoraireEnum {

	ARRIVEE("Arrivée"), DEPART("Départ");

	@Getter
	private String action;

	ActionHoraireEnum(String action) {
		this.action = action;
	}

	public static ActionHoraireEnum fromAction(String action) {
		for (ActionHoraireEnum enumValue : ActionHoraireEnum.values()) {
			if (enumValue.getAction().equalsIgnoreCase(action)) {
				return enumValue;
			}
		}

		throw new IllegalArgumentException(
				String.format("There is no value with name '%s' in Enum %s", action, ActionHoraireEnum.class));
	}
}
