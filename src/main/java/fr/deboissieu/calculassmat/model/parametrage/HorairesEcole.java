package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HorairesEcole implements Serializable {

	private static final long serialVersionUID = 2354677904506969038L;

	private Integer jour;

	private HorairesJournaliersEcole horairesJournaliersEcole;

	public boolean jourSansEcole() {
		return horairesJournaliersEcole != null && horairesJournaliersEcole.aucunHoraire();
	}

}
