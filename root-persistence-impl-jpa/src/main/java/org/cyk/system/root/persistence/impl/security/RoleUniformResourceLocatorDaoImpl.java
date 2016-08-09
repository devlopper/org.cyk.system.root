package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.system.root.persistence.api.security.RoleUniformResourceLocatorDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class RoleUniformResourceLocatorDaoImpl extends AbstractTypedDao<RoleUniformResourceLocator> implements RoleUniformResourceLocatorDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByPathByParameters,readByRoles;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByRoles, _select().whereIdentifierIn(RoleUniformResourceLocator.FIELD_ROLE));
	}
	
	@Override
	public RoleUniformResourceLocator readByPathByParameters(String path, Map<String, String> parameters) {
		return namedQuery(readByPathByParameters).parameter(UniformResourceLocator.FIELD_ADDRESS, path)
				.parameter(UniformResourceLocator.FIELD_PARAMETERS, parameters).ignoreThrowable(NoResultException.class).resultOne();
	}

	@Override
	public Collection<RoleUniformResourceLocator> readByRoles(Collection<Role> roles) {
		return namedQuery(readByRoles).parameterIdentifiers(roles).resultMany();
	}
	
  
    
   

}
 