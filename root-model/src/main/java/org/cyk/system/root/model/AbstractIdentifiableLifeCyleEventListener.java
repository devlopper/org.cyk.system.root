package org.cyk.system.root.model;

import java.util.HashMap;
import java.util.Map;

public interface AbstractIdentifiableLifeCyleEventListener extends IdentifiableLifeCyleEventListener<AbstractIdentifiable,Long> {

	Map<Class<? extends AbstractIdentifiable>, AbstractIdentifiableLifeCyleEventListener> MAP = new HashMap<>();
	
}
