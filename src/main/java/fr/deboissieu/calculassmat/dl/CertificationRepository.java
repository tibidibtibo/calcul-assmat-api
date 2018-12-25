package fr.deboissieu.calculassmat.dl;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.deboissieu.calculassmat.model.certification.Certification;

public interface CertificationRepository
		extends MongoRepository<Certification, ObjectId>, CertificationRepositoryCustom {

}
