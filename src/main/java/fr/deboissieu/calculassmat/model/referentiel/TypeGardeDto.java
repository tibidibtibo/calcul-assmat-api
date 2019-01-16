package fr.deboissieu.calculassmat.model.referentiel;

import fr.deboissieu.calculassmat.commons.TypeGardeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeGardeDto {

	public TypeGardeDto(TypeGardeEnum type) {
		this.code = type.getTypeGarde();
		this.libelle = type.getLibelle();
	}

	private String code;

	private String libelle;
}
