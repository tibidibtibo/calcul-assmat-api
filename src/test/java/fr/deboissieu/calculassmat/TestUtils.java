package fr.deboissieu.calculassmat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.deboissieu.calculassmat.model.parametrage.HeuresNormale;
import fr.deboissieu.calculassmat.model.parametrage.HorairesEcole;
import fr.deboissieu.calculassmat.model.parametrage.HorairesJournaliersEcole;
import fr.deboissieu.calculassmat.model.parametrage.IndemnitesEntretien;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;

public class TestUtils {

	public static ParametrageEnfant buildParametrageEnfant(String nom, String typeGarde, Double salaireNetMensualise,
			Double heuresNormalesMensualisees, Double arEcoleKm) {
		ParametrageEnfant paramEnfant = new ParametrageEnfant();

		paramEnfant.setNom(nom);
		paramEnfant.setTypeGarde(typeGarde);
		paramEnfant.setSalaireNetMensualise(salaireNetMensualise);
		paramEnfant.setHeuresNormalesMensualisees(heuresNormalesMensualisees);
		paramEnfant.setArEcoleKm(arEcoleKm);

		return paramEnfant;
	}

	public static SaisieJournaliere buildSaisie(Date dateSaisie, String prenom, String heureArrivee, String heureDepart,
			Integer nbArEcole, Double autresDeplacementKm, Integer nbDejeuners, Integer nbGouters, String employe) {
		SaisieJournaliere saisie = new SaisieJournaliere();
		saisie.setDateSaisie(dateSaisie);
		saisie.setEnfant(prenom);
		saisie.setHeureArrivee(heureArrivee);
		saisie.setHeureDepart(heureDepart);
		saisie.setNbArEcole(nbArEcole);
		saisie.setAutresDeplacementKm(autresDeplacementKm);
		saisie.setNbDejeuners(nbDejeuners);
		saisie.setNbGouters(nbGouters);
		saisie.setEmploye(employe);
		return saisie;
	}

	public static ParametrageEmploye getParametrageEmploye() {
		ParametrageEmploye employe = new ParametrageEmploye();
		employe.setTauxHoraireComplementaireNet(2.90d);
		employe.setTauxCongesPayes(0.10d);
		employe.setIndemnitesKm(0.84d);
		employe.setIndemnitesEntretien(new IndemnitesEntretien(8d, 2d, 3d));
		employe.setFraisDejeuner(1d);
		employe.setFraisGouter(0.7d);
		employe.setNom("maternelle");
		employe.setPrenom("assistante");
		return employe;
	}

	public static List<HeuresNormale> getHeuresNormales(Double lundi, Double mardi, Double mercredi, Double jeudi,
			Double vendredi,
			Double samedi, Double dimanche) {
		List<HeuresNormale> heures = new ArrayList<>();
		heures.add(new HeuresNormale(1, lundi));
		heures.add(new HeuresNormale(2, mardi));
		heures.add(new HeuresNormale(2, mercredi));
		heures.add(new HeuresNormale(4, jeudi));
		heures.add(new HeuresNormale(5, vendredi));
		heures.add(new HeuresNormale(6, samedi));
		heures.add(new HeuresNormale(7, dimanche));
		return heures;
	}

	public static List<HorairesEcole> getHorairesEcole() {
		List<HorairesEcole> horaires = new ArrayList<>();
		horaires.add(new HorairesEcole(1, new HorairesJournaliersEcole("09:00", "12:00", "13:00", "16:30")));
		horaires.add(new HorairesEcole(2, new HorairesJournaliersEcole("09:00", "12:00", "13:00", "16:30")));
		horaires.add(new HorairesEcole(3, new HorairesJournaliersEcole()));
		horaires.add(new HorairesEcole(4, new HorairesJournaliersEcole("09:00", "12:00", "13:00", "16:30")));
		horaires.add(new HorairesEcole(5, new HorairesJournaliersEcole("09:00", "12:00", "13:00", "16:30")));
		horaires.add(new HorairesEcole(6, new HorairesJournaliersEcole()));
		horaires.add(new HorairesEcole(7, new HorairesJournaliersEcole()));
		return horaires;
	}
}
