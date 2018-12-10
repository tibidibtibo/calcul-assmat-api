package fr.deboissieu.calculassmat.bl.saisie.impl;

import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.saisie.SaisieBlo;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

@Component
public class SaisieBloImpl implements SaisieBlo {

	@Override
	public void enregistrerSaisie(SaisieRequest saisie) {
		saisie.getSaisie().stream().forEach(s -> {
			System.out.println(s.getEnfant() + " / " + s.getEmploye());
		});
	}

}
