package fr.deboissieu.calculassmat.dl;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.deboissieu.calculassmat.model.archives.Archive;

public interface ArchivesRepository extends MongoRepository<Archive, String> {

	// TODO : archives all
}
