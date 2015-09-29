package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.model.Clazz;

public interface ClazzBusiness {

	Collection<ClazzBusinessListener> LISTENERS = new ArrayList<>();
	
	/**/
	Clazz register(Clazz aClazz);
	Clazz register(Class<?> aClass);
	
	Clazz find(Class<?> aClass,Boolean registerWhenNull);
	Clazz find(Class<?> aClass);
	Clazz find(String identifier);
	
	void resetUiLabels();
	
	/**/
	
    public static interface ClazzBusinessListener{
    	void register(Clazz clazz);
    	void doSetUiLabel(Clazz clazz);
    }
    
    public static class ClazzBusinessAdapter implements ClazzBusinessListener,Serializable {
		private static final long serialVersionUID = 4649513322270146977L;
		@Override public void register(Clazz clazz) {}
		@Override public void doSetUiLabel(Clazz clazz) {}
    }
   
}
