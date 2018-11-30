package fr.deboissieu.calculassmat.bl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmployeDto;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;

public interface ParametrageBlo {

	/**
	 * Recherche les paramètres enfant et retourne une map par prénom
	 * 
	 * @return {@link Map}<{@link String}, {@link ParametrageEnfant}> paramètres
	 *         enfant par nom
	 */
	public Map<String, ParametrageEnfant> findAllParamsEnfants();

	/**
	 * Recherche le paramétrage employé par idEmploye
	 * 
	 * @param string
	 * @return {@link ParametrageEmploye}
	 */
	public ParametrageEmploye findEmployeParId(String idEmploye);

	/**
	 * Retourne la liste des employés
	 * 
	 * @return {@link List}<{@link ParametrageEmploye}>
	 */
	public List<ParametrageEmploye> findAllEmployes();

	/**
	 * Recherche le paramétrage des employés
	 * 
	 * @return Liste de {@link ParametrageEmploye} paramétrage employé
	 */
	public List<ParametrageEmploye> getAllEmployes();

	/**
	 * Recherche le paramétrage des enfants
	 * 
	 * @return Liste de {@link ParametrageEnfant} paramétrage enfant
	 */
	public Collection<ParametrageEnfant> getAllEnfants();

	/**
	 * Update de l'objet {@link ParametrageEmploye}
	 * 
	 * @param paramEmploye
	 */
	public void updateParamEmploye(ParametrageEmployeDto paramEmployeDto);

	/**
	 * Suppression du paramétrage
	 * 
	 * @param employeId
	 */
	public void deleteParamEmploye(String employeId);
}
