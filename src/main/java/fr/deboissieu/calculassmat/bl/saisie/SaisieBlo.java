package fr.deboissieu.calculassmat.bl.saisie;

import java.util.Collection;

import fr.deboissieu.calculassmat.model.saisie.SaisieEnfantDto;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

public interface SaisieBlo {

	/**
	 * Enregistrement d'une saisie
	 * 
	 * @param saisie
	 */
	void enregistrerSaisie(SaisieRequest saisie);

	/**
	 * Recherche des saisies pour un mois/année
	 * 
	 * @param month
	 *            : mois de la recherche
	 * @param year
	 *            : année de la recherche
	 * @return Collection<SaisieEnfantDto>
	 */
	Collection<SaisieEnfantDto> findSaisiesByMonth(Integer month, Integer year);
}
