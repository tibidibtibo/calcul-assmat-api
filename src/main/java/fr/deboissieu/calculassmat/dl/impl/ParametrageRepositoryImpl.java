package fr.deboissieu.calculassmat.dl.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.EnumMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import fr.deboissieu.calculassmat.dl.ParametrageRepository;
import fr.deboissieu.calculassmat.dl.mock.MockData;
import fr.deboissieu.calculassmat.model.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageGarde;

@Component
public class ParametrageRepositoryImpl implements ParametrageRepository {

	private static final Logger logger = LogManager.getLogger(ParametrageRepositoryImpl.class);

	@Override
	public EnumMap<PrenomEnum, ParametrageEnfant> getParametrageEnfant() {
		EnumMap<PrenomEnum, ParametrageEnfant> parametres = new EnumMap<>(PrenomEnum.class);
		Collection<ParametrageEnfant> paramEnfants = MockData.getParametrageEnfant();
		if (CollectionUtils.isNotEmpty(paramEnfants)) {
			for (ParametrageEnfant param : paramEnfants) {
				parametres.put(param.getPrenom(), param);
			}
		}
		return parametres;
	}

	public ParametrageGarde getParametrageGarde() {

		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(getClass().getClassLoader().getResource("parametrage.json").getFile()));
			ParametrageGarde parametrageGarde = new Gson().fromJson(bufferedReader, ParametrageGarde.class);
			// TODO : mapper json avec Gson
			return parametrageGarde;
		} catch (FileNotFoundException e) {
			logger.error("Impossible de lire le fichier JSON de paramétrage : {}", e);
		}

		return null;
	}

}
