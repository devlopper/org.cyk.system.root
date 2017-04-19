package org.cyk.system.root.business.api;

import java.util.Collection;
import java.util.Locale;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;

public interface GenericBusiness extends AbstractGenericBusinessService<AbstractIdentifiable, Long> {
	
    void flushEntityManager();
    
    void create(Collection<AbstractIdentifiable> identifiables,Boolean useThreadPoolExecutor);
    void createIdentifiables(Collection<? extends AbstractIdentifiable> collection,Boolean useThreadPoolExecutor);
    
    AbstractIdentifiable find(String identifiableClassIdentifier,String code);

    <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode, Locale locale);
    <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode);
    <IDENTIFIABLE extends AbstractIdentifiable> Collection<File> createReportFiles(Collection<IDENTIFIABLE> identifiables, String reportTemplateCode);
    
    <T extends AbstractIdentifiable> void deleteByCodes(Class<T> aClass,Collection<String> codes);
    <T extends AbstractIdentifiable> void deleteByCode(Class<T> aClass,String code);
}
