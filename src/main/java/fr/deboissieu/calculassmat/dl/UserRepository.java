package fr.deboissieu.calculassmat.dl;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.deboissieu.calculassmat.model.authentication.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
	User findByUsername(String username);
}
