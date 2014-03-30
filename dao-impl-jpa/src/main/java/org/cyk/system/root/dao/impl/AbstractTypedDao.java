package org.cyk.system.root.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.cyk.system.root.dao.api.TypedIdentifiableQuery;
import org.cyk.system.root.model.AbstractIdentifiable;

public abstract class AbstractTypedDao<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractQueryable<IDENTIFIABLE> implements TypedIdentifiableQuery<IDENTIFIABLE>,Serializable {

	private static final long serialVersionUID = -2964204372097468908L;

	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		clazz = (Class<IDENTIFIABLE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		super.initialisation();
	}
	
	@Override 
	public IDENTIFIABLE create(IDENTIFIABLE object) {
		entityManager.persist(object);
		return object;
	}

	@Override
	public IDENTIFIABLE read(Long identifier) {
		return entityManager.find(clazz, identifier);
	}

	@Override
	public IDENTIFIABLE update(IDENTIFIABLE object) {
		return entityManager.merge(object);
	}

	@Override
	public IDENTIFIABLE delete(IDENTIFIABLE object) {
		entityManager.remove(entityManager.merge(object));
		return object;
	}
	
	/**/
	
	
	
}
