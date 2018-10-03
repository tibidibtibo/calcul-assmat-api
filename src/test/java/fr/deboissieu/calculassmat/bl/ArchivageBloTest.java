package fr.deboissieu.calculassmat.bl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fr.deboissieu.calculassmat.bl.impl.ArchivesBloImpl;
import fr.deboissieu.calculassmat.dl.ArchivesRepository;
import fr.deboissieu.calculassmat.model.archives.Archive;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ArchivageBloTest.Config.class })
public class ArchivageBloTest {

	public static class Config {
		@Bean
		ArchivesBlo getArchivesBlo() {
			return new ArchivesBloImpl();
		}

		@Bean
		ValidationBlo getValidationBlo() {
			return Mockito.mock(ValidationBlo.class);
		}

		@Bean
		ArchivesRepository getArchivesRepository() {
			return Mockito.mock(ArchivesRepository.class);
		}
	}

	@Resource
	ValidationBlo validationBloMock;

	@Resource
	ArchivesRepository archivesRepositoryMock;

	@Resource
	ArchivesBlo archivesBlo;

	@Test
	public void devraitArchiverLesObjets() {
		Collection<SaisieJournaliere> saisie = new ArrayList<>();
		SaisieJournaliere saisie1 = new SaisieJournaliere();
		saisie1.setPrenom("enfant1");
		saisie.add(saisie1);
		SaisieJournaliere saisie2 = new SaisieJournaliere();
		saisie2.setNbArEcole(7);
		saisie.add(saisie2);

		SyntheseGarde synthese = new SyntheseGarde(9, 2018);

		archivesBlo.archiverTraitement(saisie, synthese);

		verify(archivesRepositoryMock, times(1)).save(any(Archive.class));
	}

}
