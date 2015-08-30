package org.cyk.system.root.model;

public interface IdentifiableLifeCyleEventListener<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> {

	 void onPrePersist(IDENTIFIABLE identifiable);
	 void onPostPersist(IDENTIFIABLE identifiable);
	 void onPostLoad(IDENTIFIABLE identifiable);
	 void onPreUpdate(IDENTIFIABLE identifiable);
	 void onPostUpdate(IDENTIFIABLE identifiable);
	 void onPreRemove(IDENTIFIABLE identifiable);
	 void onPostRemove(IDENTIFIABLE identifiable);
	
}
