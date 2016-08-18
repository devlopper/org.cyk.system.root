package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;

import org.cyk.system.root.model.security.BusinessService;
import org.cyk.system.root.model.security.BusinessServiceCollection;
import org.cyk.system.root.persistence.api.security.BusinessServiceDao;
import org.cyk.system.root.persistence.impl.AbstractCollectionItemDaoImpl;

public class BusinessServiceDaoImpl extends AbstractCollectionItemDaoImpl<BusinessService,BusinessServiceCollection> implements BusinessServiceDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
}
 