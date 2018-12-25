package fr.deboissieu.calculassmat.dl;

import fr.deboissieu.calculassmat.model.certification.Certification;

public interface CertificationRepositoryCustom {

	/**
	 * Recherche par mois/année
	 * 
	 * @param month
	 * @param year
	 * @return {@link Certification}
	 */
	Certification findByMonth(Integer month, Integer year);
}
