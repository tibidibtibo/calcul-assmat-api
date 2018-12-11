package fr.deboissieu.calculassmat.bl.saisie.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.saisie.SaisieBlo;
import fr.deboissieu.calculassmat.dl.SaisieRepository;
import fr.deboissieu.calculassmat.model.saisie.SaisieEnfantDto;
import fr.deboissieu.calculassmat.model.saisie.SaisieRequest;

@Component
public class SaisieBloImpl implements SaisieBlo {

	@Resource
	SaisieRepository saisieRepository;

	@Override
	public void enregistrerSaisie(SaisieRequest saisie) {
		saisie.getSaisie().stream().forEach(s -> {
			saisieRepository.save(SaisieEnfantDto.toSaisie(s));
		});
	}

	@Override
	public Collection<SaisieEnfantDto> findSaisiesByMonth(Integer month, Integer year) {
		return null; // TODO : recherche / dto
	}

}
