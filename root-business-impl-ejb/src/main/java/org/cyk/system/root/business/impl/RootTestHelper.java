package org.cyk.system.root.business.impl;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.utility.common.generator.RandomDataProvider;

@Getter @Setter @Singleton
public class RootTestHelper implements Serializable {

	private static final long serialVersionUID = 7237631838579839333L;

	private String reportFolder = "target/report";
	
	private RandomDataProvider randomDataProvider = RandomDataProvider.getInstance();
	private RootBusinessLayer rootBusinessLayer;
	private ReportBusiness reportBusiness;
	/*
	@SuppressWarnings("unchecked")
	public <T extends ReportBasedOnTemplateFile<?>> void reportBasedOnTemplateFile(Class<T> aClass,Collection<T> datas,String reportIdentifier){
		ReportBasedOnTemplateFile<T> r ;
		AbstractReportConfiguration<AbstractIdentifiable, ReportBasedOnTemplateFile<T>> c = reportBusiness.findConfiguration(reportIdentifier);
        r = (ReportBasedOnTemplateFile<T>) ((ReportBasedOnTemplateFileConfiguration<AbstractIdentifiable, T>)c).build(aClass,datas,null,null,null);
       
        r = reportBusiness.build(r);
		
        reportBusiness.write(new File(reportFolder),r);
	}
	*/
	@SuppressWarnings("unchecked")
	public void reportBasedOnDynamicBuilderParameters(ReportBasedOnDynamicBuilderParameters<?> aParameters,String reportIdentifier){
		ReportBasedOnDynamicBuilderParameters<AbstractIdentifiable> identifiableParameters = (ReportBasedOnDynamicBuilderParameters<AbstractIdentifiable>) aParameters;
		ReportBasedOnDynamicBuilder<AbstractIdentifiable> r ;
		if(identifiableParameters.getDatas()==null){
        	AbstractReportConfiguration<AbstractIdentifiable, ReportBasedOnDynamicBuilder<AbstractIdentifiable>> c = reportBusiness.findConfiguration(reportIdentifier);
        	r = ((ReportBasedOnDynamicBuilderConfiguration<AbstractIdentifiable, ReportBasedOnDynamicBuilder<AbstractIdentifiable>>)c).build(identifiableParameters);
        }else
        	r = reportBusiness.build(identifiableParameters);
		
        reportBusiness.write(new File(reportFolder),r);
	}
	public void reportBasedOnDynamicBuilderParameters(ReportBasedOnDynamicBuilderParameters<?> aParameters){
		reportBasedOnDynamicBuilderParameters(aParameters, rootBusinessLayer.getParameterGenericReportBasedOnDynamicBuilder());
	}
	
	@SuppressWarnings("unchecked")
	public void reportBasedOnDynamicBuilderParameters(Class<?> aClass,String reportIdentifier){
		ReportBasedOnDynamicBuilderParameters<AbstractIdentifiable> parameters = new ReportBasedOnDynamicBuilderParameters<AbstractIdentifiable>();
		parameters.setIdentifiableClass((Class<? extends AbstractIdentifiable>) aClass);
		reportBasedOnDynamicBuilderParameters(parameters,reportIdentifier);
	}
	
	public void reportBasedOnDynamicBuilderParameters(Class<?> aClass){
		reportBasedOnDynamicBuilderParameters(aClass,rootBusinessLayer.getParameterGenericReportBasedOnDynamicBuilder());
	}
	
	public void addReportParameter(ReportBasedOnDynamicBuilderParameters<?> parameters,String name,Object value){
		parameters.addParameter(name,value);
	}
	
	public void addReportParameterFromDate(ReportBasedOnDynamicBuilderParameters<?> parameters,Date date){
		addReportParameter(parameters, RootBusinessLayer.getInstance().getParameterFromDate(), date);
	}
	
	public void addReportParameterToDate(ReportBasedOnDynamicBuilderParameters<?> parameters,Date date){
		addReportParameter(parameters, RootBusinessLayer.getInstance().getParameterToDate(), date);
	}
	
}
