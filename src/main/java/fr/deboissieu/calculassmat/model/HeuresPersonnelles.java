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
	private Heures heures;

	public static HeuresPersonnelles of(PrenomEnum prenom, HoraireUnitairePersonnel horaireUnitaire) {
		HeuresPersonnelles heuresPersonnelles = new HeuresPersonnelles();
		heuresPersonnelles.setPrenom(prenom);
		heuresPersonnelles.setHeures(Heures.of(horaireUnitaire));
		return heuresPersonnelles;
	}

	public void addHoraire(HoraireUnitairePersonnel horaireUnitaire) {
		this.heures.addHeure(horaireUnitaire);
	}
}
