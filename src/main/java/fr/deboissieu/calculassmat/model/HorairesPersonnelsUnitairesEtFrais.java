package fr.deboissieu.calculassmat.model;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

public class HorairesPersonnelsUnitairesEtFrais {
	@Getter
	@Setter
	private Collection<HoraireUnitairePersonnel> horaires;

	@Getter
	@Setter
	private FraisJournaliers fraisJournaliers;
}
