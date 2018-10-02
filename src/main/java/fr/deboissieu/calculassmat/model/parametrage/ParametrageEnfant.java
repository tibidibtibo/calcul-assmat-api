package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "enfants")
public class ParametrageEnfant implements Serializable {

	private static final long serialVersionUID = -4571043328713820093L;

	@Id
	private ObjectId _id;

	private ObjectId employeId;

	private String nom;

	private String typeGarde;

	private Double salaireNetMensualise;

	private Double heuresNormalesMensualisees;

	private Double arEcoleKm;

	private List<HorairesEcole> horairesEcole;

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

	public String getNom() {
		return this.nom;
	}

}
