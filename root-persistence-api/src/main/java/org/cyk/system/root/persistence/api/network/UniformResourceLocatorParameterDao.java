package org.cyk.system.root.persistence.api.network;

import java.util.Collection;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.persistence.api.TypedDao;

public interface UniformResourceLocatorParameterDao extends TypedDao<UniformResourceLocatorParameter> {

	Collection<UniformResourceLocatorParameter> readByUniformResourceLocator(UniformResourceLocator uniformResourceLocator);
	Long countByUniformResourceLocator(UniformResourceLocator uniformResourceLocator);
	
}
