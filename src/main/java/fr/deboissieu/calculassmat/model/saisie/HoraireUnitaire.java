package fr.deboissieu.calculassmat.model.saisie;

import java.util.Set;

import fr.deboissieu.calculassmat.commons.excelfile.ActionHoraireEnum;
import lombok.Getter;
import lombok.Setter;

public class HoraireUnitaire extends ActionHoraire {

	@Getter
	@Setter
	private Set<String> prenoms;

	public static HoraireUnitaire of(SaisieJournaliere saisieJournaliere) {
		HoraireUnitaire horaireUnitaire = new HoraireUnitaire();
		horaireUnitaire.setPrenoms(saisieJournaliere.getQui());
		horaireUnitaire.setAction(ActionHoraireEnum.fromAction(saisieJournaliere.getAction()));
		horaireUnitaire.setHeureAction(saisieJournaliere.getHeureAction());
		return horaireUnitaire;
	}
}
