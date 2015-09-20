package org.cyk.system.root.persistence.api.security;

import java.util.Map;

import org.cyk.system.root.model.security.ApplicationUniformResourceLocator;
import org.cyk.system.root.persistence.api.TypedDao;

public interface ApplicationUniformResourceLocatorDao extends TypedDao<ApplicationUniformResourceLocator> {

	ApplicationUniformResourceLocator readByPathByParameters(String path,Map<String,String> parameters);
    
}
