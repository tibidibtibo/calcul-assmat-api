package fr.deboissieu.calculassmat.dl;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;

public interface ParamEmployeRepository extends MongoRepository<ParametrageEmploye, String> {

	ParametrageEmploye findBy_id(ObjectId objectIdEmploye);

	ParametrageEmploye findByNom(String nomEmploye);

}
