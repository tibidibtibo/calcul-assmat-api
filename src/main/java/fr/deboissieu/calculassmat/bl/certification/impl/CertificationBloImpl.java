package fr.deboissieu.calculassmat.bl.certification.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.ValidationException;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import fr.deboissieu.calculassmat.bl.certification.CertificationBlo;
import fr.deboissieu.calculassmat.bl.synthese.SyntheseBlo;
import fr.deboissieu.calculassmat.commons.exceptions.ValidationExceptionsEnum;
import fr.deboissieu.calculassmat.dl.CertificationRepository;
import fr.deboissieu.calculassmat.dl.SaisieRepository;
import fr.deboissieu.calculassmat.model.certification.Certification;
import fr.deboissieu.calculassmat.model.certification.CertificationRequest;
import fr.deboissieu.calculassmat.model.certification.SaisieCertification;
import fr.deboissieu.calculassmat.model.saisie.Saisie;

@Component
public class CertificationBloImpl implements CertificationBlo {

	@Resource
	CertificationRepository certificationRepository;

	@Resource
	SyntheseBlo syntheseBlo;

	@Resource
	SaisieRepository saisieRepository;

	@Override
	public Certification findCertifByMonth(Integer month, Integer year) {
		return certificationRepository.findByMonth(month, year);
	}

	@Override
	public void certifier(CertificationRequest request, Integer month, Integer year) {

		Certification certification = new Certification();
		certification.setMonth(month);
		certification.setYear(year);
		certification.setSaisies(request.getSaisies());

		// Vérification existence certification
		Certification certifExistante = certificationRepository.findByMonth(month, year);
		if (certifExistante != null) {
			throw new ValidationException(ValidationExceptionsEnum.V012.toString(month + "/" + year));
		}

		Collection<Saisie> saisies = fetchSaisies(request.getSaisies());
		certification.setSyntheses(syntheseBlo.calculerSynthese(saisies, month, year));

		certificationRepository.save(certification);
	}

	private Collection<Saisie> fetchSaisies(Collection<SaisieCertification> saisiesCertif) {

		Collection<ObjectId> saisiesIds = saisiesCertif.stream()
				.map(saisieCertif -> {
					return new ObjectId(saisieCertif.getId());
				})
				.collect(Collectors.toList());
		Iterable<Saisie> saisies = saisieRepository.findAllById(saisiesIds);
		return Lists.newArrayList(saisies);
	}

}
