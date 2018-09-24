package fr.deboissieu.calculassmat.model.saisie;

import lombok.Getter;
import lombok.Setter;

public class HoraireUnitaireAvecFrais {

	@Getter
	@Setter
	HoraireUnitaire horaireUnitaire;

	@Getter
	@Setter
	FraisJournaliers fraisJournaliers;

	public static HoraireUnitaireAvecFrais of(SaisieJournaliere saisie) {
		HoraireUnitaireAvecFrais horaireUnitaireAvecFrais = new HoraireUnitaireAvecFrais();
		horaireUnitaireAvecFrais.setHoraireUnitaire(HoraireUnitaire.of(saisie));
		horaireUnitaireAvecFrais.setFraisJournaliers(FraisJournaliers.of(saisie));
		return horaireUnitaireAvecFrais;
	}
}
