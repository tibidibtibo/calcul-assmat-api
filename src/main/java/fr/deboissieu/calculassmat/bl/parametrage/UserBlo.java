package fr.deboissieu.calculassmat.bl.parametrage;

import java.util.List;

import org.bson.types.ObjectId;

import fr.deboissieu.calculassmat.model.authentication.User;

public interface UserBlo {

	User save(User user);

	List<User> findAll();

	void delete(ObjectId id);

	User findOne(String username);

	User findById(String hexId);

}
