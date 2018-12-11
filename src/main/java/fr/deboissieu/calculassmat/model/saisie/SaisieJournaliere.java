package fr.deboissieu.calculassmat.model.saisie;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Document(collection = "saisies")
public class SaisieJournaliere implements Serializable {

	private static final long serialVersionUID = -1115427582930036226L;

	@Id
	private ObjectId _id;

	private Date dateSaisie;

	private String employe;

	private String enfant;

	private String heureArrivee;

	private String heureDepart;

	private Integer nbDejeuners;

	private Integer nbGouters;

	private Integer nbArEcole;

	private Double autresDeplacementKm;

}
