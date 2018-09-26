package fr.deboissieu.calculassmat.model.parametrage;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParametrageGarde {

	@SerializedName("employes")
	@Expose
	private List<ParametrageEmploye> employes = null;

	@SerializedName("enfants")
	@Expose
	private List<ParametrageEnfant> enfants = null;

	public ParametrageEnfant getEnfant(final String nomEnfant) {
		if (CollectionUtils.isNotEmpty(enfants)) {
			return IterableUtils.find(enfants, enfant -> nomEnfant.equals(enfant.getNom()));
		}
		return null;
	}

}
