package fr.deboissieu.calculassmat.model.archives;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmployeDto;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfantDto;
import fr.deboissieu.calculassmat.model.saisie.SaisieJournaliere;
import fr.deboissieu.calculassmat.model.synthese.SyntheseGarde;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "archives")
public class Archive implements Serializable {

	private static final long serialVersionUID = -1206871538044277050L;

	private Date horodatage;

	private int mois;

	private int annee;

	private Collection<SaisieJournaliere> saisie;

	private SyntheseGarde synthese;

	private ParametrageEmployeDto parametrageEmploye;

	private Collection<ParametrageEnfantDto> parametrageEnfants;
}
