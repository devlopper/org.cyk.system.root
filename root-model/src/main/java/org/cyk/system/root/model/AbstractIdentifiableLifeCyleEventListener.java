package org.cyk.system.root.model;

import java.util.HashMap;
import java.util.Map;

public interface AbstractIdentifiableLifeCyleEventListener extends LifeCyleEventListener<Long> {

	Map<Class<? extends AbstractIdentifiable>, AbstractIdentifiableLifeCyleEventListener> MAP = new HashMap<>();
	
}
