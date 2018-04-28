package org.cyk.system.root.persistence.api.information;

import org.cyk.system.root.model.information.Entity;
import org.cyk.system.root.model.information.EntityProperty;
import org.cyk.system.root.model.information.Property;
import org.cyk.system.root.persistence.api.TypedDao;

public interface EntityPropertyDao extends TypedDao<EntityProperty> {

	EntityProperty readByEntityByProperty(Entity entity,Property property);
	
}
