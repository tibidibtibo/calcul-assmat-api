package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeInfoDto implements Serializable {

	private static final long serialVersionUID = -3879919505178953470L;

	private String employeId;

	private Double salaireNetMensualise;

	private Double heuresNormalesMensualisees;

	private List<HeuresNormale> heuresNormales;

	private Double arEcoleKm;

	public static EmployeInfoDto from(EmployeInfo employeInfo) {
		EmployeInfoDto empInfoDto = new EmployeInfoDto();
		empInfoDto.setArEcoleKm(employeInfo.getArEcoleKm());
		empInfoDto.setEmployeId(employeInfo.getEmployeId().toHexString());
		empInfoDto.setHeuresNormales(employeInfo.getHeuresNormales());
		empInfoDto.setHeuresNormalesMensualisees(employeInfo.getHeuresNormalesMensualisees());
		empInfoDto.setSalaireNetMensualise(employeInfo.getSalaireNetMensualise());
		return empInfoDto;
	}

	public static List<EmployeInfoDto> from(List<EmployeInfo> employes) {
		if (CollectionUtils.isNotEmpty(employes)) {
			return employes.stream()
					.map(EmployeInfoDto::from)
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
}
