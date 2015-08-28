package org.cyk.system.root.model;

public interface LifeCyleEventListener<IDENTIFIER> {

	 void onPrePersist(Identifiable<IDENTIFIER> identifiable);
	 void onPostPersist(Identifiable<IDENTIFIER> identifiable);
	 void onPostLoad(Identifiable<IDENTIFIER> identifiable);
	 void onPreUpdate(Identifiable<IDENTIFIER> identifiable);
	 void onPostUpdate(Identifiable<IDENTIFIER> identifiable);
	 void onPreRemove(Identifiable<IDENTIFIER> identifiable);
	 void onPostRemove(Identifiable<IDENTIFIER> identifiable);
	
}
