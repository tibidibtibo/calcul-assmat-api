package fr.deboissieu.calculassmat.dl.mock;

import java.util.ArrayList;
import java.util.Collection;

import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.commons.dateUtils.JoursSemaineEnum;
import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import fr.deboissieu.calculassmat.commons.params.TypeGardeEnum;
import fr.deboissieu.calculassmat.model.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.ParametrageEnfant.HeuresNormales;
import fr.deboissieu.calculassmat.model.ParametrageEnfant.HoraireJournalierEcole;
import fr.deboissieu.calculassmat.model.ParametrageEnfant.HorairesEcole;
import fr.deboissieu.calculassmat.model.ParametrageEnfant.HorairesHebdoEcole;

public class MockData {

	public static Collection<ParametrageEnfant> getParametrageEnfant() {

		Collection<ParametrageEnfant> parametrage = new ArrayList<>();

		// LOUISE
		HorairesEcole horaireEcoleStandard = new HorairesEcole(
				DateUtils.toLocalTime("09:00"),
				DateUtils.toLocalTime("12:00"),
				DateUtils.toLocalTime("13:30"),
				DateUtils.toLocalTime("16:45"));
		HorairesHebdoEcole horairesEcole = new HorairesHebdoEcole(
				new HoraireJournalierEcole(JoursSemaineEnum.LUNDI, horaireEcoleStandard),
				new HoraireJournalierEcole(JoursSemaineEnum.MARDI, horaireEcoleStandard),
				null,
				new HoraireJournalierEcole(JoursSemaineEnum.JEUDI, horaireEcoleStandard),
				new HoraireJournalierEcole(JoursSemaineEnum.VENDREDI, horaireEcoleStandard),
				null,
				null);
		HeuresNormales heuresNormalesLouise = new HeuresNormales(1.08f, 1.08f, 0f, 1.08f, 0f, 0f, 0f);
		ParametrageEnfant paramLouise = new ParametrageEnfant(PrenomEnum.LOUISE, TypeGardeEnum.PERISCOLAIRE,
				heuresNormalesLouise, horairesEcole);
		parametrage.add(paramLouise);

		// JOSEPHINE
		HeuresNormales heuresNormalesJosephine = new HeuresNormales(9.25f, 9.25f, 0f, 9.25f, 8f, 0f, 0f);
		ParametrageEnfant paramJosephine = new ParametrageEnfant(PrenomEnum.JOSEPHINE, TypeGardeEnum.PLEIN_TEMPS,
				heuresNormalesJosephine, null);
		parametrage.add(paramJosephine);

		return parametrage;
	}
}
