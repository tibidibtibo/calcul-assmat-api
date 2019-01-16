package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "enfants")
public class ParametrageEnfant implements Serializable {

	private static final long serialVersionUID = -4571043328713820093L;

	@Id
	private ObjectId _id;

	private String nom;

	private String typeGarde;

	private List<EmployeInfo> employes;

	private List<HorairesEcole> horairesEcole;

	public Double getHeuresNormales(final Integer jourSemaine, ParametrageEmploye paramEmploye) {
		EmployeInfo employeInfo = this.findEmploye(paramEmploye.get_id());
		HeuresNormale heuresTrouvees = employeInfo.getHeuresNormales().stream()
				.filter(entry -> jourSemaine.equals(entry.getJour())).findFirst().orElse(null);
		return (heuresTrouvees != null) ? heuresTrouvees.getHeures() : null;
	}

	public HorairesEcole getHorairesEcole(final Integer jourSemaine) {
		HorairesEcole horaireTrouve = this.horairesEcole.stream().filter(entry -> jourSemaine.equals(entry.getJour()))
				.findFirst().orElse(null);
		return horaireTrouve != null ? horaireTrouve : null;
	}

	public String getNom() {
		return this.nom;
	}

	public EmployeInfo findEmploye(ObjectId employeId) {
		if (CollectionUtils.isNotEmpty(this.employes)) {
			return this.employes.stream().filter(employe -> employeId.equals(employe.getEmployeId())).findAny()
					.orElse(null);
		}
		return null;
	}

}
