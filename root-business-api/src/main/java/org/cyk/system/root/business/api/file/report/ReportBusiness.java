package org.cyk.system.root.business.api.file.report;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.utility.common.Constant;

public interface ReportBusiness {
    
	<MODEL,REPORT extends AbstractReport<?>> void registerConfiguration(AbstractReportConfiguration<MODEL,REPORT> builder);
	<MODEL,REPORT extends AbstractReport<?>> AbstractReportConfiguration<MODEL,REPORT> findConfiguration(String identifier);
	
	<MODEL> ReportBasedOnDynamicBuilder<MODEL> build(ReportBasedOnDynamicBuilderParameters<MODEL> parameters);
	void build(ReportBasedOnTemplateFile<?> aReport,Boolean print);
	
	void write(java.io.File directory,AbstractReport<?> aReport);
	
	Collection<Column> findColumns(Class<?> aClass,ReportBasedOnDynamicBuilderParameters<?> parameters);
	
	/**
     * build a valid system file name
     * @param report
     * @return
     */
    //String buildFileName(AbstractReport<?> report);//merge with prepare
    
    /**
     * Set if not not set informations like owner name , creation date , created by , file name
     * @param report
     */
    void prepare(AbstractReport<?> report);
    
    /**
     * Build the report binary content based on the provided informations
     */
    <T> ReportBasedOnTemplateFile<T> buildBinaryContent(T reportModel,File template,String fileExtension);
    
    <T> ReportBasedOnTemplateFile<T> buildBinaryContent(Object object,String reportFieldName,T reportModel,File template,Boolean persist,String fileExtension);
    
    <T> ReportBasedOnTemplateFile<T> buildBinaryContent(Object object,T reportModel,File template,Boolean persist,String fileExtension);
    
    <T> ReportBasedOnTemplateFile<T> buildBinaryContent(Object object,T reportModel,File template,Boolean persist);
    
    <T> ReportBasedOnTemplateFile<T> buildBinaryContent(File file,String title,String extension);
    
    <T> ReportBasedOnTemplateFile<T> buildBinaryContent(File file,String title);
    
    /**
     * Persist the report to the specific file
     * @param file
     * @param report
     */
    void persist(File file,AbstractReport<?> report);
    
    /**/
    @Getter @Setter
    public static class BuildBinaryContentArguments implements Serializable{
		private static final long serialVersionUID = 6051053785963033876L;
    	private Object container,model;
    	private File template;
    	private String fieldNameInContainer,fileExtension,title;
    	private Boolean persist = Boolean.FALSE;
    }
    
    String DEFAULT_FILE_NAME = "report";
    String DEFAULT_FILE_EXTENSION = "pdf";
    String DEFAULT_FILE_NAME_AND_EXTENSION = DEFAULT_FILE_NAME+Constant.CHARACTER_DOT+DEFAULT_FILE_EXTENSION;
}
