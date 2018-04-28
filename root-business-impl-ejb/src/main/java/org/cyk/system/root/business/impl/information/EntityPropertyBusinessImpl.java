package org.cyk.system.root.business.impl.information;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.api.information.EntityPropertyBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.information.Entity;
import org.cyk.system.root.model.information.EntityProperty;
import org.cyk.system.root.model.information.Property;
import org.cyk.system.root.persistence.api.information.EntityPropertyDao;

public class EntityPropertyBusinessImpl extends AbstractTypedBusinessService<EntityProperty,EntityPropertyDao> implements EntityPropertyBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public EntityPropertyBusinessImpl(EntityPropertyDao dao) {
        super(dao);
    } 

	@Override
	public Object evaluate(String entityCode, String propertyCode, Object instance) {
		return evaluate(read(Entity.class, entityCode), read(Property.class, propertyCode), instance);
	}

	@Override
	public Object evaluate(Entity entity, Property property, Object instance) {
		EntityProperty entityProperty = dao.readByEntityByProperty(entity, property);
		if(entityProperty==null || entityProperty.getValueGeneratorScript() == null)
			return null;
		entityProperty.getValueGeneratorScript().getInputs().put("instance", instance);
		return inject(ScriptBusiness.class).evaluate(entityProperty.getValueGeneratorScript());
	}
}
