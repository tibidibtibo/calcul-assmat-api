package fr.deboissieu.calculassmat.bl;

import java.util.Map;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;

public interface ParametrageBlo {

	/**
	 * Recherche les paramètres enfant et retourne une map par prénom
	 * 
	 * @return Map<String, ParametrageEnfant> paramètres enfant par nom
	 */
	public Map<String, ParametrageEnfant> findAllParamsEnfants();

	/**
	 * Recherche le paramétrage employé par nom
	 * 
	 * @param string
	 * @return
	 */
	public ParametrageEmploye findEmployeParNom(String nom);

}
