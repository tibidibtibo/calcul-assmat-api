package fr.deboissieu.calculassmat.model.saisie;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaisieRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1982551543133530346L;

	private Collection<SaisieEnfantRequest> saisie;
}
