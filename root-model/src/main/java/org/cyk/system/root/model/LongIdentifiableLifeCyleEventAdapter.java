package org.cyk.system.root.model;

import java.io.Serializable;

public class LongIdentifiableLifeCyleEventAdapter implements AbstractIdentifiableLifeCyleEventListener,Serializable {

	private static final long serialVersionUID = -9218100451112849784L;

	@Override
	public void onPrePersist(Identifiable<Long> identifiable) {}

	@Override
	public void onPostPersist(Identifiable<Long> identifiable) {}

	@Override
	public void onPostLoad(Identifiable<Long> identifiable) {}

	@Override
	public void onPreUpdate(Identifiable<Long> identifiable) {}

	@Override
	public void onPostUpdate(Identifiable<Long> identifiable) {}

	@Override
	public void onPreRemove(Identifiable<Long> identifiable) {}

	@Override
	public void onPostRemove(Identifiable<Long> identifiable) {}

}
