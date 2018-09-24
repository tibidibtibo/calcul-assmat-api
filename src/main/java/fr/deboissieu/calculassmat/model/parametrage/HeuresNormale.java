package fr.deboissieu.calculassmat.model.parametrage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeuresNormale {

	@SerializedName("jour")
	@Expose
	private Integer jour;

	@SerializedName("heures")
	@Expose
	private Double heures;

}
