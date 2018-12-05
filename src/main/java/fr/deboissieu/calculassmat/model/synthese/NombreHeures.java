package fr.deboissieu.calculassmat.model.synthese;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import fr.deboissieu.calculassmat.commons.mathsutils.MathsUtils;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NombreHeures implements Serializable {

	private static final long serialVersionUID = 6504869320805486217L;

	private Double heuresReelles;

	private Double heuresNormalesReelles;

	private Double heuresNormalesMensualisees;

	private Double heuresComplementaires;

	private Map<String, Double> nbreHeuresJour;

	public void addHeuresNormalesReelles(Double amount) {
		if (amount != null) {
			this.heuresNormalesReelles += amount;
		}
	}

	public void addHeuresReelles(Double amount) {
		if (amount != null) {
			this.heuresReelles += amount;
		}
	}

	public void addHeuresComplementaires(Double amount) {
		if (amount != null) {
			this.heuresComplementaires += amount;
		}
	}

	public NombreHeures() {
		this.heuresReelles = 0d;
		this.heuresNormalesReelles = 0d;
		this.heuresNormalesMensualisees = 0d;
		this.heuresComplementaires = 0d;
		this.nbreHeuresJour = new HashMap<>();
	}

	public void roundValues() {
		if (this.heuresReelles != null) {
			this.heuresReelles = MathsUtils.roundTo2Digits(this.heuresReelles);
		}

		if (this.heuresNormalesReelles != null) {
			this.heuresNormalesReelles = MathsUtils.roundTo2Digits(this.heuresNormalesReelles);
		}

		if (this.heuresComplementaires != null) {
			this.heuresComplementaires = MathsUtils.roundTo2Digits(this.heuresComplementaires);
		}

	}

	public void setHeuresNormalesMensualisees(Map<String, ParametrageEnfant> mapParamEnfants) {
		if (MapUtils.isNotEmpty(mapParamEnfants)) {
			if (this.heuresNormalesMensualisees == null) {
				this.heuresNormalesMensualisees = 0d;
			}
			for (ParametrageEnfant param : mapParamEnfants.values()) {
				this.heuresNormalesMensualisees += param.getHeuresNormalesMensualisees();
			}
		}
	}

	public void updateNbHeuresJour(SaisieJournaliere saisie, Double tempsEmployeJour) {

		String key = saisie.getDateSaisie().toString();
		this.nbreHeuresJour.put(key, tempsEmployeJour);

	}
}
