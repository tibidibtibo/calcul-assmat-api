package fr.deboissieu.calculassmat.model;

import java.util.Date;
import java.util.Set;

import fr.deboissieu.calculassmat.commons.excelfile.ActionHoraireEnum;
import lombok.Getter;
import lombok.Setter;

public class HoraireUnitaireAvecFrais {

	@Getter
	@Setter
	private Set<String> qui;

	@Getter
	@Setter
	private ActionHoraireEnum action;

	@Getter
	@Setter
	private Date heureAction;

	@Getter
	@Setter
	private Set<String> repas;

	@Getter
	@Setter
	private Set<String> deplacements;

	@Getter
	@Setter
	private Integer autresDeplacementKm;

	public static HoraireUnitaireAvecFrais of(SaisieJournaliere saisie) {
		HoraireUnitaireAvecFrais horaireUnitaire = new HoraireUnitaireAvecFrais();
		horaireUnitaire.setQui(saisie.getQui());
		horaireUnitaire.setAction(ActionHoraireEnum.fromAction(saisie.getAction()));
		horaireUnitaire.setHeureAction(saisie.getHeureAction());
		horaireUnitaire.setRepas(saisie.getRepas());
		horaireUnitaire.setDeplacements(saisie.getDeplacements());
		horaireUnitaire.setAutresDeplacementKm(saisie.getAutresDeplacementKm());
		return horaireUnitaire;
	}
}
