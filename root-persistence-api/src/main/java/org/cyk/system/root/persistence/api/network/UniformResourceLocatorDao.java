package org.cyk.system.root.persistence.api.network;

import java.util.Map;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public interface UniformResourceLocatorDao extends AbstractEnumerationDao<UniformResourceLocator> {

	UniformResourceLocator readByPathByParameters(String path,Map<String,String[]> parameters);
	
}
