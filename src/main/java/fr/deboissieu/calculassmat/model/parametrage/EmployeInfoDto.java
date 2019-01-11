package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import fr.deboissieu.calculassmat.commons.IdentifiantUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeInfoDto implements Serializable {

	private static final long serialVersionUID = -3879919505178953470L;

	private ParametrageEmployeDto paramEmploye;

	private Double salaireNetMensualise;

	private Double heuresNormalesMensualisees;

	private List<HeuresNormale> heuresNormales;

	private Double arEcoleKm;

	public static EmployeInfoDto from(EmployeInfo employeInfo, ParametrageEmployeDto paramEmployeDto) {
		EmployeInfoDto empInfoDto = new EmployeInfoDto();
		empInfoDto.setArEcoleKm(employeInfo.getArEcoleKm());
		empInfoDto.setParamEmploye(paramEmployeDto);
		empInfoDto.setHeuresNormales(employeInfo.getHeuresNormales());
		empInfoDto.setHeuresNormalesMensualisees(employeInfo.getHeuresNormalesMensualisees());
		empInfoDto.setSalaireNetMensualise(employeInfo.getSalaireNetMensualise());
		return empInfoDto;
	}

	/**
	 * Conversion de la liste d'employé en liste consolidée avec le paramétrage
	 * employé
	 * 
	 * @param employes
	 * @param paramsEmployeDto
	 * @return
	 */
	public static List<EmployeInfoDto> from(List<EmployeInfo> employes,
			Collection<ParametrageEmployeDto> paramsEmployeDto) {
		if (CollectionUtils.isNotEmpty(employes) && CollectionUtils.isNotEmpty(paramsEmployeDto)) {
			return employes.stream()
					.map(employe -> {
						ParametrageEmployeDto paramEmployeDto = paramsEmployeDto.stream()
								.filter(paramEmploye -> IdentifiantUtils.sameIds(paramEmploye.getId(),
										employe.getEmployeId()))
								.findFirst()
								.orElse(null);
						return from(employe, paramEmployeDto);
					})
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
}
