package fr.deboissieu.calculassmat.bl.parametrage.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.parametrage.ParametrageBlo;
import fr.deboissieu.calculassmat.dl.ParamEmployeRepository;
import fr.deboissieu.calculassmat.dl.ParamEnfantRepository;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmploye;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEmployeDto;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;

@Component
public class ParametrageBloImpl implements ParametrageBlo {

	@Resource
	ParamEnfantRepository paramEnfantRepository;

	@Resource
	ParamEmployeRepository paramEmployeRepository;

	// ------------------- //
	// ----- ENFANTS ----- //
	// ------------------- //
	public Map<String, ParametrageEnfant> getMapIdParamsEnfants() {
		Collection<ParametrageEnfant> paramsEnfant = paramEnfantRepository.findAll();
		if (CollectionUtils.isNotEmpty(paramsEnfant)) {

			return paramsEnfant
					.stream()
					.collect(Collectors.toMap(ParametrageEnfant::getNom, Function.identity()));
		}
		return null;
	}

	public Map<ObjectId, ParametrageEnfant> getMapObjectIdParamsEnfants() {
		Collection<ParametrageEnfant> paramsEnfant = paramEnfantRepository.findAll();
		if (CollectionUtils.isNotEmpty(paramsEnfant)) {

			return paramsEnfant
					.stream()
					.collect(Collectors.toMap(ParametrageEnfant::get_id, Function.identity()));
		}
		return null;
	}

	@Override
	public Collection<ParametrageEnfant> getAllEnfants() {
		return paramEnfantRepository.findAll();
	}

	// ---------------------//
	// ----- EMPLOYES ----- //
	// -------------------- //
	@Override
	public ParametrageEmploye findEmployeParId(String idEmploye) {
		ObjectId objectIdEmploye = new ObjectId(idEmploye);
		return paramEmployeRepository.findBy_id(objectIdEmploye);
	}

	@Override
	public List<ParametrageEmploye> findAllEmployes() {
		return paramEmployeRepository.findAll();
	}

	@Override
	public List<ParametrageEmploye> getAllEmployes() {
		return paramEmployeRepository.findAll();
	}

	@Override
	public void updateParamEmploye(ParametrageEmployeDto paramEmployeDto) {
		ParametrageEmploye paramEmploye = ParametrageEmployeDto.from(paramEmployeDto);
		paramEmployeRepository.save(paramEmploye);
	}

	@Override
	public void deleteParamEmploye(String employeId) {
		// FIXME : mettre à jour paramEnfant
		paramEmployeRepository.deleteById(employeId);
	}

	@Override
	public ParametrageEmploye findEmployeParNom(String nomEmploye) {
		return paramEmployeRepository.findByNom(nomEmploye);
	}
}
