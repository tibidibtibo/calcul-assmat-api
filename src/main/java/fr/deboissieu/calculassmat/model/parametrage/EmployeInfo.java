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

	// "employes" : [
	// {
	// "employeId" : ObjectId("5baff2462efb71c0790b6e55"),
	// "salaireNetMensualise" : 27.48,
	// "heuresNormalesMensualisees" : 9.48,
	// "heuresNormales" : [
	// {
	// "jour" : 1,
	// "heures" : 1.083
	// },
	// {
	// "jour" : 2,
	// "heures" : 1.083
	// },
	// {
	// "jour" : 3,
	// "heures" : 0
	// },
	// {
	// "jour" : 4,
	// "heures" : 1.083
	// },
	// {
	// "jour" : 5,
	// "heures" : 0
	// },
	// {
	// "jour" : 6,
	// "heures" : 0
	// },
	// {
	// "jour" : 7,
	// "heures" : 0
	// }
	// ]
	// }
	// ]
}
