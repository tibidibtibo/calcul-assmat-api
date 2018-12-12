package fr.deboissieu.calculassmat.model.saisie;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaisieEnfantDto implements Serializable {

	private static final long serialVersionUID = -3964371014259791586L;

	private String id;

	@NotNull
	private String enfant;

	@NotNull
	private String employe;

	@NotNull
	private Date dateSaisie;

	private Double autreKm;

	private Integer nbArEcole;

	private Integer nbDejeuner;

	private Integer nbGouter;

	private Boolean saisie;

	@NotNull
	private Date heureArrivee;

	@NotNull
	private Date heureDepart;

	public static Saisie toSaisie(SaisieEnfantDto saisieRequest) {
		Saisie saisie = new Saisie();
		saisie.setEmployeId(new ObjectId(saisieRequest.getEmploye()));
		saisie.setEnfantId(new ObjectId(saisieRequest.getEnfant()));
		saisie.setDateSaisie(
				org.apache.commons.lang3.time.DateUtils.truncate(saisieRequest.getDateSaisie(), Calendar.DATE));
		saisie.setHeureArrivee(saisieRequest.getHeureArrivee());
		saisie.setHeureDepart(saisieRequest.getHeureDepart());
		saisie.setNbArEcole(saisieRequest.getNbArEcole());
		saisie.setAutresDeplacementKm(saisieRequest.getAutreKm());
		saisie.setNbDejeuners(saisieRequest.getNbDejeuner());
		saisie.setNbGouters(saisieRequest.getNbGouter());
		return saisie;
	}

	public static SaisieEnfantDto toSaisieEnfantDto(Saisie saisie) {
		SaisieEnfantDto saisieEnfantDto = new SaisieEnfantDto();
		saisieEnfantDto.setId(saisie.get_id().toHexString());
		saisieEnfantDto.setEmploye(saisie.getEmployeId().toHexString());
		saisieEnfantDto.setEnfant(saisie.getEnfantId().toHexString());
		saisieEnfantDto.setDateSaisie(saisie.getDateSaisie());
		saisieEnfantDto.setHeureArrivee(saisie.getHeureArrivee());
		saisieEnfantDto.setHeureDepart(saisie.getHeureDepart());
		saisieEnfantDto.setNbArEcole(saisie.getNbArEcole());
		saisieEnfantDto.setAutreKm(saisie.getAutresDeplacementKm());
		saisieEnfantDto.setNbDejeuner(saisie.getNbDejeuners());
		saisieEnfantDto.setNbGouter(saisie.getNbGouters());
		return saisieEnfantDto;
	}

	public static Collection<SaisieEnfantDto> toSaisieEnfantDto(Collection<Saisie> saisies) {
		// return saisies.stream().map(saisie -> {
		// return toSaisieEnfantDto(saisie);
		// }).to;
		// TODO
	}
}
