package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.security.BusinessServiceBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.security.BusinessService;
import org.cyk.system.root.model.security.BusinessServiceCollection;
import org.cyk.system.root.persistence.api.security.BusinessServiceDao;

public class BusinessServiceBusinessImpl extends AbstractCollectionItemBusinessImpl<BusinessService, BusinessServiceDao,BusinessServiceCollection> implements BusinessServiceBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public BusinessServiceBusinessImpl(BusinessServiceDao dao) {
		super(dao); 
	}
	
}
