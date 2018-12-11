package fr.deboissieu.calculassmat.model.saisie;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "saisies")
public class Saisie extends SaisieBase implements Serializable {

	private static final long serialVersionUID = -9144993720236969626L;

	@Id
	private ObjectId _id;

	private ObjectId employeId;

	private ObjectId enfantId;

	private Date heureArrivee;

	private Date heureDepart;

}
