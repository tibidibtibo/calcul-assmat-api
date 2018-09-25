package fr.deboissieu.calculassmat.model.parametrage;

import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametrageEnfant {

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

	/**
	 * Valeurs de "typeGarde"
	 */
	public enum TypeGardeEnum {
		TEMPS_PLEIN("TEMPS_PLEIN"), PERISCOLAIRE("PERISCOLAIRE");

		@Getter
		@Setter
		private String typeGarde;

		TypeGardeEnum(String typeGarde) {
			this.typeGarde = typeGarde;
		}

		public static TypeGardeEnum fromString(final String value) {
			return Arrays.stream(values())
					.filter(enumEntry -> enumEntry.typeGarde.equalsIgnoreCase(value))
					.findFirst()
					.orElse(null);
		}
	}

	public Double getHeuresNormales(final Integer jourSemaine) {
		HeuresNormale heuresTrouvees = this.heuresNormales
				.stream()
				.filter(entry -> jourSemaine.equals(entry.getJour()))
				.findFirst()
				.orElse(null);
		return (heuresTrouvees != null) ? heuresTrouvees.getHeures() : null;
	}

	public HorairesEcole getHorairesEcole(final Integer jourSemaine) {
		HorairesEcole horaireTrouve = this.horairesEcole
				.stream()
				.filter(entry -> jourSemaine.equals(entry.getJour()))
				.findFirst()
				.orElse(null);
		return horaireTrouve != null ? horaireTrouve : null;
	}

}
