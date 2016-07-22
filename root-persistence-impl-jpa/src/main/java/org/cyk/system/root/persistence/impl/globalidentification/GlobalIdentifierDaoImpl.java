package org.cyk.system.root.persistence.impl.globalidentification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;

public class GlobalIdentifierDaoImpl implements GlobalIdentifierDao {

	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public GlobalIdentifier update(GlobalIdentifier globalIdentifier) {
		return entityManager.merge(globalIdentifier);
	}

}
