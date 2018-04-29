package org.cyk.system.root.business.impl.metadata;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.file.ScriptBusiness;
import org.cyk.system.root.business.api.metadata.EntityBusiness;
import org.cyk.system.root.business.api.metadata.EntityPropertyBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.metadata.Entity;
import org.cyk.system.root.model.metadata.EntityProperty;
import org.cyk.system.root.model.metadata.Property;
import org.cyk.system.root.persistence.api.metadata.EntityPropertyDao;
import org.cyk.utility.common.helper.FieldHelper;

public class EntityPropertyBusinessImpl extends AbstractTypedBusinessService<EntityProperty,EntityPropertyDao> implements EntityPropertyBusiness,Serializable {
	private static final long serialVersionUID = -5970296090669949506L;

	@Inject
    public EntityPropertyBusinessImpl(EntityPropertyDao dao) {
        super(dao);
    } 
	
	@Override
	public EntityProperty instanciateOne() {
		return super.instanciateOne().setEvaluatableWhenInputChange(Boolean.TRUE);
	}
	
	@Override
	public Object evaluate(EntityProperty entityProperty,Object instance,Boolean isInputChange,Boolean isPersist) {
		Object result;
		if(entityProperty==null || entityProperty.getValueGeneratorScript() == null || instance == null){
			//evaluation is not possible
			result = null;
		}else {
			if((Boolean.TRUE.equals(isInputChange) && Boolean.TRUE.equals(entityProperty.getEvaluatableWhenInputChange())) || 
					(Boolean.TRUE.equals(isPersist) || Boolean.TRUE.equals(entityProperty.getEvaluatableWhenPersist()))){
				//an event has trigger the evaluation. we run the script and set the result value to the field
				entityProperty.getValueGeneratorScript().getInputs().put(RootConstant.Configuration.Script.INSTANCE, instance);
				result = inject(ScriptBusiness.class).evaluate(entityProperty.getValueGeneratorScript());
				FieldHelper.getInstance().set(instance, result, entityProperty.getProperty().getPath());
			}else{
				//no event has trigger the evaluation so the result is the current field value
				result = FieldHelper.getInstance().read(instance, entityProperty.getProperty().getPath());
			}
		}
		return result;
	}
	
	@Override
	public Object evaluate(EntityProperty entityProperty,Object instance) {
		return evaluate(entityProperty,instance,Boolean.TRUE,Boolean.TRUE);
	}
	
	@Override
	public Object evaluate(Entity entity, Property property, Object instance,Boolean isInputChange,Boolean isPersist) {
		return evaluate(dao.readByEntityByProperty(entity, property),instance,isInputChange,isPersist);
	}
	
	@Override
	public Object evaluate(Entity entity, Property property, Object instance) {
		return evaluate(entity,property,instance,Boolean.TRUE,Boolean.TRUE);
	}
	
	@Override
	public Object evaluate(String entityCode, String propertyCode, Object instance,Boolean isInputChange,Boolean isPersist) {
		return evaluate(read(Entity.class, entityCode), read(Property.class, propertyCode), instance,isInputChange,isPersist);
	}
	
	@Override
	public Object evaluate(String entityCode, String propertyCode, Object instance) {
		return evaluate(entityCode, propertyCode, instance,Boolean.TRUE,Boolean.TRUE);
	}

	@Override
	public Object evaluate(Property property, Object instance,Boolean isInputChange,Boolean isPersist) {
		return evaluate(inject(EntityBusiness.class).findByClassName(instance.getClass()), property, instance, isInputChange,isPersist);
	}
	
	@Override
	public Object evaluate(Property property, Object instance) {
		return evaluate(property, instance, Boolean.TRUE,Boolean.TRUE);
	}

	@Override
	public Object evaluate(String propertyCode, Object instance,Boolean isInputChange,Boolean isPersist) {
		return evaluate(inject(EntityBusiness.class).findByClassName(instance.getClass()), read(Property.class, propertyCode), instance, isInputChange,isPersist);
	}
	
	@Override
	public Object evaluate(String propertyCode, Object instance) {
		return evaluate(propertyCode, instance, Boolean.TRUE,Boolean.TRUE);
	}

}
