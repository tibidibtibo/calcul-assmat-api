package fr.deboissieu.calculassmat.model.synthese;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultatCalcul {

	private String mois;

	private String annee;

	private Collection<SyntheseGarde> synthese;
}
