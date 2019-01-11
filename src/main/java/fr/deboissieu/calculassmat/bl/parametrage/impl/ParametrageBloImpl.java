package fr.deboissieu.calculassmat.bl.parametrage.impl;

import java.util.Collection;
import java.util.HashMap;
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
import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfantDto;

@Component
public class ParametrageBloImpl implements ParametrageBlo {

	@Resource
	ParamEnfantRepository paramEnfantRepository;

	@Resource
	ParamEmployeRepository paramEmployeRepository;

	// ------------------- //
	// ----- ENFANTS ----- //
	// ------------------- //
	public Map<String, ParametrageEnfant> getMapNomParamsEnfants() {
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

	@Override
	public Collection<ParametrageEnfantDto> getParametrageEnfantConsolide() {
		Collection<ParametrageEmploye> paramEmployes = this.getAllEmployes();
		Collection<ParametrageEmployeDto> paramsEmployesDto = ParametrageEmployeDto.fromList(paramEmployes);
		return ParametrageEnfantDto.from(this.getAllEnfants(), paramsEmployesDto);
	}

	@Override
	public Map<String, ParametrageEnfant> getMapIdParamsEnfants() {
		Collection<ParametrageEnfant> listeEnfants = this.getAllEnfants();
		if (CollectionUtils.isNotEmpty(listeEnfants)) {
			return listeEnfants.stream()
					.collect(Collectors.toMap(entry -> {
						return entry.get_id().toHexString();
					}, Function.identity()));
		}
		return new HashMap<>();
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

	@Override
	public Map<String, ParametrageEmploye> getMapIdParamsEmployes() {
		Collection<ParametrageEmploye> listeEmployes = this.findAllEmployes();
		if (CollectionUtils.isNotEmpty(listeEmployes)) {
			return listeEmployes.stream()
					.collect(Collectors.toMap(entry -> {
						return entry.get_id().toHexString();
					}, Function.identity()));
		}
		return new HashMap<>();
	}

	@Override
	public Map<ObjectId, ParametrageEmploye> getMapObjectIdParamsEmployes() {
		Collection<ParametrageEmploye> listeEmployes = this.findAllEmployes();
		if (CollectionUtils.isNotEmpty(listeEmployes)) {
			return listeEmployes.stream()
					.collect(Collectors.toMap(ParametrageEmploye::get_id, Function.identity()));
		}
		return new HashMap<>();
	}

}
