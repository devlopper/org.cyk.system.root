package org.cyk.system.root.business.api.network;

import java.net.URL;
import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.network.UniformResourceLocator;

public interface UniformResourceLocatorBusiness extends AbstractEnumerationBusiness<UniformResourceLocator> {
    
	UniformResourceLocator find(URL url);
	UniformResourceLocator find(URL url,Collection<UniformResourceLocator> uniformResourceLocators);

	Boolean isAccessible(URL url);
	Boolean isAccessible(URL url,Collection<UniformResourceLocator> uniformResourceLocators);
	
	String findPath(UniformResourceLocator uniformResourceLocator);
}
