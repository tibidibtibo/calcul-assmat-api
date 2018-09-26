package fr.deboissieu.calculassmat.model.saisie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FraisJournaliers {

	private Integer nbDejeuners;

	private Integer nbGouters;

	private Integer arEcole;

	private Integer autresDeplacementKm;

	public static FraisJournaliers of(SaisieJournaliere saisie) {
		FraisJournaliers fraisJournaliers = new FraisJournaliers();
		fraisJournaliers.setNbDejeuners(saisie.getNbDejeuners());
		fraisJournaliers.setNbGouters(saisie.getNbGouters());
		fraisJournaliers.setArEcole(saisie.getArEcole());
		fraisJournaliers.setAutresDeplacementKm(saisie.getAutresDeplacementKm());
		return fraisJournaliers;
	}

}
