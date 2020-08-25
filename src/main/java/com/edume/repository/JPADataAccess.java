package com.edume.repository;

import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JPADataAccess {

	@Autowired private EntityManager em;

	public <T> T getReferenceByNaturalId(Class<T> type, String naturalId) {
		return em.unwrap(Session.class)
				.bySimpleNaturalId(type)
				.getReference(naturalId);
	}
}
