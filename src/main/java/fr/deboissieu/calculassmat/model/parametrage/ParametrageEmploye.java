package fr.deboissieu.calculassmat.model.parametrage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametrageEmploye {

	@SerializedName("nom")
	@Expose
	private String nom;

	@SerializedName("salaireBrutHoraire")
	@Expose
	private Double salaireBrutHoraire;

	@SerializedName("salaireNetHoraire")
	@Expose
	private Double salaireNetHoraire;

	@SerializedName("indemnitesKm")
	@Expose
	private Double indemnitesKm;

	@SerializedName("indemnitesEntretien")
	@Expose
	private Double indemnitesEntretien;

	@SerializedName("fraisDejeuner")
	@Expose
	private Double fraisDejeuner;

	@SerializedName("fraisGouter")
	@Expose
	private Double fraisGouter;

	@SerializedName("tauxCongesPayes")
	@Expose
	private Double tauxCongesPayes;

}
