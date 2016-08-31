package org.cyk.system.root.business.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.utility.common.computation.DataReadConfiguration;

public interface TypedBusiness<IDENTIFIABLE extends AbstractIdentifiable> extends IdentifiableBusinessService<IDENTIFIABLE, Long> {

	/* predefined query  */
	
    IDENTIFIABLE load(Long identifier);
    
    void load(IDENTIFIABLE identifiable);
    void load(Collection<IDENTIFIABLE> identifiables);
    
    IDENTIFIABLE instanciateOneRandomly();
    Collection<IDENTIFIABLE> instanciateManyRandomly(Integer count);
    
    Collection<IDENTIFIABLE> findAll(); 
    Long countAll();
    
    Collection<IDENTIFIABLE> findAll(DataReadConfiguration configuration); 
    Long countAll(DataReadConfiguration configuration);
    
    Collection<IDENTIFIABLE> findAllExclude(Collection<IDENTIFIABLE> identifiables); 
    
    Long countAllExclude(Collection<IDENTIFIABLE> identifiables); 
    
    Collection<IDENTIFIABLE> findByGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers);
	Long countByGlobalIdentifiers(Collection<GlobalIdentifier> globalIdentifiers);
    
	IDENTIFIABLE findByGlobalIdentifierValue(String globalIdentifier);
	
    Collection<IDENTIFIABLE> findByClasses(Collection<Class<?>> classes);
    Long countByClasses(Collection<Class<?>> classes);
    
    Collection<IDENTIFIABLE> findByNotClasses(Collection<Class<?>> classes);
    Long countByNotClasses(Collection<Class<?>> classes);
    
    /**/
    
    <T extends IDENTIFIABLE> Collection<T> findByClass(Class<T> aClass);
    Long countByClass(Class<?> aClass);
    
    Collection<IDENTIFIABLE> findByNotClass(Class<?> aClass);
    Long countByNotClass(Class<?> aClass);
    
    /**
	 * {@link #findByGlobalIdentifierCode(String)}
	 * 
	 */
	IDENTIFIABLE find(String globalIdentifierCode);
	
	/**
	 * {@link #findByGlobalIdentifierCodes(Collection<String>)}
	 * 
	 */
	Collection<IDENTIFIABLE> find(Collection<String> globalIdentifierCodes);
    
	File createFile(IDENTIFIABLE identifiable,String fileRepresentationTypeCode);
	
    //TODO clone service must be implemented using reflection and listener
}
