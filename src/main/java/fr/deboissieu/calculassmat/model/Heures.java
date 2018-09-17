package fr.deboissieu.calculassmat.model;

import lombok.Getter;
import lombok.Setter;

public class Heures {

	@Getter
	@Setter
	private String heureArrivee;

	@Getter
	@Setter
	private String heureDepart;

	public static Heures of(HoraireUnitairePersonnel horaireUnitaire) {
		Heures heures = new Heures();
		switch (horaireUnitaire.getAction()) {
		case ARRIVEE:
			heures.setHeureArrivee(horaireUnitaire.getHeureAction());
			break;
		case DEPART:
			heures.setHeureDepart(horaireUnitaire.getHeureAction());
			break;
		}
		return heures;
	}

	public void addHeure(HoraireUnitairePersonnel horaireUnitaire) {
		switch (horaireUnitaire.getAction()) {
		case ARRIVEE:
			this.heureArrivee = horaireUnitaire.getHeureAction();
			break;
		case DEPART:
			this.heureDepart = horaireUnitaire.getHeureAction();
			break;
		}
	}
}
