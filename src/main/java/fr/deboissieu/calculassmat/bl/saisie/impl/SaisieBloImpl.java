package fr.deboissieu.calculassmat.bl.saisie.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.saisie.SaisieBlo;
import fr.deboissieu.calculassmat.dl.SaisieRepository;
import fr.deboissieu.calculassmat.model.saisie.SaisieEnfantRequest;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

@Component
public class SaisieBloImpl implements SaisieBlo {

	@Resource
	SaisieRepository saisieRepository;

	@Override
	public void enregistrerSaisie(SaisieRequest saisie) {
		saisie.getSaisie().stream().forEach(s -> {
			// TODO : finir méthode to()
			saisieRepository.save(SaisieEnfantRequest.toSaisieJournaliere(s));
		});
	}

}
