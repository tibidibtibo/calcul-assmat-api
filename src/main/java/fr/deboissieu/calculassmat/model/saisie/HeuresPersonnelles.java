package fr.deboissieu.calculassmat.model.saisie;

import lombok.Getter;
import lombok.Setter;

public class HeuresPersonnelles {

	@Getter
	@Setter
	private String prenom;

	@Getter
	@Setter
	private String heureArrivee;

	@Getter
	@Setter
	private String heureDepart;

	public static HeuresPersonnelles of(String prenom, HoraireUnitairePersonnel horaireUnitaire) {
		HeuresPersonnelles heuresPersonnelles = new HeuresPersonnelles();
		heuresPersonnelles.setPrenom(prenom);
		switch (horaireUnitaire.getAction()) {
		case ARRIVEE:
			heuresPersonnelles.setHeureArrivee(horaireUnitaire.getHeureAction());
			break;
		case DEPART:
			heuresPersonnelles.setHeureDepart(horaireUnitaire.getHeureAction());
			break;
		}
		return heuresPersonnelles;
	}

	public void addHoraire(HoraireUnitairePersonnel horaireUnitaire) {
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
