package org.cyk.system.root.persistence.impl.geography;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.geography.Website;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.persistence.api.geography.WebsiteDao;

public class WebsiteDaoImpl extends AbstractContactDaoImpl<Website> implements WebsiteDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readByValue, _select().where(commonUtils.attributePath(Website.FIELD_UNIFORM_RESOURCE_LOCATOR,UniformResourceLocator.FIELD_ADDRESS),UniformResourceLocator.FIELD_ADDRESS));
    }
    
	@Override
	public Collection<Website> readByValue(String address) {
		return castCollection(namedQuery(readByValue).parameter(UniformResourceLocator.FIELD_ADDRESS, address).resultMany(),Website.class);
	}
	
	@Override
	public Long countByValue(String address) {
		return countNamedQuery(countByValue).parameter(UniformResourceLocator.FIELD_ADDRESS, address).resultOne();
	}
	
   
 
}
 