package fr.deboissieu.calculassmat.bl;

import java.util.Collection;
import java.util.Map;

import fr.deboissieu.calculassmat.model.HorairesPersonnelsEtFrais;
import fr.deboissieu.calculassmat.model.SaisieJournaliere;

public interface AssemblageSaisieBlo {

	/**
	 * 
	 * @param {@link
	 * 			SaisieJournaliere} saisie
	 * @return {@link Map}<{@link String}, {@link HorairesPersonnelsEtFrais}> map
	 *         des horaires par Date
	 */
	Map<String, HorairesPersonnelsEtFrais> assemblerDonneesSaisies(Collection<SaisieJournaliere> saisie);
}
