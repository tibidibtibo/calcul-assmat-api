package fr.deboissieu.calculassmat.model.parametrage;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enfant {

	@SerializedName("nom")
	@Expose
	private String nom;

	@SerializedName("typeGarde")
	@Expose
	private String typeGarde;

	@SerializedName("salaireNetMensualise")
	@Expose
	private Double salaireNetMensualise;

	@SerializedName("arEcoleKm")
	@Expose
	private Double arEcoleKm;

	@SerializedName("horairesEcole")
	@Expose
	private List<HorairesEcole> horairesEcole;

	@SerializedName("heuresNormales")
	@Expose
	private List<HeuresNormale> heuresNormales;

}
