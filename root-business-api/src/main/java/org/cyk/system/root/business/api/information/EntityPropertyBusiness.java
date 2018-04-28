package org.cyk.system.root.business.api.information;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.information.Entity;
import org.cyk.system.root.model.information.EntityProperty;
import org.cyk.system.root.model.information.Property;

public interface EntityPropertyBusiness extends TypedBusiness<EntityProperty> {

	Object evaluate(String entityCode,String propertyCode,Object instance);
	Object evaluate(Entity entity,Property property,Object instance);
	
}
