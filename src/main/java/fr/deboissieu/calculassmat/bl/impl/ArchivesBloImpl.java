package fr.deboissieu.calculassmat.bl.impl;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.ArchivesBlo;
import fr.deboissieu.calculassmat.bl.ValidationBlo;
import fr.deboissieu.calculassmat.dl.ArchivesRepository;
import fr.deboissieu.calculassmat.model.archives.Archive;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@Component
public class ArchivesBloImpl implements ArchivesBlo {

	@Resource
	ArchivesRepository archivesRepository;

	@Resource
	ValidationBlo validationBlo;

	@Override
	public void archiverTraitement(Collection<SaisieJournaliere> saisie, SyntheseGarde synthese, String employe,
			int mois,
			int annee) {

		validationBlo.validerAvantArchivage(saisie, synthese);

		Archive archive = new Archive(new Date(), mois, annee, employe, saisie, synthese);
		archivesRepository.save(archive);
	}

}
