package fr.deboissieu.calculassmat.model.parametrage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeuresNormale {

	@SerializedName("jour")
	@Expose
	private Integer jour;

	@SerializedName("heures")
	@Expose
	private Double heures;

}
