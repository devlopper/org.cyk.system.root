package org.cyk.system.root.business.api.network;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;

public interface UniformResourceLocatorParameterBusiness extends TypedBusiness<UniformResourceLocatorParameter> {
	
	Collection<UniformResourceLocatorParameter> findByUniformResourceLocator(UniformResourceLocator uniformResourceLocator);
	
}
