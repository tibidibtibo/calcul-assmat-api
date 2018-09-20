package fr.deboissieu.calculassmat.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;

import fr.deboissieu.calculassmat.commons.dateUtils.JoursSemaineEnum;
import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import fr.deboissieu.calculassmat.commons.params.TypeGardeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametrageEnfant {

	public ParametrageEnfant(PrenomEnum prenom, TypeGardeEnum typeGarde, HeuresNormales heuresNormales,
			HorairesHebdoEcole horairesEcole) {
		this.prenom = prenom;
		this.typeGarde = typeGarde;
		this.horairesEcole = horairesEcole;
		this.heuresNormales = heuresNormales;
	}

	private PrenomEnum prenom;

	private TypeGardeEnum typeGarde;

	private HorairesHebdoEcole horairesEcole;

	private HeuresNormales heuresNormales;

	public Float getHeuresNormales(final int jourSemaine) {
		List<ParametrageJournalier> paramJours = new ArrayList<>();
		paramJours.add(this.heuresNormales.getHeuresNormalesLundi());
		paramJours.add(this.heuresNormales.getHeuresNormalesMardi());
		paramJours.add(this.heuresNormales.getHeuresNormalesMercredi());
		paramJours.add(this.heuresNormales.getHeuresNormalesJeudi());
		paramJours.add(this.heuresNormales.getHeuresNormalesVendredi());
		paramJours.add(this.heuresNormales.getHeuresNormalesSamedi());
		paramJours.add(this.heuresNormales.getHeuresNormalesDimanche());

		ParametrageJournalier paramJour = IterableUtils.find(paramJours, new Predicate<ParametrageJournalier>() {

			@Override
			public boolean evaluate(ParametrageJournalier paramJour) {
				return paramJour.getJourSemaine() == jourSemaine;
			}
		});
		return paramJours != null ? paramJour.getHeuresNormales() : null;
	}

	@Getter
	@Setter
	public static class HeuresNormales {
		private ParametrageJournalier heuresNormalesLundi;

		private ParametrageJournalier heuresNormalesMardi;

		private ParametrageJournalier heuresNormalesMercredi;

		private ParametrageJournalier heuresNormalesJeudi;

		private ParametrageJournalier heuresNormalesVendredi;

		private ParametrageJournalier heuresNormalesSamedi;

		private ParametrageJournalier heuresNormalesDimanche;

		public HeuresNormales(Float heuresNormalesLundi, Float heuresNormalesMardi, Float heuresNormalesMercredi,
				Float heuresNormalesJeudi, Float heuresNormalesVendredi, Float heuresNormalesSamedi,
				Float heuresNormalesDimanche) {
			this.heuresNormalesLundi = new ParametrageJournalier(JoursSemaineEnum.LUNDI, heuresNormalesLundi);
			this.heuresNormalesMardi = new ParametrageJournalier(JoursSemaineEnum.MARDI, heuresNormalesMardi);
			this.heuresNormalesMercredi = new ParametrageJournalier(JoursSemaineEnum.MERCREDI, heuresNormalesMercredi);
			this.heuresNormalesJeudi = new ParametrageJournalier(JoursSemaineEnum.JEUDI, heuresNormalesJeudi);
			this.heuresNormalesVendredi = new ParametrageJournalier(JoursSemaineEnum.VENDREDI, heuresNormalesVendredi);
			this.heuresNormalesSamedi = new ParametrageJournalier(JoursSemaineEnum.SAMEDI, heuresNormalesSamedi);
			this.heuresNormalesDimanche = new ParametrageJournalier(JoursSemaineEnum.DIMANCHE, heuresNormalesDimanche);
		}
	}

	@Getter
	@Setter
	public static class ParametrageJournalier {

		private int jourSemaine;

		private float heuresNormales;

		public ParametrageJournalier(JoursSemaineEnum jourEnum, float heuresNormales) {
			this.jourSemaine = jourEnum.getJour();
			this.heuresNormales = heuresNormales;
		}

	}

	@Getter
	@Setter
	public static class HorairesHebdoEcole {

		private HoraireJournalierEcole horairesLundi;

		private HoraireJournalierEcole horairesMardi;

		private HoraireJournalierEcole horairesMercredi;

		private HoraireJournalierEcole horairesJeudi;

		private HoraireJournalierEcole horairesVendredi;

		private HoraireJournalierEcole horairesSamedi;

		private HoraireJournalierEcole horairesDimanche;

		public HorairesHebdoEcole(HoraireJournalierEcole horairesLundi, HoraireJournalierEcole horairesMardi,
				HoraireJournalierEcole horairesMercredi, HoraireJournalierEcole horairesJeudi,
				HoraireJournalierEcole horairesVendredi, HoraireJournalierEcole horairesSamedi,
				HoraireJournalierEcole horairesDimanche) {
			this.horairesLundi = horairesLundi;
			this.horairesMardi = horairesMardi;
			this.horairesMercredi = horairesMercredi;
			this.horairesJeudi = horairesJeudi;
			this.horairesVendredi = horairesVendredi;
			this.horairesSamedi = horairesSamedi;
			this.horairesDimanche = horairesDimanche;
		}

	}

	@Getter
	@Setter
	public static class HoraireJournalierEcole {

		private int jourSemaine;

		private HorairesEcole horairesEcole;

		public HoraireJournalierEcole(JoursSemaineEnum jourEnum, HorairesEcole horairesEcole) {
			this.jourSemaine = jourEnum.getJour();
			this.horairesEcole = horairesEcole;
		}

	}

	@Getter
	@Setter
	public static class HorairesEcole {
		private LocalTime heureDebutMatin;

		private LocalTime heureFinMatin;

		private LocalTime heureDebutAprem;

		private LocalTime heureFinAprem;

		public HorairesEcole(LocalTime heureDebutMatin, LocalTime heureFinMatin, LocalTime heureDebutAprem,
				LocalTime heureFinAprem) {
			this.heureDebutMatin = heureDebutMatin;
			this.heureFinMatin = heureFinMatin;
			this.heureDebutAprem = heureDebutAprem;
			this.heureFinAprem = heureFinAprem;
		}

	}

}
