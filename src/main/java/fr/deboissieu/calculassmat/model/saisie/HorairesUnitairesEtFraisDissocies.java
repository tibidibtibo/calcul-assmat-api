package fr.deboissieu.calculassmat.model.saisie;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

public class HorairesUnitairesEtFraisDissocies {

	@Getter
	@Setter
	private Collection<HoraireUnitaire> horaires;

	@Getter
	@Setter
	private FraisJournaliers fraisJournaliers;

}
