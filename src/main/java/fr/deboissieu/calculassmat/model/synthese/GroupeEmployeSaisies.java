package fr.deboissieu.calculassmat.model.synthese;

import java.util.Collection;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.saisie.Saisie;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupeEmployeSaisies {

	private ParametrageEmploye paramEmploye;

	Collection<Saisie> saisies;

}
