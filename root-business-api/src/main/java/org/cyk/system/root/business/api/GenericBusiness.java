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
    
    void createIfNotIdentified(Collection<? extends AbstractIdentifiable> collection);
    void createIfNotIdentified(AbstractIdentifiable...identifiables);
    void create(Object object,Collection<String> fieldNames);
    void create(Object object,String...fieldNames);
    
    void save(Object object,Collection<String> fieldNames);
    void save(Object object,String...fieldNames);
    
    void updateIfIdentifiedElseCreate(Collection<? extends AbstractIdentifiable> collection);
    void updateIfIdentifiedElseCreate(AbstractIdentifiable...identifiables);
    
    void deleteIfIdentified(Collection<? extends AbstractIdentifiable> collection);
    void deleteIfIdentified(AbstractIdentifiable...identifiables);
    
    void delete(Object object,Collection<String> fieldNames);
    void delete(Object object,String...fieldNames);
    
    AbstractIdentifiable find(String identifiableClassIdentifier,String code);

    <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode, Locale locale,Map<String, Boolean> fieldSortingMap);
    <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode, Locale locale);
    <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode,Map<String, Boolean> fieldSortingMap);
    <IDENTIFIABLE extends AbstractIdentifiable> File createReportFile(IDENTIFIABLE identifiable, String reportTemplateCode);
    <IDENTIFIABLE extends AbstractIdentifiable> Collection<File> createReportFiles(Collection<IDENTIFIABLE> identifiables, String reportTemplateCode,Map<String, Boolean> fieldSortingMap);
    <IDENTIFIABLE extends AbstractIdentifiable> Collection<File> createReportFiles(Collection<IDENTIFIABLE> identifiables, String reportTemplateCode);
    
    <T extends AbstractIdentifiable> void deleteByCodes(Class<T> aClass,Collection<String> codes);
    <T extends AbstractIdentifiable> void deleteByCode(Class<T> aClass,String code);
    
    <T extends AbstractIdentifiable> Collection<T> findWhereExistencePeriodFromDateIsLessThan(Class<T> aClass,String code);
    <T extends AbstractIdentifiable> Long countWhereExistencePeriodFromDateIsLessThan(Class<T> aClass,String code);
    
    <T extends AbstractIdentifiable> T findFirstWhereExistencePeriodFromDateIsLessThan(Class<T> aClass,String code);
    
    <T extends AbstractIdentifiable> void setFieldValuesRandomly(T identifiable,Collection<String> fieldNames);
    <T extends AbstractIdentifiable> void setFieldValuesRandomly(T identifiable,String...fieldNames);
    
    
}
