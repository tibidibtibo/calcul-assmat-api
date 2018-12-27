package fr.deboissieu.calculassmat.bl.certification;

import fr.deboissieu.calculassmat.model.certification.Certification;
import fr.deboissieu.calculassmat.model.certification.CertificationRequest;

public interface CertificationBlo {

	/**
	 * Recherche la certification par mois/année
	 * 
	 * @param mois
	 * @param annee
	 * @return
	 */
	Certification findCertifByMonth(Integer mois, Integer annee);

	/**
	 * Certification
	 * 
	 * @param request
	 * @param mois
	 * @param annee
	 */
	void certifier(CertificationRequest request, Integer mois, Integer annee);
}
