package fr.deboissieu.calculassmat.model;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

public class HorairesPersonnelsEtFrais {

	@Getter
	@Setter
	Collection<HeuresPersonnelles> heuresPersonnelles;

	@Getter
	@Setter
	FraisJournaliers fraisJournaliers;
}
