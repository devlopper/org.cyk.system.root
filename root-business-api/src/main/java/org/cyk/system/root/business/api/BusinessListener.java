package org.cyk.system.root.business.api;

import java.util.Collection;
import java.util.Map;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface BusinessListener {

	<T extends AbstractIdentifiable> Collection<T> find(Class<T> dataClass,Integer first, Integer pageSize,String sortField, Boolean ascendingOrder,Map<String, Object> filters);
	
	<T extends AbstractIdentifiable> Long count(Class<T> dataClass,Map<String, Object> filters);
	
	<T extends AbstractIdentifiable> Long count(Class<T> dataClass,String filter);
	
}