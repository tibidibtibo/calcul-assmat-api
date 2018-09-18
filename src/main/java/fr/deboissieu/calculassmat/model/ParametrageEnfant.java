package fr.deboissieu.calculassmat.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametrageEnfant {

	public ParametrageEnfant(Float heuresNormalesLundi, Float heuresNormalesMardi, Float heuresNormalesMercredi,
			Float heuresNormalesJeudi, Float heuresNormalesVendredi, Float heuresNormalesSamedi,
			Float heuresNormalesDimanche) {
		this.heuresNormalesLundi = heuresNormalesLundi;
		this.heuresNormalesMardi = heuresNormalesMardi;
		this.heuresNormalesMercredi = heuresNormalesMercredi;
		this.heuresNormalesJeudi = heuresNormalesJeudi;
		this.heuresNormalesVendredi = heuresNormalesVendredi;
		this.heuresNormalesSamedi = heuresNormalesSamedi;
		this.heuresNormalesDimanche = heuresNormalesDimanche;
	}

	private float heuresNormalesLundi;

	private float heuresNormalesMardi;

	private float heuresNormalesMercredi;

	private float heuresNormalesJeudi;

	private float heuresNormalesVendredi;

	private float heuresNormalesSamedi;

	private float heuresNormalesDimanche;
}
