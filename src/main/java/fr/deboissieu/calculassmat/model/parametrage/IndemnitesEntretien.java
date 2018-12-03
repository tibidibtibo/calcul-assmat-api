package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IndemnitesEntretien implements Serializable {

	private static final long serialVersionUID = 3608719021827771855L;

	private Double borne;

	private Double indemniteInf;

	private Double indemniteSup;
}
