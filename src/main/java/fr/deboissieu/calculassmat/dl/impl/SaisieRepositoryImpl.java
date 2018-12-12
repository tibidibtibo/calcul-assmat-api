package fr.deboissieu.calculassmat.dl.impl;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import fr.deboissieu.calculassmat.dl.SaisieRepositoryCustom;
import fr.deboissieu.calculassmat.model.saisie.Saisie;

public class SaisieRepositoryImpl implements SaisieRepositoryCustom {

	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public Collection<Saisie> findSaisieBetween(Date startDate, Date stopDate) {
		Query query = new Query();
		query.addCriteria(Criteria.where("dateSaisie").gte(startDate).lt(stopDate));
		return mongoTemplate.find(query, Saisie.class);
	}

}
