package org.cyk.system.root.persistence.impl.network;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.persistence.api.network.UniformResourceLocatorParameterDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class UniformResourceLocatorParameterDaoImpl extends AbstractTypedDao<UniformResourceLocatorParameter> implements UniformResourceLocatorParameterDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByUniformResourceLocator;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByUniformResourceLocator, _select().where(UniformResourceLocatorParameter.FIELD_UNIFORM_RESOURCE_LOCATOR));
	}
	
	

	@Override
	public Collection<UniformResourceLocatorParameter> readByUniformResourceLocator(UniformResourceLocator uniformResourceLocator) {
		return namedQuery(readByUniformResourceLocator).parameter(UniformResourceLocatorParameter.FIELD_UNIFORM_RESOURCE_LOCATOR, uniformResourceLocator)
				.resultMany();
	}

	
	

}
 