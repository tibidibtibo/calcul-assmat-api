package fr.deboissieu.calculassmat.model;

import java.util.Set;

import fr.deboissieu.calculassmat.commons.excelfile.ActionHoraireEnum;
import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import lombok.Getter;
import lombok.Setter;

public class HoraireUnitaire {
	@Getter
	@Setter
	private Set<PrenomEnum> prenoms;

	@Getter
	@Setter
	private ActionHoraireEnum action;

	@Getter
	@Setter
	private String heureAction;

	public static HoraireUnitaire of(SaisieJournaliere saisieJournaliere) {
		HoraireUnitaire horaireUnitaire = new HoraireUnitaire();
		horaireUnitaire.setPrenoms(PrenomEnum.fromPrenom(saisieJournaliere.getQui()));
		horaireUnitaire.setAction(ActionHoraireEnum.fromAction(saisieJournaliere.getAction()));
		horaireUnitaire.setHeureAction(saisieJournaliere.getHeureAction());
		return horaireUnitaire;
	}
}
