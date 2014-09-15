package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.model.AbstractIdentifiable;

public abstract class AbstractTypedDao<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractPersistenceService<IDENTIFIABLE> implements TypedDao<IDENTIFIABLE>,Serializable {

	private static final long serialVersionUID = -2964204372097468908L;

	protected String readAll,countAll;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		clazz = (Class<IDENTIFIABLE>) parameterizedClass();
		//(Class<IDENTIFIABLE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		super.initialisation();
	}
	
	protected Class<?> parameterizedClass(){
	    return (Class<?>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readAll, _select()+(StringUtils.isEmpty(readAllOrderByString())?"":" "+readAllOrderByString()));
	}
	
	protected String readAllOrderByString(){
		return null;
	}
	
	/**/
	
	@Override
	public Collection<IDENTIFIABLE> readAll() {
		return namedQuery(readAll).resultMany();
	}
	
	@Override
	public Long countAll() {
		return namedQuery(countAll,Long.class).resultOne();
	}
	
}
