package fr.deboissieu.calculassmat.model;

import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import lombok.Getter;
import lombok.Setter;

public class HeuresPersonnelles {

	@Getter
	@Setter
	private PrenomEnum prenom;

	@Getter
	@Setter
	private String heureArrivee;

	@Getter
	@Setter
	private String heureDepart;

	public static HeuresPersonnelles of(PrenomEnum prenom, HoraireUnitairePersonnel horaireUnitaire) {
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
