package org.cyk.system.root.business.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface GenericBusiness extends AbstractGenericBusinessService<AbstractIdentifiable, Long> {
	
    void flushEntityManager();
    
    void create(Collection<AbstractIdentifiable> identifiables,Boolean useThreadPoolExecutor);
    void createIdentifiables(Collection<? extends AbstractIdentifiable> collection,Boolean useThreadPoolExecutor);
    
    AbstractIdentifiable find(String identifiableClassIdentifier,String code);
}
