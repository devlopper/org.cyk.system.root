package org.cyk.system.root.persistence.impl;

import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;

public class PersistenceIntegrationTestHelper {

	public static Class<?>[] BASE_CLASSES = {QueryStringBuilder.class,PersistenceService.class,AbstractPersistenceService.class,GenericDao.class,GenericDaoImpl.class};
	
	
}
