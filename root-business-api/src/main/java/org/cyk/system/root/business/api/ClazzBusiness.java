package org.cyk.system.root.business.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.system.root.model.Clazz;

public interface ClazzBusiness {

	Collection<ClazzBusinessListener> LISTENERS = new ArrayList<>();
	
	/**/
	Clazz register(Clazz aClazz);
	Clazz register(Class<?> aClass);
	
	Clazz find(Class<?> aClass,Boolean registerWhenNull);
	Clazz find(Class<?> aClass);
	Clazz find(String identifier);
	
	Object findParentOf(Class<?> rootClass,Object object);
	List<Object> findParentsOf(Class<?> rootClass,Object object);
	List<Object> findPathOf(Class<?> rootClass,Object object);
	
	void resetUiLabels();
	
	/**/
	
    public static interface ClazzBusinessListener{
    	void register(Clazz clazz);
    	void doSetUiLabel(Clazz clazz);
    	Object getParentOf(Object object);
    	/**/
    	
    	public static class Adapter implements ClazzBusinessListener,Serializable {
    		private static final long serialVersionUID = 4649513322270146977L;
    		@Override public void register(Clazz clazz) {}
    		@Override public void doSetUiLabel(Clazz clazz) {}
    		@Override
    		public Object getParentOf(Object object) {
    			return null;
    		}
        }
    }
    
    
   
}
