package fr.deboissieu.calculassmat.dl;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;

public interface SaisieRepository extends MongoRepository<SaisieJournaliere, String> {

}
