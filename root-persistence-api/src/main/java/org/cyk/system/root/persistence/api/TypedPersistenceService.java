package org.cyk.system.root.persistence.api;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.Identifiable;

public interface TypedPersistenceService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends PersistenceService<IDENTIFIABLE,IDENTIFIER> {
		
	/* predefined query  */
    
    Collection<IDENTIFIABLE> readAll(); 
    //Long countAll();
    IDENTIFIABLE readDefaulted(); 
    
    Collection<IDENTIFIABLE> readByClasses(Collection<Class<?>> classes);
    Long countByClasses(Collection<Class<?>> classes);
    
    Collection<IDENTIFIABLE> readByNotClasses(Collection<Class<?>> classes);
    Long countByNotClasses(Collection<Class<?>> classes);
    
    Collection<IDENTIFIABLE> readByGlobalIdentifierSupportingDocumentCode(String supportingDocumentCode);
  	Long countByGlobalIdentifierSupportingDocumentCode(String supportingDocumentCode);
    
    /**/
    
    <T extends IDENTIFIABLE> Collection<T> readByClass(Class<T> aClass);
    Long countByClass(Class<?> aClass);
    
    Collection<IDENTIFIABLE> readByNotClass(Class<?> aClass);
    Long countByNotClass(Class<?> aClass);
    
    void executeDelete(Collection<IDENTIFIABLE> identifiables);
    
    /**/
    //TODO to be removed
    Collection<IDENTIFIABLE> readDuplicates();
  //TODO to be removed
    Long countDuplicates();
  //TODO to be removed
    Collection<IDENTIFIABLE> readDuplicates(IDENTIFIABLE identifiable);

    Collection<IDENTIFIABLE> readWhereExistencePeriodCross(Date from,Date to);
	Long countWhereExistencePeriodCross(Date from,Date to);
	
}
