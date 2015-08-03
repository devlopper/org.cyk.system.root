package org.cyk.system.root.business.impl;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.test.TestEnvironmentListener;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractTestHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7237631838579839333L;

	public static final Collection<TestEnvironmentListener> TEST_ENVIRONMENT_LISTENERS = new ArrayList<>();
	
	protected String reportFolder = "target/report";
	
	protected RandomDataProvider randomDataProvider = RandomDataProvider.getInstance();
	protected RootBusinessLayer rootBusinessLayer;
	protected ReportBusiness reportBusiness;
	
	
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
	
	/**/
	
	protected void assertBigDecimalEquals(String message,BigDecimal expected,BigDecimal actual){
		for(TestEnvironmentListener listener : TEST_ENVIRONMENT_LISTENERS)
			listener.assertBigDecimalEquals(message, expected, actual);
	}
	
	protected void assertBigDecimalEquals(String message,String expected,BigDecimal actual){
		assertBigDecimalEquals(message,new BigDecimal(expected),actual);
	}
	
	/**/
	
}
