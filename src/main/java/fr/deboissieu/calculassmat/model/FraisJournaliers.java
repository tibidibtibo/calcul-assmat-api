package fr.deboissieu.calculassmat.model;

import java.util.Set;

import fr.deboissieu.calculassmat.commons.excelfile.DeplacementsEnum;
import fr.deboissieu.calculassmat.commons.excelfile.RepasEnum;
import lombok.Getter;
import lombok.Setter;

public class FraisJournaliers {

	@Getter
	@Setter
	private Set<RepasEnum> repas;

	@Getter
	@Setter
	private Set<DeplacementsEnum> deplacements;

	@Getter
	@Setter
	private Integer autresDeplacementKm;

	public static FraisJournaliers of(SaisieJournaliere saisie) {
		FraisJournaliers fraisJournaliers = new FraisJournaliers();
		fraisJournaliers.setRepas(RepasEnum.fromRepas(saisie.getRepas()));
		fraisJournaliers.setDeplacements(DeplacementsEnum.fromDeplacement(saisie.getDeplacements()));
		fraisJournaliers.setAutresDeplacementKm(saisie.getAutresDeplacementKm());
		return fraisJournaliers;
	}

}
