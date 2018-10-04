package fr.deboissieu.calculassmat.bl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
		saisie2.setPrenom("enf2");
		saisie2.setNbArEcole(7);
		saisie.add(saisie2);

		SyntheseGarde synthese = new SyntheseGarde(9, 2018);
		synthese.setMontantPaiementMensuel(1000d);

		archivesBlo.archiverTraitement(saisie, synthese, "nomAssmat", 9, 2018);

		ArgumentCaptor<Archive> archiveCaptor = ArgumentCaptor.forClass(Archive.class);

		verify(archivesRepositoryMock).save(archiveCaptor.capture());
		assertThat(archiveCaptor.getValue()).isNotNull();
		assertThat(archiveCaptor.getValue().getMois()).isEqualTo(9);
		assertThat(archiveCaptor.getValue().getAnnee()).isEqualTo(2018);
		assertThat(archiveCaptor.getValue().getEmploye()).isEqualTo("nomAssmat");
		assertThat(archiveCaptor.getValue().getHorodatage()).isNotNull();
		assertThat(archiveCaptor.getValue().getSaisie()).hasSize(2);
		assertThat(archiveCaptor.getValue().getSaisie()).extracting("prenom").contains("enfant1", "enf2");
		assertThat(archiveCaptor.getValue().getSaisie()).extracting("nbArEcole").contains(null, 7);
		assertThat(archiveCaptor.getValue().getSynthese().getAnnee()).isEqualTo("2018");
		assertThat(archiveCaptor.getValue().getSynthese().getMois()).isEqualTo("9");
		assertThat(archiveCaptor.getValue().getSynthese().getMontantPaiementMensuel()).isEqualTo(1000d);

	}

}
