package fr.deboissieu.calculassmat.model.saisie;

import java.util.Set;

import fr.deboissieu.calculassmat.commons.excelfile.ActionHoraireEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HoraireUnitaire extends ActionHoraire {

	private Set<String> prenoms;

	private Integer nbDejeunersJosephine;

	private Integer nbGoutersLouise;

	private Integer nbGoutersJosephine;

	private Integer nbArEcoleLouise;

	public static HoraireUnitaire of(SaisieJournaliere saisieJournaliere) {
		HoraireUnitaire horaireUnitaire = new HoraireUnitaire();
		horaireUnitaire.setPrenoms(saisieJournaliere.getQui());
		horaireUnitaire.setAction(ActionHoraireEnum.fromAction(saisieJournaliere.getAction()));
		horaireUnitaire.setHeureAction(saisieJournaliere.getHeureAction());
		return horaireUnitaire;
	}
}
