package org.cyk.system.root.business.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFileConfiguration;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.test.TestEnvironmentListener;
import org.hamcrest.Matcher;

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
	
	public <T extends AbstractIdentifiable> void reportBasedOnTemplateFile(Class<T> aClass,Collection<T> collection,Map<String, String[]> map,String reportIdentifier){
        AbstractReportConfiguration<T, ReportBasedOnTemplateFile<T>> c = reportBusiness.findConfiguration(reportIdentifier);
        ReportBasedOnTemplateFile<T> r = ((ReportBasedOnTemplateFileConfiguration<T, ReportBasedOnTemplateFile<T>>)c)
        		.build(aClass,collection,"pdf",Boolean.FALSE,map);
        
        reportBusiness.build(r,Boolean.FALSE);
		
        reportBusiness.write(new File(reportFolder),r);
	}
	
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
	
	protected static void assertThat(String reason,Boolean assertion){
		for(TestEnvironmentListener listener : TEST_ENVIRONMENT_LISTENERS)
			listener.assertThat(reason, assertion);
	}
	
	protected static <T> void assertThat(T actual,Matcher<? super T> matcher){
		for(TestEnvironmentListener listener : TEST_ENVIRONMENT_LISTENERS)
			listener.assertThat(actual, matcher);
	}
	
	protected  <T> void assertThat(String reason,T actual,Matcher<? super T> matcher){
		for(TestEnvironmentListener listener : TEST_ENVIRONMENT_LISTENERS)
			listener.assertThat(reason, actual, matcher);
	}
	
	protected static void hasProperty(Object object,String name,Object value){
		for(TestEnvironmentListener listener : TEST_ENVIRONMENT_LISTENERS)
			listener.hasProperty(object, name, value);
	}
	
	protected static void hasProperties(Object object,Object...entries){
		for(TestEnvironmentListener listener : TEST_ENVIRONMENT_LISTENERS)
			listener.hasProperties(object, entries);
	}
	
	protected static <T> void contains(Class<T> aClass,Collection<T> list,Object[] names,Object[][] values){
		for(TestEnvironmentListener listener : TEST_ENVIRONMENT_LISTENERS)
			listener.contains(aClass, list, names, values);
	}
	
	/**/
	
	protected void writeReport(AbstractReport<?> report){
    	try {
			IOUtils.write(report.getBytes(), new FileOutputStream( System.getProperty("user.dir")+"/"+reportFolder+"/"+report.getFileName()+System.currentTimeMillis()+"."+report.getFileExtension()));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	protected void writeReport(org.cyk.system.root.model.file.File file,String name,String extension){
    	try {
			IOUtils.write(file.getBytes(), new FileOutputStream( System.getProperty("user.dir")+"/"+reportFolder+"/"+name+"."+extension));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}