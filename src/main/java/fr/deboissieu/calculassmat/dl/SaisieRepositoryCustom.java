package fr.deboissieu.calculassmat.dl;

import java.util.Collection;
import java.util.Date;

import fr.deboissieu.calculassmat.model.saisie.Saisie;

public interface SaisieRepositoryCustom {

	/**
	 * Recherche des saisies en fonction du mois/année passé en paramètres
	 * 
	 * @param month
	 * @return
	 */
	Collection<Saisie> findSaisieBetween(Date startDate, Date stopDate);

}
