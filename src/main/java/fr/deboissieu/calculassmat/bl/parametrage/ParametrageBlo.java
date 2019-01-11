package fr.deboissieu.calculassmat.bl.parametrage;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmployeDto;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfantDto;

public interface ParametrageBlo {

	/**
	 * Recherche les paramètres enfant et retourne une map par prénom
	 * 
	 * @return {@link Map}<{@link String}, {@link ParametrageEnfant}> paramètres
	 *         enfant par nom
	 */
	Map<String, ParametrageEnfant> getMapNomParamsEnfants();

	/**
	 * Recherche les paramètres enfant et retourne une map par id
	 * 
	 * @return {@link Map}<{@link String}, {@link ParametrageEnfant}> paramètres
	 *         enfant par nom
	 */
	Map<String, ParametrageEnfant> getMapIdParamsEnfants();

	/**
	 * Recherche tous les param employes et retourne une map id, valeur
	 * 
	 * @return
	 */
	Map<String, ParametrageEmploye> getMapIdParamsEmployes();

	/**
	 * Recherche les paramètres enfant et retourne une map par ObjectId
	 * 
	 * @return {@link Map}<{@link String}, {@link ParametrageEnfant}> paramètres
	 *         enfant par nom
	 */
	Map<ObjectId, ParametrageEnfant> getMapObjectIdParamsEnfants();

	/**
	 * Recherche les paramètres employes et retourne une map par ObjectId
	 * 
	 * @return
	 */
	Map<ObjectId, ParametrageEmploye> getMapObjectIdParamsEmployes();

	/**
	 * Recherche le paramétrage employé par idEmploye
	 * 
	 * @param string
	 * @return {@link ParametrageEmploye}
	 */
	ParametrageEmploye findEmployeParId(String idEmploye);

	/**
	 * Recherche le paramétrage employé par nom
	 * 
	 * @param nomEmploye
	 * @return {@link ParametrageEmploye}
	 */
	ParametrageEmploye findEmployeParNom(String nomEmploye);

	/**
	 * Retourne la liste des employés
	 * 
	 * @return {@link List}<{@link ParametrageEmploye}>
	 */
	List<ParametrageEmploye> findAllEmployes();

	/**
	 * Recherche le paramétrage des employés
	 * 
	 * @return Liste de {@link ParametrageEmploye} paramétrage employé
	 */
	Collection<ParametrageEmploye> getAllEmployes();

	/**
	 * Recherche le paramétrage des enfants
	 * 
	 * @return Liste de {@link ParametrageEnfant} paramétrage enfant
	 */
	Collection<ParametrageEnfant> getAllEnfants();

	/**
	 * Update de l'objet {@link ParametrageEmploye}
	 * 
	 * @param paramEmploye
	 */
	void updateParamEmploye(ParametrageEmployeDto paramEmployeDto);

	/**
	 * Suppression du paramétrage
	 * 
	 * @param employeId
	 */
	void deleteParamEmploye(String employeId);

	/**
	 * Retourne le paramétrage Enfant avec les Employes consolidés avec le
	 * Paramétrage Employé
	 * 
	 * @return
	 */
	Collection<ParametrageEnfantDto> getParametrageEnfantConsolide();

}
