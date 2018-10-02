package fr.deboissieu.calculassmat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.deboissieu.calculassmat.model.parametrage.HeuresNormale;
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
			Integer nbArEcole, Double autresDeplacementKm, Integer nbDejeuners, Integer nbGouters) {
		SaisieJournaliere saisie = new SaisieJournaliere();
		saisie.setDateSaisie(dateSaisie);
		saisie.setPrenom(prenom);
		saisie.setHeureArrivee(heureArrivee);
		saisie.setHeureDepart(heureDepart);
		saisie.setNbArEcole(nbArEcole);
		saisie.setAutresDeplacementKm(autresDeplacementKm);
		saisie.setNbDejeuners(nbDejeuners);
		saisie.setNbGouters(nbGouters);
		return saisie;
	}

	public static ParametrageEmploye getParametrageEmploye() {
		ParametrageEmploye employe = new ParametrageEmploye();
		employe.setTauxHoraireComplementaireNet(2.90d);
		employe.setTauxCongesPayes(0.10d);
		employe.setIndemnitesKm(0.84d);
		employe.setIndemnitesEntretien(1.5d);
		employe.setFraisDejeuner(1d);
		employe.setFraisGouter(0.7d);
		employe.setNom("maternelle");
		employe.setPrenom("assistante");
		return employe;
	}

	public static Map<String, ParametrageEnfant> getMapParamEnfant() {
		Map<String, ParametrageEnfant> mapParamEnfants = new HashMap<>();
		ParametrageEnfant enfant1 = TestUtils.buildParametrageEnfant("enfant1", "type1", 0d, 0d, 0d);
		enfant1.setHeuresNormales(getHeuresNormales(9d, 9d, 0d, 8d, 9d, 0d, 0d));
		enfant1.setTypeGarde("TEMPS_PLEIN");
		enfant1.setHeuresNormalesMensualisees(10.1d);
		enfant1.setSalaireNetMensualise(250d);
		mapParamEnfants.put(enfant1.getNom(), enfant1);
		return mapParamEnfants;
	}

	private static List<HeuresNormale> getHeuresNormales(Double lundi, Double mardi, Double mercredi, Double jeudi,
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
}
