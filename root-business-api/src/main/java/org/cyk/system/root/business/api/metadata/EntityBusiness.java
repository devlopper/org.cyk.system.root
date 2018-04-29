package org.cyk.system.root.business.api.metadata;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.metadata.Entity;

public interface EntityBusiness extends AbstractEnumerationBusiness<Entity> {

	Entity findByClassName(Class<?> aClass);
	
}
