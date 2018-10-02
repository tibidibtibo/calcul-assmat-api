package fr.deboissieu.calculassmat.dl;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;

public interface ParamEnfantRepository extends MongoRepository<ParametrageEnfant, String> {

	ParametrageEnfant findByNom(String nom);

}
