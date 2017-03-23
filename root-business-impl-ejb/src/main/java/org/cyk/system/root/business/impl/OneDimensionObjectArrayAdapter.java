package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.system.root.model.AbstractIdentifiable;
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
		if(fieldType.isAnnotationPresent(Entity.class))
			return inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)fieldType).read((String)value);
		return super.getValue(fieldType, value);
	}

}
