package org.cyk.system.root.persistence.api.metadata;

import org.cyk.system.root.model.metadata.Entity;
import org.cyk.system.root.model.metadata.EntityProperty;
import org.cyk.system.root.model.metadata.Property;
import org.cyk.system.root.persistence.api.TypedDao;

public interface EntityPropertyDao extends TypedDao<EntityProperty> {

	EntityProperty readByEntityByProperty(Entity entity,Property property);
	
}
