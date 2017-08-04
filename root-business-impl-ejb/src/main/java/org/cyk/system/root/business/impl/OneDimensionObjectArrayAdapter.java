package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.accessor.InstanceFieldSetter.OneDimensionObjectArray;

public class OneDimensionObjectArrayAdapter<T> extends OneDimensionObjectArray.Adapter.Default<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public OneDimensionObjectArrayAdapter(Class<T> outputClass) {
		super(outputClass);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getValue(Class<?> fieldType, Object value) {
		if(fieldType.isAnnotationPresent(Entity.class)){
			TypedDao<AbstractIdentifiable> persistence = inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)fieldType);
			if(Boolean.TRUE.equals(isReadByGlobalIdentifierValue(fieldType, value)))
				return persistence.readByGlobalIdentifierValue((String) value);
			if(Boolean.TRUE.equals(isReadByGlobalIdentifierCode(fieldType, value)))
				return persistence.read((String)value);
		}
		return super.getValue(fieldType, value);
	}
	
	protected Boolean isReadByGlobalIdentifierValue(Class<?> fieldType, Object value){
		if(File.class.equals(fieldType))
			return Boolean.TRUE;
		return Boolean.FALSE;
	}
	
	protected Boolean isReadByGlobalIdentifierCode(Class<?> fieldType, Object value){
		return Boolean.TRUE;
	}
	
	/**/
	
	
	
}
