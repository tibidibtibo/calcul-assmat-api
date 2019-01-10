package fr.deboissieu.calculassmat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.model.parametrage.EmployeInfo;
import fr.deboissieu.calculassmat.model.parametrage.HeuresNormale;
import fr.deboissieu.calculassmat.model.parametrage.HorairesEcole;
import fr.deboissieu.calculassmat.model.parametrage.HorairesJournaliersEcole;
import fr.deboissieu.calculassmat.model.parametrage.IndemnitesEntretien;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant.TypeGardeEnum;
import fr.deboissieu.calculassmat.model.saisie.Saisie;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;

public class TestUtils {

	public final static String idEnfant1 = "5baff2462efb71c0790b6e55";
	public final static ObjectId objectIdEnfant1 = new ObjectId(idEnfant1);

	public final static String idEnfant2 = "5baff2462efb71c0790b6e66";
	public final static ObjectId objectIdEnfant2 = new ObjectId(idEnfant2);

	public final static String idEnfant3 = "5baff2462efb71c0790b6e44";
	public final static ObjectId objectIdEnfant3 = new ObjectId(idEnfant3);

	public final static String idEmploye1 = "5baff2462efb71c0790b6e77";
	public final static ObjectId objectIdEmploye1 = new ObjectId(idEmploye1);

	public final static String idEmploye2 = "5baff2462efb71c0790b6e88";
	public final static ObjectId objectIdEmploye2 = new ObjectId(idEmploye2);

	public static ParametrageEnfant buildParametrageEnfant(ObjectId enfantObjId, String nom, String typeGarde) {

		ParametrageEnfant paramEnfant = new ParametrageEnfant();
		paramEnfant.set_id(enfantObjId);
		paramEnfant.setNom(nom);
		paramEnfant.setTypeGarde(typeGarde);
		return paramEnfant;
	}

	public static Saisie buildSaisie(Date dateSaisie, String enfant, String heureArrivee, String heureDepart,
			Integer nbArEcole, Double autresDeplacementKm, Integer nbDejeuners, Integer nbGouters, String employe) {
		Saisie saisie = new Saisie();
		saisie.setDateSaisie(dateSaisie);
		saisie.setEnfantId(new ObjectId(enfant));
		saisie.setEmployeId(new ObjectId(employe));
		saisie.setHeureArrivee(DateUtils.parseHeure(heureArrivee));
		saisie.setHeureDepart(DateUtils.parseHeure(heureDepart));
		saisie.setNbArEcole(nbArEcole);
		saisie.setAutresDeplacementKm(autresDeplacementKm);
		saisie.setNbDejeuners(nbDejeuners);
		saisie.setNbGouters(nbGouters);
		return saisie;
	}

	public static ParametrageEmploye getParametrageEmploye(String nom, ObjectId identifiant) {
		ParametrageEmploye employe = new ParametrageEmploye();
		employe.setTauxHoraireComplementaireNet(2.90d);
		employe.setTauxCongesPayes(0.10d);
		employe.setIndemnitesKm(0.84d);
		employe.setIndemnitesEntretien(new IndemnitesEntretien(8d, 2d, 3d));
		employe.setFraisDejeuner(1d);
		employe.setFraisGouter(0.7d);
		employe.setNom(nom);
		employe.setPrenom("assistante");
		employe.set_id(identifiant);
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

	public static SyntheseGarde buildSyntheseGarde(int month, int year, String employe) {
		SyntheseGarde synthese = new SyntheseGarde();
		synthese.initSyntheseGarde(month, year, employe);
		return synthese;
	}

	public static Map<ObjectId, ParametrageEnfant> getMapParamEnfant1() {

		Map<ObjectId, ParametrageEnfant> mapParamEnfants = new HashMap<>();

		ParametrageEnfant enfant1 = TestUtils.buildParametrageEnfant(TestUtils.objectIdEnfant1, "enfant1",
				TypeGardeEnum.TEMPS_PLEIN.getTypeGarde());
		enfant1.setEmployes(Arrays.asList(buildEmployeInfo(TestUtils.objectIdEmploye1, 0d, 10.1d, 250d,
				TestUtils.getHeuresNormales(9d, 9d, 0d, 8d, 9d, 0d, 0d))));
		mapParamEnfants.put(TestUtils.objectIdEnfant1, enfant1);

		return mapParamEnfants;
	}

	public static Map<ObjectId, ParametrageEnfant> getMapParamEnfant2() {
		Map<ObjectId, ParametrageEnfant> mapParamEnfants = new HashMap<>();

		ParametrageEnfant enfant2 = TestUtils.buildParametrageEnfant(TestUtils.objectIdEnfant2, "enfant2",
				TypeGardeEnum.PERISCOLAIRE.getTypeGarde());
		enfant2.setEmployes(Arrays.asList(buildEmployeInfo(TestUtils.objectIdEmploye1, 3.2d, 2d, 104d,
				TestUtils.getHeuresNormales(2d, 1d, 0d, 2d, 1d, 0d, 0d))));
		enfant2.setHorairesEcole(TestUtils.getHorairesEcole());
		mapParamEnfants.put(TestUtils.objectIdEnfant2, enfant2);

		return mapParamEnfants;
	}

	public static EmployeInfo buildEmployeInfo(ObjectId employeId, Double arEcoleKm, Double heuresNormalesMensualisees,
			Double salaireNetMensualise, List<HeuresNormale> heuresNormales) {
		EmployeInfo employeInfo = new EmployeInfo();
		employeInfo.setEmployeId(employeId);
		employeInfo.setArEcoleKm(arEcoleKm);
		employeInfo.setHeuresNormalesMensualisees(heuresNormalesMensualisees);
		employeInfo.setSalaireNetMensualise(salaireNetMensualise);
		employeInfo.setHeuresNormales(heuresNormales);
		return employeInfo;
	}
}
