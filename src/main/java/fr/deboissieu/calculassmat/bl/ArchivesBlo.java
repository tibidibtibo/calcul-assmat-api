package fr.deboissieu.calculassmat.bl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import fr.deboissieu.calculassmat.model.archives.Archive;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

public interface ArchivesBlo {

	void archiverTraitement(Collection<SaisieJournaliere> saisie, SyntheseGarde synthese, int mois,
			int annee, Map<String, ParametrageEnfant> mapParamEnfants);

	List<Archive> getArchives();
}
