package fr.deboissieu.calculassmat.model.saisie;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaisieRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1982551543133530346L;

	@NotNull
	@Size(min = 1)
	@Valid
	@JsonProperty("saisie")
	private Collection<SaisieEnfantDto> saisie;
}
