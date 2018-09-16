package fr.deboissieu.calculassmat.model;

import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import lombok.Getter;
import lombok.Setter;

public class HoraireUnitairePersonnel extends ActionHoraire {

	@Getter
	@Setter
	private PrenomEnum prenom;

	public static HoraireUnitairePersonnel of(HoraireUnitaire horaireUnitaire, PrenomEnum prenom) {
		HoraireUnitairePersonnel horaireUnitairePerso = new HoraireUnitairePersonnel();
		horaireUnitairePerso.setAction(horaireUnitaire.getAction());
		horaireUnitairePerso.setHeureAction(horaireUnitaire.getHeureAction());
		horaireUnitairePerso.setPrenom(prenom);
		return horaireUnitairePerso;
	}

}
