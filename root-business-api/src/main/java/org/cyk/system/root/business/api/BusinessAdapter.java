package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.cyk.system.root.model.AbstractIdentifiable;

public class BusinessAdapter implements BusinessListener,Serializable {

	private static final long serialVersionUID = -6980159194301961222L;

	@Override
	public <T extends AbstractIdentifiable> Collection<T> find(
			Class<T> dataClass, Integer first, Integer pageSize,
			String sortField, Boolean ascendingOrder,
			Map<String, Object> filters) {
		return null;
	}

	@Override
	public <T extends AbstractIdentifiable> Long count(Class<T> dataClass,
			Map<String, Object> filters) {
		return null;
	}

	@Override
	public <T extends AbstractIdentifiable> Long count(Class<T> dataClass,
			String filter) {
		return null;
	}

	@Override
	public <T extends AbstractIdentifiable> Collection<T> find(
			Class<T> dataClass, Integer first, Integer pageSize,
			String sortField, Boolean ascendingOrder, String filter) {
		return null;
	}

}
