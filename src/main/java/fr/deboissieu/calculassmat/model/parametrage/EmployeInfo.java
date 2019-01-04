package fr.deboissieu.calculassmat.model.parametrage;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeInfo implements Serializable {

	private static final long serialVersionUID = 8049206416111968987L;

	private ObjectId employeId;

	private Double salaireNetMensualise;

	private Double heuresNormalesMensualisees;

	private List<HeuresNormale> heuresNormales;

	private Double arEcoleKm;

}
