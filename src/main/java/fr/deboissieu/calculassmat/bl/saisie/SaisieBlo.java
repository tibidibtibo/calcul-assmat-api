package fr.deboissieu.calculassmat.bl.saisie;

import java.io.IOException;
import java.util.Collection;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

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

	/**
	 * Importe les saisies à partir d'un fichier Excel
	 * 
	 * @param numeroMois
	 * @param numeroAnnee
	 * @param fileName
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	void importerFichierSaisie(Integer numeroMois, Integer numeroAnnee, String fileName)
			throws InvalidFormatException, IOException;

	/**
	 * Suppression d'une saisie
	 * 
	 * @param identifiant
	 */
	void supprimerSaisie(String identifiant);

}
