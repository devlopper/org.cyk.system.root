package org.cyk.system.root.business.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.persistence.impl.PersistenceIntegrationTestHelper;


public class BusinessIntegrationTestHelper {

    
	@SuppressWarnings("unchecked")
    public static Class<?>[] BASE_CLASSES = ArrayUtils.addAll(PersistenceIntegrationTestHelper.BASE_CLASSES,
	        BusinessService.class,AbstractBusinessService.class,GenericBusiness.class,GenericBusinessServiceImpl.class);
	
	
}
