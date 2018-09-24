package fr.deboissieu.calculassmat.model.saisie;

import lombok.Getter;
import lombok.Setter;

public class HoraireUnitairePersonnel extends ActionHoraire {

	@Getter
	@Setter
	private String prenom;

	public static HoraireUnitairePersonnel of(HoraireUnitaire horaireUnitaire, String prenom) {
		HoraireUnitairePersonnel horaireUnitairePerso = new HoraireUnitairePersonnel();
		horaireUnitairePerso.setAction(horaireUnitaire.getAction());
		horaireUnitairePerso.setHeureAction(horaireUnitaire.getHeureAction());
		horaireUnitairePerso.setPrenom(prenom);
		return horaireUnitairePerso;
	}

}
