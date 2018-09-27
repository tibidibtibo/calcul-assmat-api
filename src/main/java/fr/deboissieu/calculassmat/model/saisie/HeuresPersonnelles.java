package fr.deboissieu.calculassmat.model.saisie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeuresPersonnelles {

	private String prenom;

	private String heureArrivee;

	private String heureDepart;

	private Integer nbDejeuners;

	private Integer nbGouters;

	private Integer nbArEcole;

	public static HeuresPersonnelles of(String prenom, HoraireUnitairePersonnel horaireUnitaire) {
		HeuresPersonnelles heuresPersonnelles = new HeuresPersonnelles();
		heuresPersonnelles.setPrenom(prenom);
		// heuresPersonnelles.setNbArEcole(horaireUnitaire.ge);
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
