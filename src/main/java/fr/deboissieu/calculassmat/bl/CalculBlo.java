package fr.deboissieu.calculassmat.bl;

import java.util.Collection;

import fr.deboissieu.calculassmat.model.SaisieJournaliere;

public interface CalculBlo {

	Collection<SaisieJournaliere> calculerSyntheseGarde(int mois);

}
