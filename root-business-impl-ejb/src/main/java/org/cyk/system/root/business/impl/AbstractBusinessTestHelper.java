package org.cyk.system.root.business.impl;

import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_DAUGHTER;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_FATHER;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_MOTHER;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_SON;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_SPOUSE_HUSBAND;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_SPOUSE_WIFE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineAlphabetBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineStateBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFileConfiguration;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.language.LanguageCollection;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineFinalState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineTransition;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.api.geography.ElectronicMailDao;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineAlphabetDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateDao;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeRoleDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.ClassRepository.ClassField;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.file.FileNameNormaliser;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.test.TestEnvironmentListener;
import org.cyk.utility.common.test.TestEnvironmentListener.Try;
import org.hamcrest.Matcher;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractBusinessTestHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7237631838579839333L;

	//public static final Collection<TestEnvironmentListener> TEST_ENVIRONMENT_LISTENERS = TestEnvironmentListener.COLLECTION; //new ArrayList<>();
	
	protected String reportFolder = "target/file/report";
	protected String fileFolder = "target/file";
	
	protected RandomDataProvider randomDataProvider = RandomDataProvider.getInstance();
	
	protected ReportBusiness reportBusiness;
	@Inject protected GenericBusiness genericBusiness;
	@Inject protected IntervalCollectionBusiness intervalCollectionBusiness;
	@Inject protected MetricCollectionBusiness metricCollectionBusiness;
	@Inject protected FiniteStateMachineStateDao finiteStateMachineStateDao;
	@Inject protected FiniteStateMachineAlphabetDao finiteStateMachineAlphabetDao;
	
	public <T extends AbstractIdentifiable> T create(final T identifiable,String expectedThrowableMessage){
		if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {inject(GenericBusiness.class).create(identifiable);}
    		}.execute();
    	}else{
    		inject(GenericBusiness.class).create(identifiable);
    		assertThat("Created", inject(PersistenceInterfaceLocator.class).injectTypedByObject(identifiable).read(identifiable.getIdentifier())!=null);
    	}
		return identifiable;
	}
	public <T extends AbstractIdentifiable> T create(final T identifiable){
		return create(identifiable, null);
	}
	
	public <T extends AbstractIdentifiable> T read(final Class<T> aClass,final String code,String expectedThrowableMessage){
		T read = null;
		if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(code);}
    		}.execute();
    	}else{
    		read = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(code);
    		assertThat("Read", read!=null);
    	}
		return read;
	}
	public <T extends AbstractIdentifiable> T read(final Class<T> aClass,final String code){
		return read(aClass,code, null);
	}
	
	public <T extends AbstractIdentifiable> T update(final T identifiable,String expectedThrowableMessage){
		if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {inject(GenericBusiness.class).update(identifiable);}
    		}.execute();
    	}else{
    		inject(GenericBusiness.class).update(identifiable);
    		assertThat("Updated", inject(PersistenceInterfaceLocator.class).injectTypedByObject(identifiable).read(identifiable.getIdentifier())!=null);
    	}
		return identifiable;
	}
	public <T extends AbstractIdentifiable> T update(T identifiable){
		return (T) update(identifiable,null);
	}
	
	public <T extends AbstractIdentifiable> T delete(final T identifiable,String expectedThrowableMessage){
		if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {inject(GenericBusiness.class).delete(identifiable);}
    		}.execute();
    	}else{
    		inject(GenericBusiness.class).delete(identifiable);
    		assertThat("Deleted", inject(PersistenceInterfaceLocator.class).injectTypedByObject(identifiable).read(identifiable.getIdentifier())==null);
    	}
		return identifiable;
	}
	public <T extends AbstractIdentifiable> T delete(T identifiable){
		return delete(identifiable,null);
	}
	public <T extends AbstractIdentifiable> T delete(Class<T> aClass,String code,String expectedThrowableMessage){
		T identifiable = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(code);
		assertThat("Found "+aClass.getSimpleName()+" with code "+code+" to delete", identifiable!=null);
		return delete(identifiable,expectedThrowableMessage);
	}
	public <T extends AbstractIdentifiable> T delete(Class<T> aClass,String code){
		return delete(aClass,code,null);
	}
	
	public void throwMessage(final Runnable runnable,String expectedThrowableMessage){
		new Try(expectedThrowableMessage){ 
			private static final long serialVersionUID = -8176804174113453706L;
			@Override protected void code() {runnable.run();}
		}.execute();
	}
	
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
	
	protected void doAssertions(Object object,ObjectFieldValues expectedValues){
		for(Entry<ClassField, Object> entry : expectedValues.getValuesMap().entrySet()){
			String message = entry.getKey().toString();
			String expectedValue = (String)entry.getValue();
			Object actualValue = commonUtils.readProperty(object, entry.getKey().getName());
			if(String.class.equals(entry.getKey().getField().getType()))
				assertEquals(message, expectedValue, (String)actualValue);	
			else if(BigDecimal.class.equals(entry.getKey().getField().getType()))
				assertBigDecimalEquals(message, expectedValue, (BigDecimal)actualValue);	
			else
				assertEquals(entry.getKey().getField().getType()+" not yet handled", Boolean.TRUE, Boolean.FALSE);
			/*
			if(!object.getClass().equals(entry.getKey().getClazz()))
				continue;
			String message = entry.getKey().toString();
			Field field = FieldUtils.getField(object.getClass(), entry.getKey().getName(), Boolean.TRUE);
			if(field==null){
				
			}else{
				message = field.getDeclaringClass().getSimpleName()+"."+field.getName();
				try {
					Object value = FieldUtils.readField(field, object, Boolean.TRUE);
					logTrace("Assert "+field.getType().getSimpleName()+" equals between {} and {}", entry.getValue(),value);
					//System.out.println("Assert "+field.getType().getSimpleName()+" equals between "+entry.getValue()+" and "+value+"");
					if(BigDecimal.class.equals(field.getType()))
						assertBigDecimalEquals(message, entry.getValue(), (BigDecimal)value);	
					else
						assertEquals(field.getType()+" not yet handled", Boolean.TRUE, Boolean.FALSE);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			*/
		}
		//listener.assertEquals(message, expected, actual);	
	}
	
	protected static void assertEquals(String message,Object expected,Object actual){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.assertEquals(message, expected, actual);
	}
	
	public static void assertCodeExists(Class<? extends AbstractIdentifiable> aClass,String code){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.assertCodeExists(aClass, code);
	}
	
	protected static void assertBigDecimalEquals(String message,BigDecimal expected,BigDecimal actual){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.assertBigDecimalEquals(message, expected, actual);
	}
	
	protected static void assertBigDecimalEquals(String message,String expected,BigDecimal actual){
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
	
	public void write(org.cyk.system.root.model.file.File file,String name){
    	try {
			write(file.getBytes(), System.getProperty("user.dir")+"/"+fileFolder+"/"+name+Constant.CHARACTER_DOT+file.getExtension());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public void write(org.cyk.system.root.model.file.File file){
    	write(file, StringUtils.defaultIfBlank(new FileNameNormaliser.Adapter.Default().setInput(file.getName()).execute(), "file")+System.currentTimeMillis());
    }
	
	protected void writeStream(ByteArrayOutputStream byteArrayOutputStream,String name,String extension){
    	try {
			write(byteArrayOutputStream.toByteArray(), System.getProperty("user.dir")+"/"+reportFolder+"/"+name+"."+extension);
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
			assertEquals("Registration code of "+actor.getPerson(), registrationCodes[i], actor.getGlobalIdentifier().getCode());
		}
	}
	
	public void createFiniteStateMachine(String machineCode,String[] alphabetCodes,String[] stateCodes,String initialStateCode,String[] finalStateCodes,String[][] transitions){
		FiniteStateMachine machine = new FiniteStateMachine();
		set(machine, machineCode);
		genericBusiness.create(machine);
		
		Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
		for(String code : alphabetCodes){
			FiniteStateMachineAlphabet alphabet = new FiniteStateMachineAlphabet();
			set(alphabet,machineCode,code);
			identifiables.add(alphabet);
		}
		genericBusiness.create(identifiables);
		
		identifiables = new ArrayList<>();
		for(String code : stateCodes){
			FiniteStateMachineState state = new FiniteStateMachineState();
			set(state,machineCode,code);
			identifiables.add(state);
		}
		genericBusiness.create(identifiables);
		
		machine.setInitialState(finiteStateMachineStateDao.readByGlobalIdentifierCode(machineCode+Constant.CHARACTER_UNDESCORE+initialStateCode));
		machine.setCurrentState(machine.getInitialState());
		genericBusiness.update(machine);
		
		identifiables = new ArrayList<>();
		for(String code : finalStateCodes){
			FiniteStateMachineFinalState state = new FiniteStateMachineFinalState();
			state.setState(finiteStateMachineStateDao.readByGlobalIdentifierCode(machineCode+Constant.CHARACTER_UNDESCORE+code));
			identifiables.add(state);
		}
		genericBusiness.create(identifiables);
		
		identifiables = new ArrayList<>();
		for(String[] transitionInfos : transitions){
			FiniteStateMachineTransition transition = new FiniteStateMachineTransition();
			transition.setFromState(finiteStateMachineStateDao.readByGlobalIdentifierCode(machineCode+Constant.CHARACTER_UNDESCORE+transitionInfos[0]));
			transition.setAlphabet(finiteStateMachineAlphabetDao.readByGlobalIdentifierCode(machineCode+Constant.CHARACTER_UNDESCORE+transitionInfos[1]));
			transition.setToState(finiteStateMachineStateDao.readByGlobalIdentifierCode(machineCode+Constant.CHARACTER_UNDESCORE+transitionInfos[2]));
			identifiables.add(transition);
		}
		genericBusiness.create(identifiables);
	}

	/**/
	
	/* Setters */
	
	protected void setEnumeration(AbstractEnumeration enumeration,String code){
		enumeration.setCode(code);
		enumeration.setName(code);
	}
	
	public void set(Interval interval,String code,String name,String low,String high){
		interval.setCode(code);
		interval.setName(name);
		interval.getLow().setValue(commonUtils.getBigDecimal(low));
		interval.getHigh().setValue(commonUtils.getBigDecimal(high));
	}
	
	public void set(MovementCollection movementCollection,String code,String name,String value,String low,String high,String incrementActionName,String decrementActionName){
		movementCollection.setCode(code);
		movementCollection.setName(name);
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
		
	public void set(FiniteStateMachine machine,String code){
		setEnumeration(machine, code);
	}
	public void set(FiniteStateMachineAlphabet alphabet,String machineCode,String code){
		setEnumeration(alphabet, machineCode+"_"+code);
		alphabet.setMachine(inject(FiniteStateMachineBusiness.class).findByGlobalIdentifierCode(machineCode));
	}
	public void set(FiniteStateMachineState state,String machineCode,String code){
		setEnumeration(state, machineCode+"_"+code);
		state.setMachine(inject(FiniteStateMachineBusiness.class).findByGlobalIdentifierCode(machineCode));
	}
	
	
	/* Businesses */
	
	public Movement createMovement(String movementCollectionCode,String value,String expectedValue,String expectedThrowableMessage){
		MovementCollection movementCollection = inject(MovementCollectionBusiness.class).find(movementCollectionCode);
    	final Movement movement = inject(MovementBusiness.class).instanciateOne(movementCollection
    			,StringUtils.startsWith(value, Constant.CHARACTER_MINUS.toString()) ? movementCollection.getDecrementAction():movementCollection.getIncrementAction(), value);
    	if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {create(movement);}
    		}.execute();
    	}else{
    		create(movement);
    		assertMovementCollection(movement.getCollection(), expectedValue);
    	}
    	return movement;
    }
	public Movement createMovement(String movementCollectionCode,String value,String expectedValue){
		return createMovement(movementCollectionCode,value, expectedValue,null);
	}
	
	public Movement updateMovement(Movement movement,String value,String expectedValue,String expectedThrowableMessage){
		movement.setValue(commonUtils.getBigDecimal(value));
		final Movement pMovement = movement;
    	
    	if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {update(pMovement);}
    		}.execute();
    	}else{
    		update(pMovement);
    		assertMovementCollection(pMovement.getCollection(), expectedValue);
    	}
    	return pMovement;
    }
	public Movement updateMovement(Movement movement,String value,String expectedValue){
		return updateMovement(movement,value, expectedValue,null);
	}
	
	public Movement deleteMovement(final Movement movement,String expectedValue,String expectedThrowableMessage){
    	if(expectedThrowableMessage!=null){
    		new Try(expectedThrowableMessage){ 
    			private static final long serialVersionUID = -8176804174113453706L;
    			@Override protected void code() {delete(movement);}
    		}.execute();
    	}else{
    		delete(movement);
    		assertMovementCollection(movement.getCollection(), expectedValue);
    	}
    	return movement;
    }
	public Movement deleteMovement(final Movement movement,String expectedValue){
		return deleteMovement(movement, expectedValue,null);
	}
	
	public void readFiniteStateMachine(String machineCode,String alphabetCode,String expectedStateCode){
		FiniteStateMachine machine = inject(FiniteStateMachineBusiness.class).findByGlobalIdentifierCode(machineCode);
		inject(FiniteStateMachineBusiness.class).read(machine, inject(FiniteStateMachineAlphabetBusiness.class)
				.findByGlobalIdentifierCode(alphabetCode));
		assertFiniteStateMachine(machine, expectedStateCode);
	}
	
	public void readFiniteStateMachine(String machineCode,String[] alphabetCodes,String expectedStateCode){
		for(int i=0;i<alphabetCodes.length;i++)
			readFiniteStateMachine(machineCode, alphabetCodes[i], i == alphabetCodes.length-1 ? expectedStateCode : null);
		
	}
	
	public void findByFromStateByAlphabet(String machineCode,String fromStateCode,String alphabetCode,String expectedStateCode){
		String message = fromStateCode+" and "+alphabetCode+" > "+expectedStateCode;
		FiniteStateMachineState state = inject(FiniteStateMachineStateBusiness.class)
			.findByFromStateByAlphabet(inject(FiniteStateMachineStateBusiness.class).findByGlobalIdentifierCode(fromStateCode), 
					inject(FiniteStateMachineAlphabetBusiness.class).findByGlobalIdentifierCode(alphabetCode));
		assertEquals(message, expectedStateCode, state.getCode());
	}
	
	/* Assertions */
	
	public void assertMovementCollection(String code,String expectedValue){
    	assertMovementCollection(inject(MovementCollectionDao.class).read(code), expectedValue);
    }
	
	public void assertMovementCollection(MovementCollection movementCollection,String expectedValue){
    	assertBigDecimalEquals("Value",new BigDecimal(expectedValue), movementCollection.getValue());
    }
	
	public void assertMovement(String code,String expectedValue,String expectedCollectionValue,Boolean increment,String expectedSupportingDocumentProvider,String expectedSupportingDocumentIdentifier){
    	assertMovement(inject(MovementDao.class).read(code), expectedValue,expectedCollectionValue, increment, expectedSupportingDocumentProvider, expectedSupportingDocumentIdentifier);
    }
	
	public void assertMovement(String code,String expectedValue,String expectedCollectionValue){
		assertMovement(code, expectedValue, expectedCollectionValue, null, null, null);
	}
	
	public void assertMovement(Movement movement,String expectedValue,String expectedCollectionValue,Boolean expectedIncrement,String expectedSupportingDocumentProvider,String expectedSupportingDocumentIdentifier){
    	assertBigDecimalEquals("Value",new BigDecimal(expectedValue), movement.getValue());
    	assertEquals("Action",expectedIncrement == null ? null : (Boolean.TRUE.equals(expectedIncrement) ? movement.getCollection().getIncrementAction() : movement.getCollection().getDecrementAction()), movement.getAction());
    	//assertEquals("Supporting Document Provider",expectedSupportingDocumentProvider, movement.getSupportingDocumentProvider());
    	//assertEquals("Supporting Document Identifier",expectedSupportingDocumentIdentifier, movement.getSupportingDocumentIdentifier());
    	assertMovementCollection(movement.getCollection().getCode(), expectedCollectionValue);
    }
	
	private void assertFiniteStateMachine(FiniteStateMachine machine,String expectedStateCode){
		if(expectedStateCode!=null)
			assertEquals("Current state", expectedStateCode, machine.getCurrentState().getCode());
	}
	
	
	
	/* Exceptions */
	
	private void valueMustNotBeOffThanActionIntervalExtremity(String movementCollectionCode,Boolean incrementAction,Boolean lowExtemity){
		MovementCollection movementCollection = inject(MovementCollectionBusiness.class).find(movementCollectionCode);
		MovementAction action = Boolean.TRUE.equals(incrementAction) ? movementCollection.getIncrementAction() : movementCollection.getDecrementAction();
		BigDecimal value = Boolean.TRUE.equals(lowExtemity) ? action.getInterval().getLow().getValue() : action.getInterval().getHigh().getValue();
		if(BigDecimal.ZERO.equals(value))
			value = Boolean.TRUE.equals(lowExtemity) ? value.subtract(new BigDecimal("0.1")) : value.add(new BigDecimal("0.1"));
		if(value==null)
			return;
		createMovement(movementCollectionCode,value.toString(), null,getThrowableMessage(movementCollectionCode, isIncrementAction(value.toString()),0));
	}
	/*public void incrementValueMustNotBeLessThanIntervalLow(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.TRUE, Boolean.TRUE);
	}*/
	public void incrementValueMustNotBeGreaterThanIntervalHigh(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.TRUE, Boolean.FALSE);
	}
	public void decrementValueMustNotBeLessThanIntervalLow(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.FALSE, Boolean.TRUE);
	}
	/*
	public void decrementValueMustNotBeGreaterThanIntervalHigh(String movementCollectionCode){
		valueMustNotBeOffThanActionIntervalExtremity(movementCollectionCode, Boolean.FALSE, Boolean.FALSE);
	}*/
	
	private void collectionValueMustNotBeOffThanIntervalExtremity(String movementCollectionCode,Boolean incrementAction){
		MovementCollection movementCollection = inject(MovementCollectionBusiness.class).findByGlobalIdentifierCode(movementCollectionCode);
		BigDecimal value = Boolean.TRUE.equals(incrementAction) 
				? inject(IntervalBusiness.class).findLowestGreatestValue(movementCollection.getInterval()).add(BigDecimal.ONE) 
				: inject(IntervalBusiness.class).findGreatestLowestValue(movementCollection.getInterval()).subtract(BigDecimal.ONE);
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
	
	public void intervalContains(Interval interval,String value,String scale){
		assertThat("Interval "+interval+" contains "+value
				,inject(IntervalBusiness.class).contains(interval, commonUtils.getBigDecimal(value), scale==null ? 0: new Integer(scale)));
	}
	
	
	private String getThrowableMessage(String movementCollectionCode,Boolean increment,Integer actionId){
		MovementCollection movementCollection = inject(MovementCollectionBusiness.class).find(movementCollectionCode);
		MovementAction action = Boolean.TRUE.equals(increment) ? movementCollection.getIncrementAction() : movementCollection.getDecrementAction();
		String message = Constant.EMPTY_STRING,movementCollectionName=WordUtils.capitalizeFully(movementCollection.getName()),actionName = WordUtils.capitalizeFully(action.getName());
		if(actionId==0)
			message = String.format("%s doit être supérieur à %s",actionName,action.getInterval().getLow().getValue());
		
		if(actionId==1)
			message = String.format("%s doit être supérieur à %s",actionName,action.getInterval().getLow().getValue());
		
		if(actionId==2)
			message = String.format("%s doit être inférieur à %",actionName,action.getInterval().getHigh().getValue());
		
		if(actionId==3)
			message = String.format("%s doit être entre %s et %s",movementCollectionName 
				,inject(NumberBusiness.class).format(movementCollection.getInterval().getLow().getValue())
				,inject(NumberBusiness.class).format(movementCollection.getInterval().getHigh().getValue()));
		
		return message;
	}
	
	private Boolean isIncrementAction(String value){
		BigDecimal v = commonUtils.getBigDecimal(value);
		return v == null ? null : v.signum() == 0 ? null : v.signum() == 1;
	}
	
	protected Date getDate(String date,Boolean nowIfNull){
		try {
			return date==null ? Boolean.TRUE.equals(nowIfNull) ? new Date() : null : DateUtils.parseDate(date, "dd/MM/yyyy HH:mm");
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	protected Date getDate(String date){
		return getDate(date, Boolean.TRUE);
	}
	
	/**/
	
	public <IDENTIFIABLE extends AbstractIdentifiable> void createReportFile(IDENTIFIABLE identifiable,String reportTemplateCode,Locale locale,Integer count){
		inject(GenericBusiness.class).createReportFile(identifiable, reportTemplateCode, locale);
    	if(count!=null && count>0){
    		String fileRepresentationTyeCode = reportTemplateCode;
    		FileRepresentationType fileRepresentationType = inject(FileRepresentationTypeDao.class).read(fileRepresentationTyeCode);
    		Integer i = 0;
    		for(org.cyk.system.root.model.file.File file : inject(FileBusiness.class).findByRepresentationTypeByIdentifiable(fileRepresentationType, identifiable)){
    			if(i++ == count)
    				break;
    			write(file);
    		}	
    	}
	}
	
	/**/
	
	protected RootBusinessLayer getRootBusinessLayer(){
		return RootBusinessLayer.getInstance();
	}
	
	/**/
	
	public static class TestCase extends AbstractBean implements Serializable {

		private static final long serialVersionUID = -6026836126124339547L;

		private AbstractBusinessTestHelper helper;
		private List<AbstractIdentifiable> identifiables;
		private Boolean cleaned = Boolean.FALSE;
		private Set<Class<?>> classes=new LinkedHashSet<>();
		private Map<Class<?>,Long> countAllMap = new HashMap<>();
		
		public TestCase(AbstractBusinessTestHelper helper) {
			super();
			this.helper = helper;
		}
		
		public void add(AbstractIdentifiable identifiable){
			if(identifiables==null)
				identifiables = new ArrayList<>();
			identifiables.add(identifiable);
		}
		
		public void remove(AbstractIdentifiable identifiable){
			if(identifiables!=null){
				for(int i = 0 ; i< identifiables.size() ; )
					if(identifiables.get(i).equals(identifiable)){
						identifiables.remove(i);
						break;
					}else
						i++;
			}
				
		}

		public <T extends AbstractIdentifiable> T create(final T identifiable,String expectedThrowableMessage){
			@SuppressWarnings("unchecked")
			TypedDao<T> dao = (TypedDao<T>) inject(PersistenceInterfaceLocator.class).injectTyped(identifiable.getClass());
			if(StringUtils.isNotBlank(identifiable.getCode()))
				assertThat("Object to create with code <<"+identifiable.getCode()+">> already exist", dao.read(identifiable.getCode())==null);
			T created = helper.create(identifiable,expectedThrowableMessage);
			created = inject(PersistenceInterfaceLocator.class).injectTypedByObject(identifiable).read(identifiable.getIdentifier());
			assertThat("Object created not found", created!=null);
			add(created);
			return created;
		}
		
		public <T extends AbstractIdentifiable> T create(final T identifiable){
			return create(identifiable,null);
		}
		
		public <T extends AbstractIdentifiable> T read(Class<T> aClass,String code,String expectedThrowableMessage){
			T read = helper.read(aClass,code,expectedThrowableMessage);
			assertThat("Object read not found", read!=null);
			return read;
		}
		
		public <T extends AbstractIdentifiable> T read(Class<T> aClass,String code){
			return read(aClass,code,null);
		}
		
		public <T extends AbstractIdentifiable> T update(final T identifiable,Object[][] values,String expectedThrowableMessage){
			@SuppressWarnings("unchecked")
			TypedDao<T> dao = (TypedDao<T>) inject(PersistenceInterfaceLocator.class).injectTyped(identifiable.getClass());
			assertThat("Object to update not found", dao.read(identifiable.getCode())!=null);
			if(values!=null)
				for(int i = 0 ; i < values.length ; i++){
					commonUtils.setProperty(identifiable, (String)values[i][0], values[i][1]);
				}
			T updated = helper.update(identifiable,expectedThrowableMessage);
			updated = inject(PersistenceInterfaceLocator.class).injectTypedByObject(identifiable).read(identifiable.getIdentifier());
			if(values!=null)
				for(int i = 0 ; i < values.length ; i++){
					assertThat("Updated "+values[i][0], commonUtils.readProperty(updated, (String)values[i][0]).equals(values[i][1]));
				}
			assertThat("Object updated not found", updated!=null);
			return updated;
		}
		
		public <T extends AbstractIdentifiable> T update(Class<T> aClass,String code,Object[][] values){
			TypedDao<T> dao = (TypedDao<T>) inject(PersistenceInterfaceLocator.class).injectTyped(aClass);
			T identifiable = dao.read(code);
			return update(identifiable, values, null);
		}
		
		public <T extends AbstractIdentifiable> T update(final T identifiable){
			return update(identifiable,null,null);
		}
		
		public <T extends AbstractIdentifiable> T delete(final T identifiable,String expectedThrowableMessage){
			T deleted = null;
			if(identifiable!=null){
				deleted = helper.delete(identifiable,expectedThrowableMessage);
				remove(deleted);
			}
			return deleted;
		}
		
		public <T extends AbstractIdentifiable> T delete(final T identifiable){
			return delete(identifiable,null);
		}
		
		public <T extends AbstractIdentifiable> T deleteByCode(final Class<T> aClass,final String code){
			TypedDao<T> dao = inject(PersistenceInterfaceLocator.class).injectTyped(aClass);
			T identifiable = dao.read(code);
			assertThat("Object to delete not found", dao.read(code)!=null);
			delete(identifiable);
			identifiable = dao.read(code);
			assertThat("Object deleted found", dao.read(code)==null);
			return identifiable;
		}
		
		public void throwMessage(final Runnable runnable,String expectedThrowableMessage){
			helper.throwMessage(runnable, expectedThrowableMessage);
		}
		
		public TestCase prepare(){
			countAll(classes);
			return this;
		}
		
		public void clean(){
			assertCountAll(classes);
			if(Boolean.TRUE.equals(cleaned))
				return;
			if(identifiables!=null){
				Collections.reverse(identifiables);
				for(AbstractIdentifiable identifiable : identifiables)
					helper.delete(identifiable.getClass(), identifiable.getCode()); //inject(GenericBusiness.class).delete(identifiable);
			}
			cleaned = Boolean.TRUE;
		}
		
		public TestCase addClasses(Class<?>...classes){
			if(classes!=null){
				Collection<Class<?>> collection = new ArrayList<>();
				for(@SuppressWarnings("rawtypes") Class aClass : classes)
					collection.add(aClass);
				addClasses(collection);
			}
			return this;
		}
		
		public TestCase addClasses(Collection<Class<?>> classes){
			this.classes.addAll(classes);
			return this;
		}
		
		public TestCase addIdentifiableClasses(){
			for(BusinessEntityInfos businessEntityInfos : inject(ApplicationBusiness.class).findBusinessEntitiesInfos()){
				addClasses(businessEntityInfos.getClazz());
			}
			
			/*for(Class aClass : new ClassHelper().get("org.cyk.ui.web.primefaces.page", AbstractIdentifiable.class))
				addClasses(aClass);
				*/
			return this;
		}
		
		public TestCase addRequiredClasses(){
			return addClasses(GlobalIdentifier.class);
		}
		
		public TestCase addPersonClasses(){
			addClasses(org.cyk.system.root.model.file.File.class,ContactCollection.class,PersonExtendedInformations.class,JobInformations.class
					,MedicalInformations.class,LanguageCollection.class,Location.class);
			addRequiredClasses();
			return this;
		}
		
		protected Long getCountAll(Class<?> aClass){
			if(GlobalIdentifier.class.equals(aClass)){
				return inject(GlobalIdentifierDao.class).countAll();	
			}else{
				@SuppressWarnings("unchecked")
				TypedDao<AbstractIdentifiable> dao = (TypedDao<AbstractIdentifiable>) inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass);
				if(dao==null)
					return null;
				else
					return dao.countAll();
			}
		}
		
		public void countAll(Collection<Class<?>> classes){
			for(@SuppressWarnings("rawtypes") Class aClass : classes){
				countAllMap.put((Class<?>) aClass, getCountAll(aClass));	
			}
		}
		
		public TestCase countAll(Class<?>...classes){
			if(classes!=null)
				countAll(Arrays.asList(classes));
			return this;
		}
		
		public TestCase assertCountAll(@SuppressWarnings("rawtypes") Class aClass,Integer increment){
			assertEquals(aClass.getSimpleName()+" count all is not correct", new Long(commonUtils.getValueIfNotNullElseDefault(countAllMap.get(aClass),0l)+increment)
					, getCountAll(aClass));
			return this;
		}
		
		public TestCase assertCountAll(Collection<Class<?>> classes){
			for(Class<?> aClass : classes)
				assertCountAll(aClass,0);
			return this;
		}
		
		public TestCase assertCountAll(Class<?>...classes){
			if(classes!=null)
				assertCountAll(Arrays.asList(classes));
			return this;
		}
		
		/**/
		
		public Person createOnePerson(String code,String firstname,String lastnames,String email){
			Person person = inject(PersonBusiness.class).instanciateOne().setCode(code).setName(firstname).setLastnames(lastnames).setElectronicMail(email);
			create(person);
			if(StringUtils.isNotBlank(email))
				assertElectronicMail(code, email);
			return person;
		}
		
		public Person createOnePersonRandomly(String code){
			return create(inject(PersonBusiness.class).instanciateOneRandomly(code));
		}
		
		public void createManyPersonRandomly(String...codes){
			for(Person person : inject(PersonBusiness.class).instanciateManyRandomly(new HashSet<>(Arrays.asList(codes))))
				create(person);
		}
		
		public PersonRelationship createOnePersonRelationship(String person1Code,String role1Code,String person2Code,String role2Code){
			return create((PersonRelationship) inject(PersonRelationshipBusiness.class).instanciateOne(person1Code,role1Code,person2Code,role2Code));
		}
		
		public void createParentChildrenPersonRelationship(String parentPersonCode,String parentRoleCode,String[] sonPersonCodes,String[] daughterPersonCodes){
			if(StringUtils.isBlank(parentPersonCode))
				return;
			if(sonPersonCodes!=null)
				for(String sonPersonCode : sonPersonCodes)
					createOnePersonRelationship(parentPersonCode, parentRoleCode, sonPersonCode, FAMILY_PARENT_SON);
			
			if(daughterPersonCodes!=null)
				for(String daughterPersonCode : daughterPersonCodes)
					createOnePersonRelationship(parentPersonCode, parentRoleCode, daughterPersonCode, FAMILY_PARENT_DAUGHTER);
		}
		
		public void createFamilyPersonRelationship(String fatherPersonCode,String motherPersonCode,String[] sonPersonCodes,String[] daughterPersonCodes){
			for(String personCode : ArrayUtils.addAll(ArrayUtils.addAll(sonPersonCodes, daughterPersonCodes),new String[]{fatherPersonCode,motherPersonCode})){
				if(inject(PersonDao.class).read(personCode)==null)
					createOnePersonRandomly(personCode);
			}
			createOnePersonRelationship(fatherPersonCode,FAMILY_SPOUSE_HUSBAND,motherPersonCode,FAMILY_SPOUSE_WIFE);
			createParentChildrenPersonRelationship(fatherPersonCode, FAMILY_PARENT_FATHER, sonPersonCodes, daughterPersonCodes);
			createParentChildrenPersonRelationship(motherPersonCode, FAMILY_PARENT_MOTHER, sonPersonCodes, daughterPersonCodes);
						
			assertPersonRelationship(fatherPersonCode, FAMILY_PARENT_FATHER, FAMILY_PARENT_SON, sonPersonCodes);
			assertPersonRelationship(motherPersonCode, FAMILY_PARENT_MOTHER, FAMILY_PARENT_SON, sonPersonCodes);
			assertPersonRelationship(fatherPersonCode, FAMILY_PARENT_FATHER, FAMILY_PARENT_DAUGHTER, daughterPersonCodes);
			assertPersonRelationship(motherPersonCode, FAMILY_PARENT_MOTHER, FAMILY_PARENT_DAUGHTER, daughterPersonCodes);
		}
		
		public void assertPersonRelationship(String person1Code,String role1Code,String role2Code,String[] expectedPersonCodes){
			__assertPersonRelationship__(person1Code, role1Code, role2Code, expectedPersonCodes,Boolean.TRUE);
			if(expectedPersonCodes!=null)
				for(String expectedPersonCode : expectedPersonCodes)
					__assertPersonRelationship__(expectedPersonCode, role2Code, role1Code, new String[]{person1Code},Boolean.FALSE);
		}
		
		private void __assertPersonRelationship__(String person1Code,String role1Code,String role2Code,String[] expectedPersonCodes,Boolean assertCount){
			Person person1 = inject(PersonDao.class).read(person1Code);
			PersonRelationshipTypeRole role1 = inject(PersonRelationshipTypeRoleDao.class).read(role1Code);
			PersonRelationshipTypeRole role2 = inject(PersonRelationshipTypeRoleDao.class).read(role2Code);
			Collection<PersonRelationship> personRelationships = inject(PersonRelationshipDao.class).readByPersonByRoleByOppositeRole(person1, role1 , role2);
			
			if(Boolean.TRUE.equals(assertCount)){
				Integer count = inject(PersonRelationshipDao.class).countByPersonByRoleByOppositeRole(person1, role1, role2).intValue();
				assertEquals("Number of "+role2Code+" of "+person1Code, expectedPersonCodes==null ? 0 : expectedPersonCodes.length, personRelationships.size());
				assertEquals("Database count "+count+" is not equals to list size "+personRelationships.size(), count, personRelationships.size());
			}
			
			if(expectedPersonCodes!=null){
				Set<String> codes = new HashSet<>();
				for(Person person : inject(PersonRelationshipBusiness.class).getRelatedPersons(personRelationships, person1))
					codes.add(person.getCode());
				for(String code : expectedPersonCodes)
					assertThat(code+" is not "+role2Code+" of "+person1Code, codes.contains(code));	
			}
		}
		
		public void assertElectronicMail(String personCode,String email){
			Collection<ElectronicMail> electronicMails = commonUtils.castCollection(
					inject(ElectronicMailDao.class).readByCollection(inject(PersonDao.class).read(personCode).getContactCollection()),ElectronicMail.class);
			assertEquals("Electronic mail", email, electronicMails.isEmpty() ? Constant.EMPTY_STRING : electronicMails.iterator().next().getAddress());
		}
		
		public <T extends AbstractIdentifiable> void assertWhereExistencePeriodFromDateIsLessThanCount(final Class<T> aClass,final String code,Integer count){
			T identifiable = getBusiness(aClass).find(code);
			Collection<T> collection = getBusiness(aClass).findWhereExistencePeriodFromDateIsLessThan(identifiable);
			Long dbCount = getBusiness(aClass).countWhereExistencePeriodFromDateIsLessThan(identifiable);
			System.out.println(toString(identifiable, EXISTENCE_PERIOD_FROM_DATE_IS_LESS_THAN)+" , Childrens - collection size = "+collection.size()+" , count from db = "+dbCount);
			assertEquals("collection and count", dbCount.intValue(),collection.size());
			assertEquals("Collection size", count, collection.size());
			assertEquals("count", count, dbCount.intValue());
		}
		
		public <T extends AbstractIdentifiable> void assertFirstWhereExistencePeriodFromDateIsLessThan(final Class<T> aClass,final String code,String firstPreviousCode,Integer numberOfPrevious){
			T identifiable = read(aClass, code);
			T previous = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).readFirstWhereExistencePeriodFromDateIsLessThan(identifiable);
			String name = toString(identifiable, EXISTENCE_PERIOD_FROM_DATE_IS_LESS_THAN);
			assertEquals("Number of previous of "+name, numberOfPrevious, getPersistence(aClass).countWhereExistencePeriodFromDateIsLessThan(identifiable).intValue());
			if(previous==null){
				assertThat("No previous found for "+name, previous==null && StringUtils.isBlank(firstPreviousCode));
			}else{
				T firstPrevious = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(firstPreviousCode);
				assertEquals("Previous of "+name+" is "+firstPreviousCode+"("+firstPrevious.getBirthDate()+")", previous, firstPrevious);	
			}
			
		}
		
		public <T extends AbstractIdentifiable> void assertOrderBasedOnExistencePeriodFromDate(final Class<T> aClass,Boolean firstIsRoot,final String...codes){
			if(Boolean.TRUE.equals(firstIsRoot)){
				assertFirstWhereExistencePeriodFromDateIsLessThan(aClass, codes[0], null,0);
			}
			
			if(codes!=null && codes.length > 1)
				for(int i = 0 ; i < codes.length - 1 ; i++){
					String code = codes[i+1];
					assertFirstWhereExistencePeriodFromDateIsLessThan(aClass, code, codes[i],i+1);
				}
		}
		
		public <T extends AbstractIdentifiable> void assertOrderBasedOnExistencePeriodFromDate(final Class<T> aClass,final String...codes){
			assertOrderBasedOnExistencePeriodFromDate(aClass, Boolean.TRUE, codes);
		}
		
		
		
		/**/
		
		public <T extends AbstractIdentifiable> void crud(final Class<T> aClass,T instance,Object[][] values){
			create(instance);
	    	String code = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(instance.getIdentifier()).getCode();
	    	read(aClass,code);
	    	update(aClass,code, values);    	
	    	deleteByCode(aClass,code);
	    	clean();
	    }
		
		/**/
		
		protected <T extends AbstractIdentifiable> TypedBusiness<T> getBusiness(Class<T> aClass) {
			return inject(BusinessInterfaceLocator.class).injectTyped(aClass);
		}
		
		protected <T extends AbstractIdentifiable> TypedDao<T> getPersistence(Class<T> aClass) {
			return inject(PersistenceInterfaceLocator.class).injectTyped(aClass);
		} 
		
		/**/
		
		protected String toString(AbstractIdentifiable identifiable,Integer actionIdentifier){
			if(actionIdentifier==EXISTENCE_PERIOD_FROM_DATE_IS_LESS_THAN)
				return identifiable.getCode()+"("+Constant.DATE_TIME_FORMATTER.format(identifiable.getBirthDate())+")";
			return null;
		}
		
		/**/
		
		protected static final Integer EXISTENCE_PERIOD_FROM_DATE_IS_LESS_THAN = 0;
	}

	public TestCase instanciateTestCase(AbstractBusinessTestHelper helper){
		TestCase testCase = new TestCase(helper);
		
		return testCase;
	}
	
	public TestCase instanciateTestCase(){
		return instanciateTestCase(this);
	}
}
