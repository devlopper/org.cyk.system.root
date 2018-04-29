package org.cyk.system.root.business.api.metadata;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.metadata.Entity;
import org.cyk.system.root.model.metadata.EntityProperty;
import org.cyk.system.root.model.metadata.Property;

public interface EntityPropertyBusiness extends TypedBusiness<EntityProperty> {

	Object evaluate(EntityProperty entityProperty,Object instance,Boolean isInputChange,Boolean isPersist);
	Object evaluate(EntityProperty entityProperty,Object instance);
	
	Object evaluate(Entity entity,Property property,Object instance,Boolean isInputChange,Boolean isPersist);
	Object evaluate(Entity entity,Property property,Object instance);
	
	Object evaluate(String entityCode,String propertyCode,Object instance,Boolean isInputChange,Boolean isPersist);
	Object evaluate(String entityCode,String propertyCode,Object instance);
	
	Object evaluate(Property property,Object instance,Boolean isInputChange,Boolean isPersist);
	Object evaluate(Property property,Object instance);
	
	Object evaluate(String propertyCode,Object instance,Boolean isInputChange,Boolean isPersist);
	Object evaluate(String propertyCode,Object instance);
	
}
