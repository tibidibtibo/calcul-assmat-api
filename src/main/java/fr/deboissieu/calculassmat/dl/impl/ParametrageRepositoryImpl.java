package fr.deboissieu.calculassmat.dl.impl;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import fr.deboissieu.calculassmat.dl.ParametrageRepository;
import fr.deboissieu.calculassmat.model.parametrage.ParametrageGarde;

@Component
public class ParametrageRepositoryImpl implements ParametrageRepository {

	private static final Logger logger = LogManager.getLogger(ParametrageRepositoryImpl.class);

	@Override
	public ParametrageGarde getParametrageGarde() {

		try (BufferedReader bufferedReader = new BufferedReader(
				new FileReader(getClass().getClassLoader().getResource("parametrage.json").getFile()))) {

			return new Gson().fromJson(bufferedReader, ParametrageGarde.class);

		} catch (Exception e) {
			logger.error("Impossible de lire le fichier JSON de paramétrage : {}", e);
		}

		return null;
	}

}
