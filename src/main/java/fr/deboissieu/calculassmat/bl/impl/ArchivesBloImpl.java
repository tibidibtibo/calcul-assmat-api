package fr.deboissieu.calculassmat.bl.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ArchivesBlo;
import fr.deboissieu.calculassmat.bl.ValidationBlo;
import fr.deboissieu.calculassmat.dl.ArchivesRepository;
import fr.deboissieu.calculassmat.model.archives.Archive;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmployeDto;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfantDto;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@Component
public class ArchivesBloImpl implements ArchivesBlo {

	@Resource
	ArchivesRepository archivesRepository;

	@Resource
	ValidationBlo validationBlo;

	@Override
	public void archiverTraitement(Collection<SaisieJournaliere> saisie, SyntheseGarde synthese,
			int mois,
			int annee, ParametrageEmploye paramAssmat, Map<String, ParametrageEnfant> mapParamEnfants) {

		validationBlo.validerAvantArchivage(saisie, synthese);

		Archive archive = new Archive(new Date(), mois, annee, saisie, synthese,
				ParametrageEmployeDto.from(paramAssmat), ParametrageEnfantDto.from(mapParamEnfants.values()));
		archivesRepository.save(archive);
	}

	@Override
	public List<Archive> getArchives() {
		return archivesRepository.findAll();
	}

}
