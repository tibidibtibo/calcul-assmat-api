package fr.deboissieu.calculassmat.bl;

import java.util.Collection;
import java.util.Map;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;

public interface ValidationBlo {

	int validerPathParamCalculMoisAnnee(String pathParam);

	void validerAvantCalcul(Collection<SaisieJournaliere> donneesSaisies, ParametrageEmploye paramAssmat,
			Map<String, ParametrageEnfant> mapParamEnfants);
}
