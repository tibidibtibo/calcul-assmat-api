package fr.deboissieu.calculassmat.dl;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.deboissieu.calculassmat.model.saisie.Saisie;

public interface SaisieRepository extends MongoRepository<Saisie, String>, SaisieRepositoryCustom {

}
