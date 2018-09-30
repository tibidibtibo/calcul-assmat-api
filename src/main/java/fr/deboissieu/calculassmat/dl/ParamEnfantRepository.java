package fr.deboissieu.calculassmat.dl;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.deboissieu.calculassmat.model.parametrage.ParametrageEnfant;

public interface ParamEnfantRepository extends MongoRepository<ParametrageEnfant, String> {

	ParametrageEnfant findByNom(String nom);

	public static Map<String, ParametrageEnfant> findParamsEnfants(Collection<ParametrageEnfant> paramsEnfant) {
		if (CollectionUtils.isNotEmpty(paramsEnfant)) {

			return paramsEnfant
					.stream()
					.collect(Collectors.toMap(ParametrageEnfant::getNom, Function.identity()));
		}
		return null;
	}

}
