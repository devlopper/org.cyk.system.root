package org.cyk.system.root.business.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFileConfiguration;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.test.TestEnvironmentListener;
import org.cyk.utility.common.test.TestEnvironmentListener.Try;
import org.hamcrest.Matcher;

@Getter @Setter
public abstract class AbstractBusinessTestHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7237631838579839333L;

	//public static final Collection<TestEnvironmentListener> TEST_ENVIRONMENT_LISTENERS = TestEnvironmentListener.COLLECTION; //new ArrayList<>();
	
	protected String reportFolder = "target/report";
	
	protected RandomDataProvider randomDataProvider = RandomDataProvider.getInstance();
	
	protected ReportBusiness reportBusiness;
	@Inject protected GenericBusiness genericBusiness;
	@Inject protected IntervalCollectionBusiness intervalCollectionBusiness;
	@Inject protected MetricCollectionBusiness metricCollectionBusiness;
	
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
		reportBasedOnDynamicBuilderParameters(aParameters, getRootBusinessLayer().getParameterGenericReportBasedOnDynamicBuilder());
	}
	
	@SuppressWarnings("unchecked")
	public void reportBasedOnDynamicBuilderParameters(Class<?> aClass,String reportIdentifier){
		ReportBasedOnDynamicBuilderParameters<AbstractIdentifiable> parameters = new ReportBasedOnDynamicBuilderParameters<AbstractIdentifiable>();
		parameters.setIdentifiableClass((Class<? extends AbstractIdentifiable>) aClass);
		reportBasedOnDynamicBuilderParameters(parameters,reportIdentifier);
	}
	
	public void reportBasedOnDynamicBuilderParameters(Class<?> aClass){
		reportBasedOnDynamicBuilderParameters(aClass,getRootBusinessLayer().getParameterGenericReportBasedOnDynamicBuilder());
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
	
	protected void assertEquals(String message,Object expected,Object actual){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.assertEquals(message, expected, actual);
	}
	
	protected void assertBigDecimalEquals(String message,BigDecimal expected,BigDecimal actual){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.assertBigDecimalEquals(message, expected, actual);
	}
	
	protected void assertBigDecimalEquals(String message,String expected,BigDecimal actual){
		assertBigDecimalEquals(message,new BigDecimal(expected),actual);
	}
	
	protected static void assertThat(String reason,Boolean assertion){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.assertThat(reason, assertion);
	}
	
	protected static <T> void assertThat(T actual,Matcher<? super T> matcher){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.assertThat(actual, matcher);
	}
	
	protected  <T> void assertThat(String reason,T actual,Matcher<? super T> matcher){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.assertThat(reason, actual, matcher);
	}
	
	protected static void hasProperty(Object object,String name,Object value){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.hasProperty(object, name, value);
	}
	
	protected static void hasProperties(Object object,Object...entries){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.hasProperties(object, entries);
	}
	
	protected static <T> void contains(Class<T> aClass,Collection<T> list,Object[] names,Object[][] values){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.contains(aClass, list, names, values);
	}
	
	/**/
	
	protected void writeReport(AbstractReport<?> report){
    	try {
			write(report.getBytes(),System.getProperty("user.dir")+"/"+reportFolder+"/"+report.getFileName()+System.currentTimeMillis()+"."+report.getFileExtension());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	protected void writeReport(org.cyk.system.root.model.file.File file,String name,String extension){
    	try {
			write(file.getBytes(), System.getProperty("user.dir")+"/"+reportFolder+"/"+name+"."+extension);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	protected void write(byte[] bytes,String filePath){
    	try {
    		File file = new File(filePath);
    		file.getParentFile().mkdirs();
			IOUtils.write(bytes, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public <T extends AbstractActor> void assertActorRegistrationCode(Class<T> classActor,List<T> actors,String[] registrationCodes){
		for(int i=0;i<actors.size();i++){
			T actor = actors.get(i);
			assertEquals("Registration code of "+actor.getPerson(), registrationCodes[i], actor.getRegistration().getCode());
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractActor> T createActor(Class<T> actorClass,String code,String[] names){
		return (T) genericBusiness.create(_createActor_(actorClass, code, names));
	}
	
	private <T extends AbstractActor> T _createActor_(Class<T> actorClass,String code,String[] names){
		T actor = RootRandomDataProvider.getInstance().actor(actorClass);
		actor.getRegistration().setCode(code);
		if(names!=null){
			if(names.length>0)
				actor.getPerson().setName(names[0]);
			if(names.length>1)
				actor.getPerson().setLastName(names[1]);
			if(names.length>2)
				actor.getPerson().setSurname(names[2]);
		}
		return actor;
	}
	
	public <T extends AbstractActor> List<T> createActors(Class<T> actorClass,String[] codes){
		List<T> list = new ArrayList<>();
		for(String code : codes)
			list.add(createActor(actorClass,code, null));
		return list;
	}

	/**/
	
	/* Setters */
	
	public void set(Interval interval,String code,String name,String low,String high){
		interval.setCode(code);
		interval.setName(name);
		interval.getLow().setValue(commonUtils.getBigDecimal(low));
		interval.getHigh().setValue(commonUtils.getBigDecimal(high));
	}
	
	public void set(MovementCollection movementCollection,String code,String value,String low,String high,String incrementActionName,String decrementActionName){
		movementCollection.setCode(code);
		movementCollection.setName(code);
		movementCollection.setValue(value ==null ? null : new BigDecimal(value));
		movementCollection.setInterval(new Interval());
		set(movementCollection.getInterval(),code+"int", code+"intname", low,high);
		
		movementCollection.setIncrementAction(new MovementAction());
		set(movementCollection.getIncrementAction(), code+"inc",incrementActionName, "0", null);
		
		movementCollection.setDecrementAction(new MovementAction());
		set(movementCollection.getDecrementAction(), code+"dec",decrementActionName, "0", null);
		
	}
	
	public void set(MovementAction movementAction,String code,String name,String low,String high){
		movementAction.setCode(code);
		movementAction.setName(name);
		movementAction.setInterval(new Interval());
		set(movementAction.getInterval(),code, name, low,high);
	}
	
	public void set(Movement movement,String movementCollectionCode,String value){
		movement.setValue(value==null?null : new BigDecimal(value));
		movement.setCollection(getRootBusinessLayer().getMovementCollectionBusiness().find(movementCollectionCode));
		movement.setCode(movementCollectionCode+"_"+RandomStringUtils.randomAlphabetic(3));
		movement.setName(movement.getCode());
		movement.setAction(movement.getValue() == null ? null : movement.getValue().signum() == 1 ? movement.getCollection().getIncrementAction() : movement.getCollection().getDecrementAction());
	}
	
	/* Businesses */
	
	public void createMovement(String movementCollectionCode,String value,String expectedValue,String expectedThrowableMessage){
    	final Movement movement = new Movement();
    	set(movement,movementCollectionCode, value);
    	
    	if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {getRootBusinessLayer().getMovementBusiness().create(movement);}
    		}.execute();
    	}else{
    		getRootBusinessLayer().getMovementBusiness().create(movement);
    		assertMovementCollection(movement.getCollection(), expectedValue);
    	}
    }
	public void createMovement(String movementCollectionCode,String value,String expectedBalance){
		createMovement(movementCollectionCode,value, expectedBalance,null);
	}
	
	/* Assertions */
	
	private void assertMovementCollection(MovementCollection movementCollection,String expectedValue){
    	movementCollection = (MovementCollection) genericBusiness.use(MovementCollection.class).find(movementCollection.getIdentifier());
    	assertEquals("Value",new BigDecimal(expectedValue), movementCollection.getValue());
    }
	
	/* Exceptions */
	
	private void valueMustNotBeOffThanActionIntervalExtremity(String movementCollectionCode,Boolean incrementAction,Boolean lowExtemity){
		MovementCollection movementCollection = getRootBusinessLayer().getMovementCollectionBusiness().find(movementCollectionCode);
		MovementAction action = Boolean.TRUE.equals(incrementAction) ? movementCollection.getIncrementAction() : movementCollection.getDecrementAction();
		BigDecimal value = Boolean.TRUE.equals(lowExtemity) ? action.getInterval().getLow().getValue() : action.getInterval().getHigh().getValue();
		if(value==null)
			return;
		createMovement(movementCollectionCode,value.toString(), null,getThrowableMessage(movementCollectionCode, isIncrementAction(value.toString()),0));
	}
	public void incrementValueMustNotBeLessThanIntervalLow(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.TRUE, Boolean.TRUE);
	}
	public void incrementValueMustNotBeGreaterThanIntervalHigh(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.TRUE, Boolean.FALSE);
	}
	public void decrementValueMustNotBeLessThanIntervalLow(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.FALSE, Boolean.TRUE);
	}
	public void decrementValueMustNotBeGreaterThanIntervalHigh(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.FALSE, Boolean.FALSE);
	}
	
	private void collectionValueMustNotBeOffThanIntervalExtremity(String movementCollectionCode,Boolean incrementAction){
		MovementCollection movementCollection = getRootBusinessLayer().getMovementCollectionBusiness().find(movementCollectionCode);
		BigDecimal value = Boolean.TRUE.equals(incrementAction) ? movementCollection.getInterval().getHigh().getValue() : BigDecimal.ONE.negate();
		if(value==null)
			return;
		createMovement(movementCollectionCode,value.toString(), null,getThrowableMessage(movementCollectionCode, isIncrementAction(value.toString()),3));
	}
	public void collectionValueMustNotBeLessThanIntervalLow(String movementCollectionCode){
		collectionValueMustNotBeOffThanIntervalExtremity(movementCollectionCode,Boolean.FALSE);
    }
	public void collectionValueMustNotBeGreaterThanIntervalHigh(String movementCollectionCode){
		collectionValueMustNotBeOffThanIntervalExtremity(movementCollectionCode,Boolean.TRUE);
    }
	
	
	private String getThrowableMessage(String movementCollectionCode,Boolean increment,Integer actionId){
		MovementCollection movementCollection = getRootBusinessLayer().getMovementCollectionBusiness().find(movementCollectionCode);
		MovementAction action = Boolean.TRUE.equals(increment) ? movementCollection.getIncrementAction() : movementCollection.getDecrementAction();
		if(actionId==0)
			return String.format("%s doit être supérieur à %s",action.getName(),action.getInterval().getLow().getValue());
		
		if(actionId==1)
			return String.format("%s doit être supérieur à %s",action.getName(),action.getInterval().getLow().getValue());
		
		if(actionId==2)
			return String.format("%s doit être inférieur à %",action.getName(),action.getInterval().getHigh().getValue());
		
		if(actionId==3)
			return String.format("%s doit être entre %s et %s",action.getName()
				,getRootBusinessLayer().getNumberBusiness().format(movementCollection.getInterval().getLow().getValue())
				,getRootBusinessLayer().getNumberBusiness().format(movementCollection.getInterval().getHigh().getValue()));
		
		return Constant.EMPTY_STRING;
	}
	
	private Boolean isIncrementAction(String value){
		BigDecimal v = commonUtils.getBigDecimal(value);
		return v == null ? null : v.signum() == 0 ? null : v.signum() == 1;
	}
	
	protected Date getDate(String date){
		//RootBusinessLayer.getInstance().getTimeBusiness().find
		try {
			return date==null ? new Date() : DateUtils.parseDate(date, "dd/MM/yyyy HH:mm");
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**/
	
	protected RootBusinessLayer getRootBusinessLayer(){
		return RootBusinessLayer.getInstance();
	}
	
}
