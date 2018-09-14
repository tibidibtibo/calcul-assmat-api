package fr.deboissieu.calculassmat.model;

import java.util.Set;

import fr.deboissieu.calculassmat.commons.excelfile.ActionHoraireEnum;
import lombok.Getter;
import lombok.Setter;

public class HoraireUnitaire {
	@Getter
	@Setter
	private Set<String> qui;

	@Getter
	@Setter
	private ActionHoraireEnum action;

	@Getter
	@Setter
	private String heureAction;

	public static HoraireUnitaire of(SaisieJournaliere saisieJournaliere) {
		HoraireUnitaire horaireUnitaire = new HoraireUnitaire();
		horaireUnitaire.setQui(saisieJournaliere.getQui());
		horaireUnitaire.setAction(ActionHoraireEnum.fromAction(saisieJournaliere.getAction()));
		horaireUnitaire.setHeureAction(saisieJournaliere.getHeureAction());
		return horaireUnitaire;
	}
}
