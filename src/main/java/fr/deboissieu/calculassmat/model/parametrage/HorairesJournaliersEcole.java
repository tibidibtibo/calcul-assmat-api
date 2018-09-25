package fr.deboissieu.calculassmat.model.parametrage;

import java.time.LocalTime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
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

	public LocalTime getArriveeMatin() {
		return this.aM != null ? DateUtils.toLocalTime(this.aM) : null;
	}

	public LocalTime getDepartMatin() {
		return this.dM != null ? DateUtils.toLocalTime(this.dM) : null;
	}

	public LocalTime getArriveeAprem() {
		return this.aA != null ? DateUtils.toLocalTime(this.aA) : null;
	}

	public LocalTime getDepartAprem() {
		return this.dA != null ? DateUtils.toLocalTime(this.dA) : null;
	}

}
