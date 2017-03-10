package org.cyk.system.root.business.api;

import java.util.Collection;
import java.util.Locale;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface GenericBusiness extends AbstractGenericBusinessService<AbstractIdentifiable, Long> {
	
    void flushEntityManager();
    
    void create(Collection<AbstractIdentifiable> identifiables,Boolean useThreadPoolExecutor);
    void createIdentifiables(Collection<? extends AbstractIdentifiable> collection,Boolean useThreadPoolExecutor);
    
    AbstractIdentifiable find(String identifiableClassIdentifier,String code);

    <IDENTIFIABLE extends AbstractIdentifiable> void createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode, Locale locale);
}
