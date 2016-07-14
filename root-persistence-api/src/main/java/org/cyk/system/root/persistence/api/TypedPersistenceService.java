package org.cyk.system.root.persistence.api;

import java.util.Collection;

import org.cyk.system.root.model.Identifiable;

public interface TypedPersistenceService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends PersistenceService<IDENTIFIABLE,IDENTIFIER> {
		
	/* predefined query  */
    
    Collection<IDENTIFIABLE> readAll(); 
    Long countAll();
    
    Collection<IDENTIFIABLE> readByClasses(Collection<Class<?>> classes);
    Long countByClasses(Collection<Class<?>> classes);
    
    Collection<IDENTIFIABLE> readByNotClasses(Collection<Class<?>> classes);
    Long countByNotClasses(Collection<Class<?>> classes);
    
    /**/
    
    <T extends IDENTIFIABLE> Collection<T> readByClass(Class<T> aClass);
    Long countByClass(Class<?> aClass);
    
    Collection<IDENTIFIABLE> readByNotClass(Class<?> aClass);
    Long countByNotClass(Class<?> aClass);
    
    void delete(Collection<IDENTIFIABLE> identifiables);
}
