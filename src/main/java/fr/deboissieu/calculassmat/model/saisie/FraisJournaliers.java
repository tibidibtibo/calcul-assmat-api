package fr.deboissieu.calculassmat.model.saisie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FraisJournaliers {

	private Double autresDeplacementKm;

	public static FraisJournaliers of(SaisieJournaliere saisie) {
		FraisJournaliers fraisJournaliers = new FraisJournaliers();
		fraisJournaliers.setAutresDeplacementKm(saisie.getAutresDeplacementKm());
		return fraisJournaliers;
	}

}
