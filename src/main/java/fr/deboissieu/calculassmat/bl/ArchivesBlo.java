package fr.deboissieu.calculassmat.bl;

import java.util.Collection;

import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

public interface ArchivesBlo {

	void archiverTraitement(Collection<SaisieJournaliere> saisie, SyntheseGarde synthese);
}
