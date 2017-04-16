package org.cyk.system.root.persistence.api;

import java.util.Collection;

import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;

public interface TypedPersistenceService<IDENTIFIABLE extends Identifiable<IDENTIFIER>,IDENTIFIER> extends PersistenceService<IDENTIFIABLE,IDENTIFIER> {
		
	/* predefined query  */
    
    Collection<IDENTIFIABLE> readAll(); 
    Long countAll();
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
    
    <SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Collection<IDENTIFIABLE> readBySearchCriteria(SEARCH_CRITERIA searchCriteria);
    
	<SEARCH_CRITERIA extends AbstractFieldValueSearchCriteriaSet> Long countBySearchCriteria(SEARCH_CRITERIA searchCriteria);
    
    Collection<IDENTIFIABLE> readDuplicates();
    Long countDuplicates();
    
    Collection<IDENTIFIABLE> readDuplicates(IDENTIFIABLE identifiable);

}
