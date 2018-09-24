package fr.deboissieu.calculassmat.model.saisie;

import fr.deboissieu.calculassmat.commons.excelfile.ActionHoraireEnum;
import lombok.Getter;
import lombok.Setter;

public class ActionHoraire {

	@Getter
	@Setter
	private ActionHoraireEnum action;

	@Getter
	@Setter
	private String heureAction;

}
