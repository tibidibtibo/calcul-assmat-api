package fr.deboissieu.calculassmat.model;

import fr.deboissieu.calculassmat.commons.dateUtils.JoursSemaineEnum;
import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametrageEnfant {

	public ParametrageEnfant(PrenomEnum prenom, Float heuresNormalesLundi, Float heuresNormalesMardi,
			Float heuresNormalesMercredi, Float heuresNormalesJeudi, Float heuresNormalesVendredi,
			Float heuresNormalesSamedi, Float heuresNormalesDimanche) {
		this.prenom = prenom;
		this.heuresNormalesLundi = new ParametrageJournalier(JoursSemaineEnum.LUNDI, heuresNormalesLundi);
		this.heuresNormalesMardi = new ParametrageJournalier(JoursSemaineEnum.MARDI, heuresNormalesMardi);
		this.heuresNormalesMercredi = new ParametrageJournalier(JoursSemaineEnum.MERCREDI, heuresNormalesMercredi);
		this.heuresNormalesJeudi = new ParametrageJournalier(JoursSemaineEnum.JEUDI, heuresNormalesJeudi);
		this.heuresNormalesVendredi = new ParametrageJournalier(JoursSemaineEnum.VENDREDI, heuresNormalesVendredi);
		this.heuresNormalesSamedi = new ParametrageJournalier(JoursSemaineEnum.SAMEDI, heuresNormalesSamedi);
		this.heuresNormalesDimanche = new ParametrageJournalier(JoursSemaineEnum.DIMANCHE, heuresNormalesDimanche);
	}

	private PrenomEnum prenom;

	private ParametrageJournalier heuresNormalesLundi;

	private ParametrageJournalier heuresNormalesMardi;

	private ParametrageJournalier heuresNormalesMercredi;

	private ParametrageJournalier heuresNormalesJeudi;

	private ParametrageJournalier heuresNormalesVendredi;

	private ParametrageJournalier heuresNormalesSamedi;

	private ParametrageJournalier heuresNormalesDimanche;

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
}
