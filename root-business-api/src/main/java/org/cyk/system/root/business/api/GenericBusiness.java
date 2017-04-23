package org.cyk.system.root.business.api;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;

public interface GenericBusiness extends AbstractGenericBusinessService<AbstractIdentifiable, Long> {
	
    void flushEntityManager();
    
    void create(Collection<AbstractIdentifiable> identifiables,Boolean useThreadPoolExecutor);
    void createIdentifiables(Collection<? extends AbstractIdentifiable> collection,Boolean useThreadPoolExecutor);
    
    AbstractIdentifiable find(String identifiableClassIdentifier,String code);

    <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode, Locale locale,Map<String, Boolean> fieldSortingMap);
    <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode, Locale locale);
    <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode,Map<String, Boolean> fieldSortingMap);
    <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode);
    <IDENTIFIABLE extends AbstractIdentifiable> Collection<File> createReportFiles(Collection<IDENTIFIABLE> identifiables, String reportTemplateCode,Map<String, Boolean> fieldSortingMap);
    <IDENTIFIABLE extends AbstractIdentifiable> Collection<File> createReportFiles(Collection<IDENTIFIABLE> identifiables, String reportTemplateCode);
}
