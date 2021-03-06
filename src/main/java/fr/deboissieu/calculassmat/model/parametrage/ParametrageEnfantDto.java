package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametrageEnfantDto implements Serializable {

	private static final long serialVersionUID = -1394153813050806610L;

	private String id;

	private List<EmployeInfoDto> employes;

	private String nom;

	private String typeGarde;

	private List<HorairesEcole> horairesEcole;

	/**
	 * Conversion listes
	 * 
	 * @param allEnfants
	 * @return
	 */
	public static Collection<ParametrageEnfantDto> from(Collection<ParametrageEnfant> allEnfants,
			Collection<ParametrageEmployeDto> paramsEmployeDto) {
		if (CollectionUtils.isNotEmpty(allEnfants)) {
			return allEnfants.stream()
					.map(enfant -> from(enfant, paramsEmployeDto))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	public static ParametrageEnfantDto from(ParametrageEnfant paramEnfant,
			Collection<ParametrageEmployeDto> paramsEmployeDto) {
		ParametrageEnfantDto paramEnfantDto = new ParametrageEnfantDto();
		paramEnfantDto.setId(paramEnfant.get_id().toHexString());
		paramEnfantDto.setEmployes(EmployeInfoDto.from(paramEnfant.getEmployes(), paramsEmployeDto));
		paramEnfantDto.setNom(paramEnfant.getNom());
		paramEnfantDto.setTypeGarde(paramEnfant.getTypeGarde());
		paramEnfantDto.setHorairesEcole(paramEnfant.getHorairesEcole());
		return paramEnfantDto;
	}
}
