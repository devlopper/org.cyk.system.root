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
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Column;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.ReportColumn;

public abstract class AbstractReportBusinessImpl<STYLE> implements ReportBusiness<STYLE> , Serializable {

	private static final long serialVersionUID = -3596293198479369047L;

	public static final Set<AbstractReportConfiguration<Object, AbstractReport<?>>> REPORTS = new HashSet<>();
	
	@Inject protected LanguageBusiness languageBusiness;
	@Inject protected ApplicationBusiness applicationBusiness;
	
	/* Registration */
	
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
	
	/* Builders */
	
	@SuppressWarnings("unchecked")
	@Override
	public <MODEL> ReportBasedOnDynamicBuilder<MODEL> build(ReportBasedOnDynamicBuilderParameters<MODEL> parameters) {
		//return build(parameters.getClazz(),parameters.getDatas(),parameters.getFileExtension(), parameters.getPrint(),parameters.getReportBasedOnDynamicBuilderListener());
		BusinessEntityInfos businessEntityInfos = applicationBusiness.findBusinessEntityInfos((Class<AbstractIdentifiable>) parameters.getClazz());
		ReportBasedOnDynamicBuilder<MODEL> report = null;
		if(parameters.getReport()==null){
			report = new ReportBasedOnDynamicBuilder<MODEL>();
			parameters.setReport(report);
			report.setTitle(languageBusiness.findText("report.datatable.title", new Object[]{businessEntityInfos==null?"TITLE":languageBusiness.findText(businessEntityInfos.getUiLabelId())}));
			report.setFileName(report.getTitle());
			report.setFileExtension(parameters.getFileExtension());
			report.setColumns(findColumns(parameters.getClazz()));
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
	public Collection<Column> findColumns(Class<?> aClass) {
		Collection<Column> columns = new ArrayList<>();
		Collection<Class<? extends Annotation>> filters = new ArrayList<>();
    	filters.add(Input.class);
    	filters.add(ReportColumn.class);
    	for(Field field : CommonUtils.getInstance().getAllFields(aClass, filters)){
    		columns.add(new Column(field.getName(),field.getType(),languageBusiness.findFieldLabelText(field)) );
    	}
		return columns;
	}
			
	/**/
	
	protected abstract <MODEL> void __build__(ReportBasedOnDynamicBuilderParameters<MODEL> parameters);

}
