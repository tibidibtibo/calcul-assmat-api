package fr.deboissieu.calculassmat.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.deboissieu.calculassmat.bl.certification.CertificationBlo;
import fr.deboissieu.calculassmat.bl.saisie.SaisieBlo;
import fr.deboissieu.calculassmat.bl.validation.ValidationBlo;
import fr.deboissieu.calculassmat.configuration.LogCall;
import fr.deboissieu.calculassmat.model.certification.Certification;
import fr.deboissieu.calculassmat.model.certification.CertificationRequest;

@RestController
@RequestMapping("/certification")
@CrossOrigin(origins = "http://localhost:8888", maxAge = 3600)
public class CertificationController {

	@Resource
	private ValidationBlo validationBlo;

	@Resource
	CertificationBlo certificationBlo;

	@Resource
	private SaisieBlo saisieBlo;

	@LogCall
	@RequestMapping(produces = "application/json", method = { RequestMethod.GET }, path = "/{year}/{month}")
	public Certification findCertification(@PathVariable String month, @PathVariable String year) {
		Integer mois = validationBlo.validerPathParamCalculMoisAnnee(month);
		Integer annee = validationBlo.validerPathParamCalculMoisAnnee(year);
		return certificationBlo.findCertifByMonth(mois, annee);
	}

	@LogCall
	@RequestMapping(produces = "application/json", method = {
			RequestMethod.POST }, path = "/{year}/{month}")
	public void certifier(@PathVariable String month, @PathVariable String year,
			@RequestBody CertificationRequest request) {
		Integer mois = validationBlo.validerPathParamCalculMoisAnnee(month);
		Integer annee = validationBlo.validerPathParamCalculMoisAnnee(year);
		validationBlo.validerCertification(request);
		certificationBlo.certifier(request, mois, annee);
	}
}
