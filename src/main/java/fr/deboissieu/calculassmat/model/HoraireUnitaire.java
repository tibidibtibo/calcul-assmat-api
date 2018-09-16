package fr.deboissieu.calculassmat.model;

import java.util.Set;

import fr.deboissieu.calculassmat.commons.excelfile.ActionHoraireEnum;
import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import lombok.Getter;
import lombok.Setter;

public class HoraireUnitaire extends ActionHoraire {

	@Getter
	@Setter
	private Set<PrenomEnum> prenoms;

	public static HoraireUnitaire of(SaisieJournaliere saisieJournaliere) {
		HoraireUnitaire horaireUnitaire = new HoraireUnitaire();
		horaireUnitaire.setPrenoms(PrenomEnum.fromPrenom(saisieJournaliere.getQui()));
		horaireUnitaire.setAction(ActionHoraireEnum.fromAction(saisieJournaliere.getAction()));
		horaireUnitaire.setHeureAction(saisieJournaliere.getHeureAction());
		return horaireUnitaire;
	}
}
