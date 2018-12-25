package fr.deboissieu.calculassmat.dl.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import fr.deboissieu.calculassmat.dl.CertificationRepositoryCustom;
import fr.deboissieu.calculassmat.model.certification.Certification;

public class CertificationRepositoryImpl implements CertificationRepositoryCustom {

	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public Certification findByMonth(Integer month, Integer year) {

		Query query = new Query();
		query.addCriteria(Criteria.where("month").is(month));
		query.addCriteria(Criteria.where("year").is(year));

		Collection<Certification> certifs = mongoTemplate.find(query, Certification.class);

		if (CollectionUtils.isEmpty(certifs)) {
			return null;
		}

		return IterableUtils.get(certifs, 0);
	}

}
