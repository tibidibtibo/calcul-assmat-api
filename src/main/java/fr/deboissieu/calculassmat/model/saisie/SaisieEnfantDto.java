package fr.deboissieu.calculassmat.model.saisie;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmployeDto;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfantDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaisieEnfantDto implements Serializable {

	private static final long serialVersionUID = -3964371014259791586L;

	private String id;

	@NotNull
	private String enfant;

	private ParametrageEnfantDto paramEnfant;

	@NotNull
	private String employe;

	private ParametrageEmployeDto paramEmploye;

	@NotNull
	private Date dateSaisie;

	private Double autreKm;

	private Integer nbArEcole;

	private Integer nbDejeuner;

	private Integer nbGouter;

	private Boolean saisie;

	private Date heureArrivee;

	private Date heureDepart;

	public static Saisie toSaisie(SaisieEnfantDto saisieRequest) {
		Saisie saisie = new Saisie();
		saisie.setEmployeId(new ObjectId(saisieRequest.getEmploye()));
		saisie.setEnfantId(new ObjectId(saisieRequest.getEnfant()));
		saisie.setDateSaisie(
				org.apache.commons.lang3.time.DateUtils.truncate(saisieRequest.getDateSaisie(), Calendar.DATE));
		if (saisieRequest.getHeureArrivee() != null) {
			saisie.setHeureArrivee(
					DateUtils.getDateTime(saisieRequest.getDateSaisie(), saisieRequest.getHeureArrivee()));
		}
		if (saisieRequest.getHeureDepart() != null) {
			saisie.setHeureDepart(DateUtils.getDateTime(saisieRequest.getDateSaisie(), saisieRequest.getHeureDepart()));
		}
		saisie.setNbArEcole(saisieRequest.getNbArEcole());
		saisie.setAutresDeplacementKm(saisieRequest.getAutreKm());
		saisie.setNbDejeuners(saisieRequest.getNbDejeuner());
		saisie.setNbGouters(saisieRequest.getNbGouter());
		return saisie;
	}

	public static SaisieEnfantDto toSaisieEnfantDto(Saisie saisie, ParametrageEnfantDto paramEnfant,
			ParametrageEmployeDto paramEmploye) {
		SaisieEnfantDto saisieEnfantDto = new SaisieEnfantDto();
		saisieEnfantDto.setId(saisie.get_id().toHexString());
		saisieEnfantDto.setDateSaisie(saisie.getDateSaisie());
		saisieEnfantDto.setHeureArrivee(saisie.getHeureArrivee());
		saisieEnfantDto.setHeureDepart(saisie.getHeureDepart());
		saisieEnfantDto.setNbArEcole(saisie.getNbArEcole());
		saisieEnfantDto.setAutreKm(saisie.getAutresDeplacementKm());
		saisieEnfantDto.setNbDejeuner(saisie.getNbDejeuners());
		saisieEnfantDto.setNbGouter(saisie.getNbGouters());

		saisieEnfantDto.setEmploye(saisie.getEmployeId().toHexString());
		saisieEnfantDto.setParamEmploye(paramEmploye);
		saisieEnfantDto.setEnfant(saisie.getEnfantId().toHexString());
		saisieEnfantDto.setParamEnfant(paramEnfant);
		return saisieEnfantDto;
	}

	public static Collection<SaisieEnfantDto> toSaisieEnfantDto(Collection<Saisie> saisies,
			Map<String, ParametrageEnfant> paramsEnfant, Map<String, ParametrageEmploye> paramsEmploye) {
		return saisies.stream()
				.map(saisie -> {
					// TODO : à finir
					return new SaisieEnfantDto();
					// return toSaisieEnfantDto(saisie,
					// ParametrageEnfantDto.from(paramsEnfant.get(saisie.getEnfantId()),
					// paramsEmploye.get(saisie.getEmployeId()));
				})
				.collect(Collectors.toList());
	}
}
