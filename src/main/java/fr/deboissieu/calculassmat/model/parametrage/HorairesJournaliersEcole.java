package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;
import java.time.LocalTime;

import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorairesJournaliersEcole implements Serializable {

	private static final long serialVersionUID = 4996398262925887461L;

	private String am;

	private String dm;

	private String aa;

	private String da;

	public LocalTime getArriveeMatin() {
		return this.am != null ? DateUtils.toLocalTime(this.am) : null;
	}

	public LocalTime getDepartMatin() {
		return this.dm != null ? DateUtils.toLocalTime(this.dm) : null;
	}

	public LocalTime getArriveeAprem() {
		return this.aa != null ? DateUtils.toLocalTime(this.aa) : null;
	}

	public LocalTime getDepartAprem() {
		return this.da != null ? DateUtils.toLocalTime(this.da) : null;
	}

}
