package fr.deboissieu.calculassmat.bl.referentiel.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.referentiel.ReferentielBlo;
import fr.deboissieu.calculassmat.commons.TypeGardeEnum;
import fr.deboissieu.calculassmat.model.referentiel.TypeGardeDto;

@Component
public class ReferentielBloImpl implements ReferentielBlo {

	@Override
	public Collection<TypeGardeDto> getReferentielTypeGarde() {
		return Arrays.asList(TypeGardeEnum.values()).stream().map(type -> new TypeGardeDto(type))
				.collect(Collectors.toList());
	}

}
