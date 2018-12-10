package fr.deboissieu.calculassmat.bl.validation;

import java.util.Collection;
import java.util.Map;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

public interface ValidationBlo {

	/**
	 * Validation de saisie d'un entier
	 * 
	 * @param pathParam
	 * @return entier
	 */
	int validerPathParamCalculMoisAnnee(String pathParam);

	/**
	 * Validation du nom de l'employé (avec check en base)
	 * 
	 * @param idEmploye
	 * @return
	 */
	String validerPathParamNomAssmat(String idEmploye);

	/**
	 * Validation des données avant synthèse
	 * 
	 * @param Collection<SaisieJournaliere>
	 *            donneesSaisies
	 * @param ParametrageEmploye
	 *            paramAssmat
	 * @param Map<String,
	 *            ParametrageEnfant> mapParamEnfants
	 */
	void validerAvantCalcul(Collection<SaisieJournaliere> donneesSaisies,
			Map<String, ParametrageEnfant> mapParamEnfants);

	/**
	 * Validation des données avant archivage en base
	 * 
	 * @param SaisieJournaliere
	 *            saisie
	 * @param SyntheseGarde
	 *            synthese
	 */
	void validerAvantArchivage(Collection<SaisieJournaliere> saisie, SyntheseGarde synthese);

	/**
	 * Validation de saisies à enregistrer
	 * 
	 * @param saisie
	 */
	void validerSaisie(SaisieRequest saisie);
}
