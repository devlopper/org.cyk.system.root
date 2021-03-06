package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.security.BusinessServiceBusiness;
import org.cyk.system.root.business.api.security.BusinessServiceCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.security.BusinessService;
import org.cyk.system.root.model.security.BusinessServiceCollection;
import org.cyk.system.root.persistence.api.security.BusinessServiceCollectionDao;
import org.cyk.system.root.persistence.api.security.BusinessServiceDao;

public class BusinessServiceCollectionBusinessImpl extends AbstractCollectionBusinessImpl<BusinessServiceCollection,BusinessService, BusinessServiceCollectionDao,BusinessServiceDao,BusinessServiceBusiness> implements BusinessServiceCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public BusinessServiceCollectionBusinessImpl(BusinessServiceCollectionDao dao) {
		super(dao); 
	}
	
	public static class BuilderOneDimensionArray extends AbstractCollectionBusinessImpl.BuilderOneDimensionArray<BusinessServiceCollection> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(BusinessServiceCollection.class);
		}
		
	}
}
