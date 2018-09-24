package fr.deboissieu.calculassmat.model.parametrage;

import java.util.List;

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

}
