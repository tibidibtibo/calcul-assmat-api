package fr.deboissieu.calculassmat.bl;

import java.util.Collection;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageGarde;

public interface ParametrageBlo {

	ParametrageGarde getParametrageGarde();

	Collection<String> getListeNomsEnfants(ParametrageGarde paramGarde);

	ParametrageEnfant getParamEnfant(ParametrageGarde paramGarde, String prenom);
}
