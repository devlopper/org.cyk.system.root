package org.cyk.system.root.business.impl.file.report;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.ReportColumn;

public abstract class AbstractReportBusinessImpl implements ReportBusiness , Serializable {

	private static final long serialVersionUID = -3596293198479369047L;

	public static final Set<AbstractReportConfiguration<Object, AbstractReport<?>>> REPORTS = new HashSet<>();
	public static Boolean SHOW_OWNER_NAME = Boolean.TRUE;
	public static Boolean SHOW_OWNER_LOGO = Boolean.TRUE;
	
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
		//BusinessEntityInfos businessEntityInfos = applicationBusiness.findBusinessEntityInfos((Class<AbstractIdentifiable>) parameters.getClazz());
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
    	Collection<Field> fields = new ArrayList<>();
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
    		if(Boolean.TRUE.equals(dontIgnore))	
    			fields.add(field);
    	}
    	
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

}
