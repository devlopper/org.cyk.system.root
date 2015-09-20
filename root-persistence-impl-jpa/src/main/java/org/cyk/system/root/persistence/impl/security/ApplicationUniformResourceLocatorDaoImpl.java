package org.cyk.system.root.persistence.impl.security;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.NoResultException;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.security.ApplicationUniformResourceLocator;
import org.cyk.system.root.persistence.api.security.ApplicationUniformResourceLocatorDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class ApplicationUniformResourceLocatorDaoImpl extends AbstractTypedDao<ApplicationUniformResourceLocator> implements ApplicationUniformResourceLocatorDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByPathByParameters;
	
	@Override
	public ApplicationUniformResourceLocator readByPathByParameters(String path, Map<String, String> parameters) {
		return namedQuery(readByPathByParameters).parameter(UniformResourceLocator.FIELD_PATH, path)
				.parameter(UniformResourceLocator.FIELD_PARAMETERS, parameters).ignoreThrowable(NoResultException.class).resultOne();
	}
	
  
    
   

}
 