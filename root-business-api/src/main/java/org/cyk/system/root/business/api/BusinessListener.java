package org.cyk.system.root.business.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.computation.DataReadConfiguration;

public interface BusinessListener {

	<T extends AbstractIdentifiable> Collection<T> find(Class<T> dataClass,DataReadConfiguration configuration);
	
	<T extends AbstractIdentifiable> Long count(Class<T> dataClass, DataReadConfiguration configuration);
	
}