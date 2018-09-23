package fr.deboissieu.calculassmat.model.parametrage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorairesJournaliersEcole {

	@SerializedName("AM")
	@Expose
	private String aM;

	@SerializedName("DM")
	@Expose
	private String dM;

	@SerializedName("AA")
	@Expose
	private String aA;

	@SerializedName("DA")
	@Expose
	private String dA;

}
