package org.cyk.system.root.business.impl.file.report;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessServiceImpl;
import org.cyk.system.root.business.impl.AbstractFieldSorter.FieldSorter;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.ReportColumn;

public abstract class AbstractReportBusinessImpl extends AbstractBusinessServiceImpl implements ReportBusiness , Serializable {

	private static final long serialVersionUID = -3596293198479369047L;

	public static final Set<AbstractReportConfiguration<Object, AbstractReport<?>>> REPORTS = new HashSet<>();
	public static Boolean SHOW_OWNER_NAME = Boolean.TRUE;
	public static Boolean SHOW_OWNER_LOGO = Boolean.TRUE;
	
	public static final String FILE_NAME_FORMAT = "%s - %s - %s - %s";
	public static final String DEFAULT_FILE_EXTENSION = "pdf";
	public static final String DEFAULT_REPORT_FIELD_NAME = "report";
	
	@Inject protected LanguageBusiness languageBusiness;
	
	@SuppressWarnings("unchecked")
	@Override
	public <MODEL, REPORT extends AbstractReport<?>> void registerConfiguration(AbstractReportConfiguration<MODEL, REPORT> configuration) {
		REPORTS.add((AbstractReportConfiguration<Object, AbstractReport<?>>) configuration);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <MODEL, REPORT extends AbstractReport<?>> AbstractReportConfiguration<MODEL, REPORT> findConfiguration(String identifier) {
		for(AbstractReportConfiguration<?,?> r : REPORTS)
			if(r.getIdentifier().equals(identifier))
				return (AbstractReportConfiguration<MODEL, REPORT>) r;
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <MODEL> ReportBasedOnDynamicBuilder<MODEL> build(ReportBasedOnDynamicBuilderParameters<MODEL> parameters) {
		//BusinessEntityInfos businessEntityInfos = RootBusinessLayer.getInstance().getApplicationBusiness().findBusinessEntityInfos((Class<AbstractIdentifiable>) parameters.getClazz());
		ReportBasedOnDynamicBuilder<MODEL> report = null;
		if(parameters.getReport()==null){
			parameters.setReport(report = new ReportBasedOnDynamicBuilder<MODEL>());
			
			report.setColumns(findColumns(parameters.getModelClass()==null?parameters.getIdentifiableClass():parameters.getModelClass(),parameters));
			if(parameters.getDatas()==null)
				;
			else
				report.getDataSource().addAll(parameters.getDatas()); 
		}else{
			report = (ReportBasedOnDynamicBuilder<MODEL>) parameters.getReport();
		}
		
		__build__(parameters);
		
		return report;
	}
	
	@Override
	public void write(java.io.File directory,AbstractReport<?> aReport) {
		try {
			FileUtils.writeByteArrayToFile(new java.io.File(directory,aReport.getFileName()+"."+aReport.getFileExtension()), aReport.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**/
	
	@Override
	public Collection<Column> findColumns(Class<?> aClass,ReportBasedOnDynamicBuilderParameters<?> parameters) {
		Collection<Column> columns = new ArrayList<>();
		Collection<Class<? extends Annotation>> filters = new ArrayList<>();
    	filters.add(Input.class);
    	filters.add(ReportColumn.class);
    	Collection<Field> candidateFields = CommonUtils.getInstance().getAllFields(aClass, filters);
    	List<Field> fields = new ArrayList<>();
    	for(Field field : candidateFields){
    		Boolean dontIgnore = Boolean.TRUE;
    		for(ReportBasedOnDynamicBuilderListener listener : parameters.getReportBasedOnDynamicBuilderListeners()){
    			Boolean value = listener.ignoreField(field);
    			if(value==null){
    				//Set<String> fieldToIgnoreSet = listener.fieldToIgnore();
    				//if(fieldToIgnoreSet!=null)
    				//	ignore = fieldToIgnoreSet.contains(field.getName());
    				value = Boolean.FALSE;
    			}
    			dontIgnore = Boolean.FALSE.equals(value) && dontIgnore;
    		}
    		if(Boolean.TRUE.equals(dontIgnore) && !parameters.getColumnNamesToExclude().contains(field.getName()))	
    			fields.add(field);
    	}
    	
    	new FieldSorter(fields,aClass).sort();
    	
    	for(Field field : fields){
    		columns.add(new Column(field,languageBusiness.findFieldLabelText(field)) );
    	}
		return columns;
	}
			
	/**/
	
	protected <MODEL> void notifyReportStartBuilding(ReportBasedOnDynamicBuilderParameters<MODEL> parameters){
		for(ReportBasedOnDynamicBuilderListener listener : ReportBasedOnDynamicBuilderListener.GLOBALS)
			listener.report(parameters.getReport(),parameters);
		
		for(ReportBasedOnDynamicBuilderListener listener : parameters.getReportBasedOnDynamicBuilderListeners())
			listener.report(parameters.getReport(),parameters);
	}
	
	protected <MODEL> void notifyColumnStartBuilding(ReportBasedOnDynamicBuilderParameters<MODEL> parameters,Column column){
		for(ReportBasedOnDynamicBuilderListener listener : ReportBasedOnDynamicBuilderListener.GLOBALS)
			listener.column(parameters.getReport(),column);
		
		for(ReportBasedOnDynamicBuilderListener listener : parameters.getReportBasedOnDynamicBuilderListeners())
			listener.column(parameters.getReport(),column);
	}
	
	protected abstract <MODEL> void __build__(ReportBasedOnDynamicBuilderParameters<MODEL> parameters);//{
		/*__buildBeforeColumns__(parameters);
		__buildColumns__(parameters);
		__buildAfterColumns__(parameters);*/
	//}
	/*
	protected <MODEL> void __buildBeforeColumns__(ReportBasedOnDynamicBuilderParameters<MODEL> parameters){
		for(ReportBasedOnDynamicBuilderListener listener : parameters.getReportBasedOnDynamicBuilderListeners())
			listener.report(parameters.getReport());
	}
	protected <MODEL> void __buildColumns__(ReportBasedOnDynamicBuilderParameters<MODEL> parameters){
		for(Column column : parameters.getReport().getColumns()){
			__buildBeforeColumn__(parameters,column);
			__buildColumn__(parameters,column);
			__buildAfterColumn__(parameters,column);
		}
	}
	protected <MODEL> void __buildAfterColumns__(ReportBasedOnDynamicBuilderParameters<MODEL> parameters){}
	
	
	
	protected <MODEL> void __buildBeforeColumn__(ReportBasedOnDynamicBuilderParameters<MODEL> parameters,Column column){
		for(ReportBasedOnDynamicBuilderListener listener : parameters.getReportBasedOnDynamicBuilderListeners())
			listener.column(parameters.getReport(), column);
	}
	protected abstract <MODEL> void __buildColumn__(ReportBasedOnDynamicBuilderParameters<MODEL> parameters,Column column);
	protected <MODEL> void __buildAfterColumn__(ReportBasedOnDynamicBuilderParameters<MODEL> parameters,Column column){}
	*/

	/**/
	
    /**
     * build a valid system file name
     * @param report
     * @return
     */
    private  String buildFileName(AbstractReport<?> report){
    	StringBuilder s = new StringBuilder(String.format(FILE_NAME_FORMAT,StringUtils.isNotBlank(report.getOwnerName())?report.getOwnerName()
    			:RootBusinessLayer.getInstance().getApplication().getName(),
				report.getTitle(),StringUtils.replace(report.getCreationDate(),Constant.CHARACTER_COLON.toString(),Constant.CHARACTER_H.toString()),report.getCreatedBy()));
    	s = new StringBuilder(StringUtils.remove(s.toString(), Constant.CHARACTER_SLASH.charValue()));
    	s = new StringBuilder(StringUtils.remove(s.toString(), Constant.CHARACTER_BACK_SLASH.charValue()));
    	s = new StringBuilder(StringUtils.remove(s.toString(), Constant.CHARACTER_COLON.charValue()));
		return s.toString();
    }
    
    /**
     * Set if not not set informations like owner name , creation date , created by , file name
     * @param report
     */
    public void prepare(AbstractReport<?> report){
    	//logTrace("Prepare report {}", report);
    	if(StringUtils.isBlank(report.getOwnerName()))
    		report.setOwnerName(RootBusinessLayer.getInstance().getApplicationBusiness().findCurrentInstance().getName());
    	
    	if(StringUtils.isBlank(report.getCreationDate()))
			report.setCreationDate(timeBusiness.formatDateTime(new Date()));
		
		if(StringUtils.isBlank(report.getCreatedBy()))
			report.setCreatedBy("ANONYMOUS");
		
		if(StringUtils.isBlank(report.getFileExtension()))
			report.setFileExtension(DEFAULT_FILE_EXTENSION);
		
		if(StringUtils.isBlank(report.getTitle()))
			report.setTitle("UNKNOWN_TITLE");
		
		report.setFileName(buildFileName(report));
		logTrace("Report prepared : {}", report);
    }
    
    /**
     * Build the report binary content based on the provided informations
     */
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(T reportModel,File template,String fileExtension){
		//logTrace("Create report binary content. existing file?{} , model?{} , template={} , content type={}",file!=null,reportModel!=null,template,fileExtension);
    	ReportBasedOnTemplateFile<T> reportBasedOnTemplateFile = new ReportBasedOnTemplateFile<T>();
    	//reportBasedOnTemplateFile.setTitle(title);
    	reportBasedOnTemplateFile.setFileExtension(StringUtils.isBlank(fileExtension)?DEFAULT_FILE_EXTENSION:fileExtension);
		prepare(reportBasedOnTemplateFile);
		
		reportBasedOnTemplateFile.getDataSource().add(reportModel);
		reportBasedOnTemplateFile.setTemplateFile(template);
		build(reportBasedOnTemplateFile, Boolean.FALSE);
		logTrace("Report binary content built using model {} and based on template file {}. size in bytes={}",reportModel.getClass().getSimpleName(),template,
				reportBasedOnTemplateFile.getBytes()==null?0l:reportBasedOnTemplateFile.getBytes().length);
		
		return reportBasedOnTemplateFile;
	}
    
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(Object object,String reportFieldName,T reportModel,File template,Boolean persist,String fileExtension){
    	ReportBasedOnTemplateFile<T> reportBasedOnTemplateFile = buildBinaryContent(reportModel, template, fileExtension);
		if(Boolean.TRUE.equals(persist)){
			if(reportFieldName==null)
	    		reportFieldName = "report";
	    	Field reportField = commonUtils.getFieldFromClass(object.getClass(), reportFieldName); 
			File file = (File) commonUtils.readField(object, reportField, Boolean.TRUE,Boolean.TRUE);
			//logTrace("Report field {} has been read : {}", reportField,file);
			logTrace("Persist report binary content. field = {}.",reportField);
			persist(file, reportBasedOnTemplateFile);
		}
    	return reportBasedOnTemplateFile;
    }
    
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(Object object,T reportModel,File template,Boolean persist,String fileExtension){
    	return buildBinaryContent(object, DEFAULT_REPORT_FIELD_NAME, reportModel, template, persist, fileExtension);
    }
    
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(Object object,T reportModel,File template,Boolean persist){
    	return buildBinaryContent(object, reportModel, template, persist, DEFAULT_FILE_EXTENSION);
    }
    
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(File file,String title,String extension){
    	ReportBasedOnTemplateFile<T> reportBasedOnTemplateFile = new ReportBasedOnTemplateFile<>();
    	reportBasedOnTemplateFile.setTitle(title);
    	reportBasedOnTemplateFile.setFileExtension(extension);
    	prepare(reportBasedOnTemplateFile);
    	reportBasedOnTemplateFile.setBytes(file.getBytes());
		logTrace("Report binary content taken from file {}.", file);
		return reportBasedOnTemplateFile;
    }
    
    public <T> ReportBasedOnTemplateFile<T> buildBinaryContent(File file,String title){
    	return buildBinaryContent(file, title, DEFAULT_FILE_EXTENSION);
    }
    
    /**
     * Persist the report to the specific file
     * @param file
     * @param report
     */
    public void persist(File file,AbstractReport<?> report){
    	//logTrace("Persist report {}",report);
		file.setBytes(report.getBytes());
		file.setExtension(report.getFileExtension());
		if(file.getIdentifier()==null){
			RootBusinessLayer.getInstance().getFileBusiness().create(file);
			logTrace("Report file {} created",report);
		}else{
			RootBusinessLayer.getInstance().getFileBusiness().update(file);
			logTrace("Report file {} updated",report);
		}
	}
    
    
	
}
