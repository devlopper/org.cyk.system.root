package org.cyk.system.root.persistence.impl.globalidentification;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;

public class GlobalIdentifierDaoImpl implements GlobalIdentifierDao {

	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public GlobalIdentifier create(GlobalIdentifier globalIdentifier) {
		entityManager.persist(globalIdentifier);
		return globalIdentifier;
	}
	
	@Override
	public GlobalIdentifier update(GlobalIdentifier globalIdentifier) {
		return entityManager.merge(globalIdentifier);
	}

	@Override
	public GlobalIdentifier delete(GlobalIdentifier globalIdentifier) {
		entityManager.remove(entityManager.merge(globalIdentifier));
		return globalIdentifier;
	}

	@Override
	public Collection<GlobalIdentifier> readAll() {
		return entityManager.createQuery("SELECT r FROM GlobalIdentifier r",GlobalIdentifier.class).getResultList();
	}

}
