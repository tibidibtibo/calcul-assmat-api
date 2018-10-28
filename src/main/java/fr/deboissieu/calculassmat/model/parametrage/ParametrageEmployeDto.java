package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametrageEmployeDto implements Serializable {

	private static final long serialVersionUID = -377022399997787296L;

	private String id;

	private String nom;

	private String prenom;

	private Double tauxHoraireNormalBrut;

	private Double tauxHoraireNormalNet;

	private Double tauxHoraireComplementaireBrut;

	private Double tauxHoraireComplementaireNet;

	private Double indemnitesKm;

	private Double indemnitesEntretien;

	private Double fraisDejeuner;

	private Double fraisGouter;

	private Double tauxCongesPayes;

	/**
	 * Conversion {@link ParametrageEmployeDto} vers {@link ParametrageEmploye}
	 * 
	 * @param ParametrageEmployeDto
	 *            paramDto
	 * @return ParametrageEmploye
	 */
	public static ParametrageEmploye from(ParametrageEmployeDto paramDto) {
		ParametrageEmploye paramEmploye = new ParametrageEmploye();
		paramEmploye.set_id(new ObjectId(paramDto.getId()));
		paramEmploye.setNom(paramDto.getNom());
		paramEmploye.setPrenom(paramDto.getPrenom());
		paramEmploye.setTauxHoraireNormalBrut(paramDto.getTauxHoraireNormalBrut());
		paramEmploye.setTauxHoraireNormalNet(paramDto.getTauxHoraireNormalNet());
		paramEmploye.setTauxHoraireComplementaireBrut(paramDto.getTauxHoraireComplementaireBrut());
		paramEmploye.setTauxHoraireComplementaireNet(paramDto.getTauxHoraireComplementaireNet());
		paramEmploye.setIndemnitesKm(paramDto.getIndemnitesKm());
		paramEmploye.setIndemnitesEntretien(paramDto.getIndemnitesEntretien());
		paramEmploye.setFraisDejeuner(paramDto.getFraisDejeuner());
		paramEmploye.setFraisGouter(paramDto.getFraisGouter());
		paramEmploye.setTauxCongesPayes(paramDto.getTauxCongesPayes());
		return paramEmploye;
	}

	/**
	 * Conversion {@link ParametrageEmploye} vers {@link ParametrageEmployeDto}
	 * 
	 * @param ParametrageEmploye
	 *            param employé
	 * @return paramDto
	 */
	public static ParametrageEmployeDto from(ParametrageEmploye paramEmploye) {
		ParametrageEmployeDto paramDto = new ParametrageEmployeDto();
		paramDto.setId(paramEmploye.get_id().toHexString());
		paramDto.setNom(paramEmploye.getNom());
		paramDto.setPrenom(paramEmploye.getPrenom());
		paramDto.setTauxHoraireNormalBrut(paramEmploye.getTauxHoraireNormalBrut());
		paramDto.setTauxHoraireNormalNet(paramEmploye.getTauxHoraireNormalNet());
		paramDto.setTauxHoraireComplementaireBrut(paramEmploye.getTauxHoraireComplementaireBrut());
		paramDto.setTauxHoraireComplementaireNet(paramEmploye.getTauxHoraireComplementaireNet());
		paramDto.setIndemnitesKm(paramEmploye.getIndemnitesKm());
		paramDto.setIndemnitesEntretien(paramEmploye.getIndemnitesEntretien());
		paramDto.setFraisDejeuner(paramEmploye.getFraisDejeuner());
		paramDto.setFraisGouter(paramEmploye.getFraisGouter());
		paramDto.setTauxCongesPayes(paramEmploye.getTauxCongesPayes());
		return paramDto;
	}

	/**
	 * Conversion listes
	 * 
	 * @param allEmployes
	 * @return
	 */
	public static Collection<ParametrageEmployeDto> from(List<ParametrageEmploye> allEmployes) {
		if (CollectionUtils.isNotEmpty(allEmployes)) {
			return CollectionUtils.collect(allEmployes, ParametrageEmployeDto::from);
		}
		return new ArrayList<>();
	}

}
