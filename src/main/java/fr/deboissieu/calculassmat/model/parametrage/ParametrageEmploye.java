package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "employes")
public class ParametrageEmploye implements Serializable {

	private static final long serialVersionUID = -1674024421832425607L;

	@Id
	private ObjectId _id;

	private String nom;

	private String prenom;

	private Double tauxHoraireNormalBrut;

	private Double tauxHoraireNormalNet;

	private Double tauxHoraireComplementaireBrut;

	private Double tauxHoraireComplementaireNet;

	private Double indemnitesKm;

	private IndemnitesEntretien indemnitesEntretien;

	private Double fraisDejeuner;

	private Double fraisGouter;

	private Double tauxCongesPayes;

}
