package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import fr.deboissieu.calculassmat.commons.IdentifiantUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametrageEnfantDto implements Serializable {

	private static final long serialVersionUID = -1394153813050806610L;

	private String id;

	private List<String> employesIds;

	private String nom;

	private String typeGarde;

	private Double salaireNetMensualise;

	private Double heuresNormalesMensualisees;

	private Double arEcoleKm;

	private List<HorairesEcole> horairesEcole;

	private List<HeuresNormale> heuresNormales;

	/**
	 * Conversion listes
	 * 
	 * @param allEnfants
	 * @return
	 */
	public static Collection<ParametrageEnfantDto> from(Collection<ParametrageEnfant> allEnfants) {
		if (CollectionUtils.isNotEmpty(allEnfants)) {
			return CollectionUtils.collect(allEnfants, ParametrageEnfantDto::from);
		}
		return new ArrayList<>();
	}

	public static ParametrageEnfantDto from(ParametrageEnfant paramEnfant) {
		ParametrageEnfantDto paramEnfantDto = new ParametrageEnfantDto();
		paramEnfantDto.setId(paramEnfant.get_id().toHexString());
		paramEnfantDto.setEmployesIds(IdentifiantUtils.convertToHexIds(paramEnfant.getEmployesIds()));
		paramEnfantDto.setNom(paramEnfant.getNom());
		paramEnfantDto.setTypeGarde(paramEnfant.getTypeGarde());
		paramEnfantDto.setSalaireNetMensualise(paramEnfant.getSalaireNetMensualise());
		paramEnfantDto.setHeuresNormalesMensualisees(paramEnfant.getHeuresNormalesMensualisees());
		paramEnfantDto.setArEcoleKm(paramEnfant.getArEcoleKm());
		paramEnfantDto.setHorairesEcole(paramEnfant.getHorairesEcole());
		paramEnfantDto.setHeuresNormales(paramEnfant.getHeuresNormales());
		return paramEnfantDto;
	}
}
