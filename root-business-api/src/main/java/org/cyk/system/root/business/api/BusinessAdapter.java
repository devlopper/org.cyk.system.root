package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.computation.DataReadConfiguration;

public class BusinessAdapter implements BusinessListener,Serializable {

	private static final long serialVersionUID = -6980159194301961222L;

	@Override
	public <T extends AbstractIdentifiable> Collection<T> find(Class<T> dataClass, DataReadConfiguration configuration) {
		return null;
	}

	@Override
	public <T extends AbstractIdentifiable> Long count(Class<T> dataClass, DataReadConfiguration configuration) {
		return null;
	}

	

}
