package fr.deboissieu.calculassmat.model.certification;

import java.io.Serializable;
import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "certification")
public class Certification implements Serializable {

	private static final long serialVersionUID = 1175649907939313307L;

	@Id
	private ObjectId _id;

	private Integer month;

	private Integer year;

	private Collection<SaisieCertification> saisies;

}
