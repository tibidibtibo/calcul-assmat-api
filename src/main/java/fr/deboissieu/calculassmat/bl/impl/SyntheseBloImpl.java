package fr.deboissieu.calculassmat.bl.impl;

import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import fr.deboissieu.calculassmat.bl.SyntheseBlo;
import fr.deboissieu.calculassmat.commons.dateUtils.DateUtils;
import fr.deboissieu.calculassmat.commons.excelfile.PrenomEnum;
import fr.deboissieu.calculassmat.dl.ParametrageRepository;
import fr.deboissieu.calculassmat.model.HeuresPersonnelles;
import fr.deboissieu.calculassmat.model.HorairesPersonnelsEtFrais;
import fr.deboissieu.calculassmat.model.NombreHeures;
import fr.deboissieu.calculassmat.model.NombreJoursTravailles;
import fr.deboissieu.calculassmat.model.ParametrageEnfant;
import fr.deboissieu.calculassmat.model.SyntheseGarde;

@Component
public class SyntheseBloImpl implements SyntheseBlo {

	private static final Logger logger = LogManager.getLogger(SyntheseBloImpl.class);

	@Resource
	ParametrageRepository parametrageRepository;

	@Override
	public SyntheseGarde calculerFraisMensuels(Map<String, HorairesPersonnelsEtFrais> mapHorairesParDate) {
		SyntheseGarde synthese = new SyntheseGarde();

		EnumMap<PrenomEnum, ParametrageEnfant> parametresEnfant = parametrageRepository.getParametrageEnfant();

		NombreJoursTravailles nbJoursTravailles = calculerJoursTravailles(mapHorairesParDate);

		NombreHeures nbHeures = calculerNbHeures(mapHorairesParDate, parametresEnfant);

		synthese.setNbJoursTravailles(nbJoursTravailles.getNbJoursTotal());

		return synthese;
	}

	private NombreHeures calculerNbHeures(Map<String, HorairesPersonnelsEtFrais> donneesAssemblees,
			EnumMap<PrenomEnum, ParametrageEnfant> parametresEnfant) {

		NombreHeures nbHeures = new NombreHeures();

		if (donneesAssemblees != null && MapUtils.isNotEmpty(donneesAssemblees)) {

			for (Map.Entry<String, HorairesPersonnelsEtFrais> entry : donneesAssemblees.entrySet()) {

				if (CollectionUtils.isNotEmpty(entry.getValue().getHeuresPersonnelles())) {

					for (HeuresPersonnelles heures : entry.getValue().getHeuresPersonnelles()) {

						ParametrageEnfant paramEnfant = parametresEnfant.get(heures.getPrenom());

						LocalTime heureArrivee = DateUtils.toLocalTime(heures.getHeureArrivee());
						LocalTime heureDepart = DateUtils.toLocalTime(heures.getHeureDepart());

						int jourSemaine = fr.deboissieu.calculassmat.commons.dateUtils.DateUtils
								.getDayOfWeek(entry.getKey());

						switch (paramEnfant.getTypeGarde()) {
						case PERISCOLAIRE:
							// paramEnfant.ge
							break;

						case PLEIN_TEMPS:
							if (heureArrivee != null && heureDepart != null) {
								Float heuresGarde = fr.deboissieu.calculassmat.commons.dateUtils.DateUtils
										.diff(heureArrivee, heureDepart);
								Float heuresNormales = paramEnfant.getHeuresNormales(jourSemaine);
								// nbHeures.addHeuresNormales(amount); // TODO TDU : continuer
							} else {
								logger.error("Impossible de calculer les horaires de {} le {}.", heures.getPrenom(),
										entry.getKey());
							}
							break;
						}

					}
				}
			}
		}
		return nbHeures;
	}

	private NombreJoursTravailles calculerJoursTravailles(Map<String, HorairesPersonnelsEtFrais> donneesAssemblees) {
		NombreJoursTravailles nbJourTravailles = new NombreJoursTravailles();
		nbJourTravailles.setNbJoursTotal(donneesAssemblees.size());
		nbJourTravailles.setNbJoursParPersonne(calculerJoursTravaillesParPersonne(donneesAssemblees));
		return nbJourTravailles;
	}

	private EnumMap<PrenomEnum, Integer> calculerJoursTravaillesParPersonne(
			Map<String, HorairesPersonnelsEtFrais> donneesAssemblees) {
		EnumMap<PrenomEnum, Integer> mapJoursParPersonne = new EnumMap<>(PrenomEnum.class);

		if (donneesAssemblees != null && MapUtils.isNotEmpty(donneesAssemblees)) {
			for (Map.Entry<String, HorairesPersonnelsEtFrais> entry : donneesAssemblees.entrySet()) {
				for (PrenomEnum prenom : PrenomEnum.values()) {
					HeuresPersonnelles horaire = IterableUtils.find(entry.getValue().getHeuresPersonnelles(),
							heures -> prenom.equals(heures.getPrenom()));
					completerMapJours(mapJoursParPersonne, prenom, horaire);

				}
			}
		}
		return mapJoursParPersonne;
	}

	private void completerMapJours(EnumMap<PrenomEnum, Integer> mapJoursParPersonne, PrenomEnum prenom,
			HeuresPersonnelles horaire) {
		if (horaire != null) {
			if (mapJoursParPersonne.get(prenom) != null) {
				mapJoursParPersonne.put(prenom, mapJoursParPersonne.get(prenom) + 1);
			} else {
				mapJoursParPersonne.put(prenom, 1);
			}
		}
	}

}
