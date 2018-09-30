package fr.deboissieu.calculassmat.dl;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;

public interface ParamEmployeRepository extends MongoRepository<ParametrageEmploye, String> {

	ParametrageEmploye findByNom(String nom);
}
