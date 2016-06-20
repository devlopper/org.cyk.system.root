package org.cyk.system.root.model;

import java.util.HashMap;
import java.util.Map;

public interface IdentifiableLifeCyleEventListener<IDENTIFIABLE extends Identifiable<IDENTIFIER>, IDENTIFIER> {

	void onPrePersist(IDENTIFIABLE identifiable);

	void onPostPersist(IDENTIFIABLE identifiable);

	void onPostLoad(IDENTIFIABLE identifiable);

	void onPreUpdate(IDENTIFIABLE identifiable);

	void onPostUpdate(IDENTIFIABLE identifiable);

	void onPreRemove(IDENTIFIABLE identifiable);

	void onPostRemove(IDENTIFIABLE identifiable);

	/**/

	public interface AbstractIdentifiable extends IdentifiableLifeCyleEventListener<org.cyk.system.root.model.AbstractIdentifiable, Long> {

		Map<Class<? extends org.cyk.system.root.model.AbstractIdentifiable>, AbstractIdentifiable> MAP = new HashMap<>();

	}

}
