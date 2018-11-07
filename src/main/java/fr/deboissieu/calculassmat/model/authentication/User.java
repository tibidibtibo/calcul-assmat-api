package fr.deboissieu.calculassmat.model.authentication;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "users")
public class User {

	@Id
	private ObjectId _id;

	private String username;

	private String password;

}
