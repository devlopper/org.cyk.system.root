package org.cyk.system.root.model;

import java.io.Serializable;

public class IdentifiableLifeCyleEventAdapter<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> implements IdentifiableLifeCyleEventListener<IDENTIFIABLE,IDENTIFIER>,Serializable {

	private static final long serialVersionUID = -9218100451112849784L;

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
