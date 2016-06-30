package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public interface IdentifiableLifeCyleEventListener<IDENTIFIABLE extends Identifiable<IDENTIFIER>, IDENTIFIER> {

	Map<Class<? extends org.cyk.system.root.model.Identifiable<?>>, org.cyk.system.root.model.Identifiable<?>> MAP = new HashMap<>();
	
	void onPrePersist(IDENTIFIABLE identifiable);

	void onPostPersist(IDENTIFIABLE identifiable);

	void onPostLoad(IDENTIFIABLE identifiable);

	void onPreUpdate(IDENTIFIABLE identifiable);

	void onPostUpdate(IDENTIFIABLE identifiable);

	void onPreRemove(IDENTIFIABLE identifiable);

	void onPostRemove(IDENTIFIABLE identifiable);

	/**/

	public interface AbstractIdentifiable<IDENTIFIABLE extends org.cyk.system.root.model.AbstractIdentifiable> extends IdentifiableLifeCyleEventListener<IDENTIFIABLE, Long> {

		@SuppressWarnings("rawtypes")
		Map<Class<? extends org.cyk.system.root.model.AbstractIdentifiable>, AbstractIdentifiable> MAP = new HashMap<>();

		public static class Adapter<IDENTIFIABLE extends org.cyk.system.root.model.AbstractIdentifiable> implements AbstractIdentifiable<IDENTIFIABLE>,Serializable {
			private static final long serialVersionUID = 4568506042563471330L;

			@Override
			public void onPrePersist(IDENTIFIABLE identifiable) {}

			@Override
			public void onPostPersist(IDENTIFIABLE identifiable) {}

			@Override
			public void onPostLoad(IDENTIFIABLE identifiable) {}

			@Override
			public void onPreUpdate(IDENTIFIABLE identifiable) {}

			@Override
			public void onPostUpdate(IDENTIFIABLE identifiable) {}

			@Override
			public void onPreRemove(IDENTIFIABLE identifiable) {}

			@Override
			public void onPostRemove(IDENTIFIABLE identifiable) {}
			
		}
	}

}
