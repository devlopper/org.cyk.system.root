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
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.Entity;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.WordUtils;
import org.cyk.system.OrgCykSystemPackage;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineAlphabetBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineStateBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
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
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineFinalState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineTransition;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.api.geography.ElectronicMailAddressDao;
import org.cyk.system.root.persistence.api.geography.PhoneNumberDao;
import org.cyk.system.root.persistence.api.globalidentification.GlobalIdentifierDao;
import org.cyk.system.root.persistence.api.mathematics.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MovementDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineAlphabetDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateDao;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeRoleDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.ClassRepository.ClassField;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.file.FileNameNormaliser;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.cyk.utility.common.test.TestEnvironmentListener;
import org.cyk.utility.common.test.TestEnvironmentListener.Try;
import org.exolab.castor.types.DateTime;
import org.hamcrest.Matcher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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
    		assertThat("Read code "+code+" is null", read!=null);
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
	
	public void doAssertions(Object object,ObjectFieldValues expectedValues){
		for(Entry<ClassField, Object> entry : expectedValues.getValuesMap().entrySet()){
			String message = entry.getKey().toString();
			String expectedValue = (String)entry.getValue();
			Object actualValue = FieldHelper.getInstance().read(object, entry.getKey().getName());
			if(actualValue == null || expectedValue == null)
				assertEquals(message, expectedValue, actualValue);
			else if (String.class.equals(entry.getKey().getField().getType()))
				assertEquals(message, expectedValue, (String)actualValue);	
			else if(BigDecimal.class.equals(entry.getKey().getField().getType()))
				assertBigDecimalEquals(message, expectedValue, (BigDecimal)actualValue);	
			else
				assertEquals(entry.getKey().getField().getType()+" not yet handled", Boolean.TRUE, Boolean.FALSE);
		}
		//listener.assertEquals(message, expected, actual);	
	}
	
	protected static void assertNull(String message,Object object){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.assertNull(message, object);
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
		assertBigDecimalEquals(message,NumberHelper.getInstance().get(BigDecimal.class, expected, null),actual);
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
		/*movementCollection.setCode(code);
		movementCollection.setName(name);
		movementCollection.setValue(value ==null ? null : new BigDecimal(value));
		movementCollection.setInterval(new Interval());
		set(movementCollection.getInterval(),code+"int", code+"intname", low,high);
		
		movementCollection.setIncrementAction(new MovementAction());
		set(movementCollection.getIncrementAction(), code+"inc",incrementActionName, "0", null);
		
		movementCollection.setDecrementAction(new MovementAction());
		set(movementCollection.getDecrementAction(), code+"dec",decrementActionName, "0", null);
		*/
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
	@Deprecated
	public Movement createMovement(String movementCollectionCode,String value,String expectedValue,String expectedThrowableMessage){
		MovementCollection movementCollection = inject(MovementCollectionBusiness.class).find(movementCollectionCode);
    	final Movement movement = null;//inject(MovementBusiness.class).instanciateOne(movementCollection
    			//,StringUtils.startsWith(value, Constant.CHARACTER_MINUS.toString()) ? movementCollection.getDecrementAction():movementCollection.getIncrementAction(), value);
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
	@Deprecated
	public Movement createMovement(String movementCollectionCode,String value,String expectedValue){
		return createMovement(movementCollectionCode,value, expectedValue,null);
	}
	@Deprecated
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
	@Deprecated
	public Movement updateMovement(Movement movement,String value,String expectedValue){
		return updateMovement(movement,value, expectedValue,null);
	}
	@Deprecated
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
	@Deprecated
	public Movement deleteMovement(final Movement movement,String expectedValue){
		return deleteMovement(movement, expectedValue,null);
	}
	@Deprecated
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
	@Deprecated
	public void assertMovementCollection(String code,String expectedValue){
    	assertMovementCollection(inject(MovementCollectionDao.class).read(code), expectedValue);
    }
	@Deprecated
	public void assertMovementCollection(MovementCollection movementCollection,String expectedValue){
    	assertBigDecimalEquals("Collection value",new BigDecimal(expectedValue), movementCollection.getValue());
    }
	@Deprecated
	public void assertMovement(String code,String expectedValue,String expectedCumul,String expectedCollectionValue,Boolean increment,String expectedSupportingDocumentProvider,String expectedSupportingDocumentIdentifier){
    	assertMovement(inject(MovementDao.class).read(code), expectedValue,expectedCumul,expectedCollectionValue, increment, expectedSupportingDocumentProvider, expectedSupportingDocumentIdentifier);
    }
	@Deprecated
	public void assertMovement(String code,String expectedValue,String expectedCumul,String expectedCollectionValue,Boolean increment){
		assertMovement(code, expectedValue,expectedCumul, expectedCollectionValue, increment, null, null);
	}
	@Deprecated
	public void assertMovement(String code,String expectedValue,String expectedCumul,String expectedCollectionValue){
		assertMovement(code, expectedValue,expectedCumul, expectedCollectionValue, null);
	}
	@Deprecated
	public void assertMovement(Movement movement,String expectedValue,String expectedCumul,String expectedCollectionValue,Boolean expectedIncrement,String expectedSupportingDocumentProvider,String expectedSupportingDocumentIdentifier){
    	assertBigDecimalEquals("Movement value not equal",new BigDecimal(expectedValue), movement.getValue());
    	assertBigDecimalEquals("Movement cumul not equal",new BigDecimal(expectedCumul), movement.getCumul());
    	assertEquals("Movement action not equal",expectedIncrement == null ? null : (Boolean.TRUE.equals(expectedIncrement) ? movement.getCollection().getType().getIncrementAction() : movement.getCollection().getType().getDecrementAction()), movement.getAction());
    	//assertEquals("Supporting Document Provider",expectedSupportingDocumentProvider, movement.getSupportingDocumentProvider());
    	//assertEquals("Supporting Document Identifier",expectedSupportingDocumentIdentifier, movement.getSupportingDocumentIdentifier());
    	assertMovementCollection(movement.getCollection().getCode(), expectedCollectionValue);
    }
	@Deprecated
	 public void assertComputedChanges(Movement movement,String previousCumul,String cumul){
    	if(movement.getCollection()==null || movement.getCollection().getValue()==null){
    		assertNull("expected movement previous cumul is not null",previousCumul); 
    		assertNull("actual movement previous cumul is not null",movement.getPreviousCumul());
    		
    		assertNull("expected movement cumul is not null",cumul);
    		assertNull("actual movement cumul is not null",movement.getCumul());
    	}else{
    		assertBigDecimalEquals("movement previous cumul is not equal",new BigDecimal(previousCumul), movement.getPreviousCumul());
    		
    		if(movement.getValue() == null){
    			assertNull("expected movement cumul is not null",cumul);
        		assertNull("actual movement cumul is not null",movement.getCumul());
    		}else{
    			assertBigDecimalEquals("movement cumul is not equal",new BigDecimal(cumul), movement.getCumul());
    		}
    	}
    }
	@Deprecated
	private void assertFiniteStateMachine(FiniteStateMachine machine,String expectedStateCode){
		if(expectedStateCode!=null)
			assertEquals("Current state", expectedStateCode, machine.getCurrentState().getCode());
	}
	
	
	
	/* Exceptions */
	
	private void valueMustNotBeOffThanActionIntervalExtremity(String movementCollectionCode,Boolean incrementAction,Boolean lowExtemity){
		MovementCollection movementCollection = inject(MovementCollectionBusiness.class).find(movementCollectionCode);
		MovementAction action = Boolean.TRUE.equals(incrementAction) ? movementCollection.getType().getIncrementAction() : movementCollection.getType().getDecrementAction();
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
				? inject(IntervalBusiness.class).findLowestGreatestValue(movementCollection.getType().getInterval()).add(BigDecimal.ONE) 
				: inject(IntervalBusiness.class).findGreatestLowestValue(movementCollection.getType().getInterval()).subtract(BigDecimal.ONE);
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
		MovementAction action = Boolean.TRUE.equals(increment) ? movementCollection.getType().getIncrementAction() : movementCollection.getType().getDecrementAction();
		String message = Constant.EMPTY_STRING,movementCollectionName=WordUtils.capitalizeFully(movementCollection.getName()),actionName = WordUtils.capitalizeFully(action.getName());
		if(actionId==0)
			message = String.format("%s doit être supérieur à %s",actionName,action.getInterval().getLow().getValue());
		
		if(actionId==1)
			message = String.format("%s doit être supérieur à %s",actionName,action.getInterval().getLow().getValue());
		
		if(actionId==2)
			message = String.format("%s doit être inférieur à %",actionName,action.getInterval().getHigh().getValue());
		
		if(actionId==3)
			message = String.format("%s doit être entre %s et %s",movementCollectionName 
				,inject(NumberBusiness.class).format(movementCollection.getType().getInterval().getLow().getValue())
				,inject(NumberBusiness.class).format(movementCollection.getType().getInterval().getHigh().getValue()));
		
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
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class TestCase extends org.cyk.utility.common.test.TestHelper.TestCase implements Serializable {

		private static final long serialVersionUID = -6026836126124339547L;

		protected String name;
		protected AbstractBusinessTestHelper helper;
		protected List<AbstractIdentifiable> identifiables;
		protected Boolean cleaned = Boolean.FALSE;
		protected Set<Class<?>> classes=new LinkedHashSet<>();
		protected Map<Class<?>,Long> countAllMap = new HashMap<>();
		
		public TestCase(AbstractBusinessTestHelper helper) {
			super();
			this.helper = helper;
		}
		/*
		public <T> T instanciateOneWithCode(Class<T> aClass,String code){
			T instance = ClassHelper.getInstance().instanciateOne(aClass);
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class, aClass))
				//((AbstractIdentifiable)instance).setCode(code);
				FieldHelper.getInstance().set(instance, (Object)code, FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE));
			return instance;
		}
		*/
		public Movement instanciateOneMovement(String code,String collectionCode){
			Movement movement = instanciateOne(Movement.class, code);
			movement.setCollection(InstanceHelper.getInstance().getByIdentifier(MovementCollection.class, collectionCode,ClassHelper.Listener.IdentifierType.BUSINESS));
			return movement;
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
			if(StringUtils.isNotBlank(identifiable.getCode()) && StringUtils.isBlank(expectedThrowableMessage))
				assertThat("Object to create with code <<"+identifiable.getCode()+">> already exist", dao.read(identifiable.getCode())==null);
			T created = helper.create(identifiable,expectedThrowableMessage);
			if(StringUtils.isBlank(expectedThrowableMessage)){
				created = inject(PersistenceInterfaceLocator.class).injectTypedByObject(identifiable).read(identifiable.getIdentifier());
				assertThat("Object created not found", created!=null);
				add(created);
			}
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
			addIdentifiableClasses();
			addClasses(GlobalIdentifier.class);
			System.out.println("Preparing test case "+name+". #Classes="+classes.size());
			countAll(classes);
			return this;
		}
		
		public void clean(){
			if(Boolean.TRUE.equals(cleaned))
				return;
			System.out.println(StringUtils.repeat("#", 5)+" CLEAN "+StringUtils.repeat("#", 5));
			if(identifiables!=null){
				Collections.reverse(identifiables);
				for(AbstractIdentifiable identifiable : identifiables)
					if(identifiable.getCode()==null)
						helper.delete(identifiable);
					else
						helper.delete(identifiable.getClass(), identifiable.getCode()); //inject(GenericBusiness.class).delete(identifiable);
						
			}
			cleaned = Boolean.TRUE;
			assertCountAll(classes);
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
			Collection<Class<?>> classes = new ArrayList<>();
			for(Class<?> aClass : new ClassHelper.Get.Adapter.Default(OrgCykSystemPackage.class.getPackage())
					.setBaseClass(AbstractIdentifiable.class).addAnnotationClasses(Entity.class).execute()){
				if(!Modifier.isAbstract(aClass.getModifiers()) && !Party.class.equals(aClass)){
					@SuppressWarnings("unchecked")
					TypedDao<AbstractIdentifiable> dao = (TypedDao<AbstractIdentifiable>) inject(PersistenceInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass);
					if(dao!=null)
						classes.add(aClass);	
				}
			}
				
			addClasses(classes);
				
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
		
		public TestCase assertNull(Class<? extends AbstractIdentifiable> aClass,String code){
			AbstractBusinessTestHelper.assertNull(aClass+" with code "+code+" is not null", inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(code));
			return this;
		}
		
		public TestCase assertNotNull(final Class<? extends AbstractIdentifiable> aClass,Collection<String> codes){
			new CollectionHelper.Iterator.Adapter.Default<String>(codes){
				private static final long serialVersionUID = 1L;

				protected void __executeForEach__(String code) {
					AbstractBusinessTestHelper.assertThat(aClass+" with code "+code+" is null", inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(code)!=null);
				}
			}.execute();
			return this;
		}
		
		public TestCase assertNotNull(final Class<? extends AbstractIdentifiable> aClass,String...codes){
			if(ArrayHelper.getInstance().isNotEmpty(codes))
				assertNotNull(aClass, Arrays.asList(codes));
			return this;
		}
		
		/**/
		
		public Person createOnePerson(String code,String firstname,String lastnames,String email){
			Person person = inject(PersonBusiness.class).instanciateOne().setCode(code).setName(firstname).setLastnames(lastnames).addElectronicMail(email);
			if(person.getContactCollection()!=null)
				person.getContactCollection().getItems().setSynchonizationEnabled(Boolean.TRUE);
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
		
		/**/
		
		private void assertList(List<?> collection,List<?> expected){
			assertEquals("collection size not equals", CollectionHelper.getInstance().getSize(expected), CollectionHelper.getInstance().getSize(collection));
			if(collection!=null)
				for(Integer index = 0 ; index < collection.size() ; index++){
					assertEquals("element at position "+index+" not equals", expected.get(index.intValue()), collection.get(index.intValue()));
				}
		}
		
		public void assertFieldValueEquals(Class<? extends AbstractIdentifiable> aClass,String code,Object...objects){
			assertFieldValueEquals(read(aClass, code), objects);
		}
		
		public void assertFieldValueEquals(Object instance,Object...objects){
			if(ArrayHelper.getInstance().isNotEmpty(objects)){
				for(Integer index = 0 ; index < objects.length - 2; index = index + 2){
					assertEquals("not equal", objects[index+1], FieldHelper.getInstance().read(instance, (String)objects[index]));
				}
			}
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
			Collection<ElectronicMailAddress> electronicMailAddresses = commonUtils.castCollection(
					inject(ElectronicMailAddressDao.class).readByCollection(inject(PersonDao.class).read(personCode).getContactCollection()),ElectronicMailAddress.class);
			assertEquals("Electronic mail", email, electronicMailAddresses.isEmpty() ? Constant.EMPTY_STRING : electronicMailAddresses.iterator().next().getAddress());
		}
		
		public void assertContactCollectionElectronicMails(String contactCollectionCode,String[] electronicMailAddresses){
			ContactCollection contactCollection = read(ContactCollection.class, contactCollectionCode);
			Collection<ElectronicMailAddress> electronicMails = CollectionHelper.getInstance().cast(ElectronicMailAddress.class
					,inject(ElectronicMailAddressDao.class).readByCollection(contactCollection));
			assertList(CollectionHelper.getInstance().createList(MethodHelper.getInstance().callGet(electronicMails, String.class, ElectronicMailAddress.FIELD_ADDRESS))
					, ArrayHelper.getInstance().isEmpty(electronicMailAddresses) ? new ArrayList<>() : Arrays.asList(electronicMailAddresses));
		}
		
		public void assertContactCollectionPhoneNumbers(String contactCollectionCode,String[] phoneNumberValues){
			ContactCollection contactCollection = read(ContactCollection.class, contactCollectionCode);
			Collection<PhoneNumber> phoneNumbers = CollectionHelper.getInstance().cast(PhoneNumber.class
					,inject(PhoneNumberDao.class).readByCollection(contactCollection));
			assertList(CollectionHelper.getInstance().createList(MethodHelper.getInstance().callGet(phoneNumbers, String.class, PhoneNumber.FIELD_NUMBER))
					, ArrayHelper.getInstance().isEmpty(phoneNumberValues) ? new ArrayList<>() : Arrays.asList(phoneNumberValues));
		}
		
		public <T extends AbstractIdentifiable> void assertWhereExistencePeriodFromDateIsLessThanCount(final Class<T> aClass,final String code,Integer count){
			T identifiable = getBusiness(aClass).find(code);
			assertWhereExistencePeriodFromDateIsLessThanCount(aClass, identifiable, count);
		}
		
		public <T extends AbstractIdentifiable> void assertWhereExistencePeriodFromDateIsLessThanCount(Class<T> aClass,T identifiable,Integer count){
			Collection<T> collection = getBusiness(aClass).findWhereExistencePeriodFromDateIsLessThan(identifiable);
			Long dbCount = getBusiness(aClass).countWhereExistencePeriodFromDateIsLessThan(identifiable);
			//System.out.println(toString(identifiable, EXISTENCE_PERIOD_FROM_DATE_IS_LESS_THAN)+" , Childrens - collection size = "+collection.size()+" , count from db = "+dbCount);
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
		
		public <T extends AbstractIdentifiable> TestCase assertIdentifiable(Class<T> identifiableClass,String code,Map<String,Object> fieldMap){
			T identifiable = read(identifiableClass, code);
			if(identifiable!=null && fieldMap!=null){
				for(Entry<String, Object> entry : fieldMap.entrySet()){
					Object value = FieldHelper.getInstance().read(identifiable, entry.getKey());
					String message = identifiable.getClass().getSimpleName()+" "+entry.getKey()+" is not correct";
					if(value==null)
						assertThat(message, entry.getValue()==null);
					else{
						assertEquals(message,entry.getValue(), value);
					}
				}
			}
			return this;
		}
		
		public <T extends AbstractIdentifiable> TestCase assertIdentifiable(Class<T> identifiableClass,String code,String expectedName){
			Map<String,Object> map = new LinkedHashMap<>();
			map.put(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME), expectedName);
			return assertIdentifiable(identifiableClass, code, map);
		}
		
		public TestCase assertPerson(String code,String expectedName,String expectedLastnames,String expectedSexCode,String expectedDateOfBirth,String expectedPlaceOfBirth){
			Person person = read(Person.class,code);
			assertIdentifiable(Person.class, code, expectedName);
			Map<String,Object> map = new LinkedHashMap<>();
			map.put(Person.FIELD_LASTNAMES, expectedLastnames);
			assertEquals("sex is not equals", read(Sex.class, expectedSexCode), person.getSex());
			Date d1 = new TimeHelper.Builder.String.Adapter.Default(expectedDateOfBirth).execute();
			
			assertEquals("year of birth is not equals", new DateTime(d1).getYear(), new DateTime(person.getBirthDate()).getYear());
			assertEquals("month of birth is not equals", new DateTime(d1).getMonth(), new DateTime(person.getBirthDate()).getMonth());
			assertEquals("day of birth is not equals", new DateTime(d1).getDay(), new DateTime(person.getBirthDate()).getDay());
			assertEquals("place of birth is not equals", expectedPlaceOfBirth, person.getBirthLocation() == null ? null : person.getBirthLocation().getOtherDetails());
			return assertIdentifiable(Person.class, code, map);
		}
		
		public <T extends AbstractIdentifiable> TestCase assertPersonRelarionship(String person1Code,String person1RoleCode,String person2RoleCode,String expectedPerson2Code){
			PersonRelationship personRelationship = inject(PersonRelationshipDao.class).readByPersonByRoleByOppositePerson(read(Person.class, person1Code)
					, read(PersonRelationshipTypeRole.class,person1RoleCode), read(Person.class,person2RoleCode));
			assertThat("relation ship does not exist", personRelationship!=null);
			assertEquals("opposite role code is not correct", expectedPerson2Code, person1Code.equals(personRelationship.getExtremity1().getRole().getCode()) 
					? personRelationship.getExtremity2().getRole().getCode() : personRelationship.getExtremity1().getRole().getCode());
			return this;
		}
		
		public TestCase assertNestedSet(String setCode,String expectedRootCode,Long expectedChildrenCount){
			NestedSet set = read(NestedSet.class, setCode);
			assertEquals("root is not equal", StringUtils.isBlank(expectedRootCode) ? null : read(NestedSetNode.class, expectedRootCode), set.getRoot());
			assertEquals("children count is not equal", expectedChildrenCount, inject(NestedSetNodeDao.class).countBySet(set));
			return this;
		}
		
		public TestCase assertNestedSetNode(String nodeCode,String expectedSetCode,String expectedParentCode,Integer expectedLeftIndex,Integer expectedRightIndex,Long expectedDirectChildrenCount,Long expectedChildrenCount){
			NestedSetNode node = read(NestedSetNode.class, nodeCode);
			assertEquals("set is not equal", StringUtils.isBlank(expectedSetCode) ? null : read(NestedSet.class, expectedSetCode), node.getSet());
			assertEquals("parent is not equal", StringUtils.isBlank(expectedParentCode) ? null : read(NestedSetNode.class, expectedParentCode), node.getParent());
			assertEquals("left index is not equal", expectedLeftIndex, node.getLeftIndex());
			assertEquals("right index is not equal", expectedRightIndex, node.getRightIndex());
			assertEquals("direct children count is not equal", expectedDirectChildrenCount, inject(NestedSetNodeDao.class).countDirectChildrenByParent(node));
			assertEquals("children count is not equal", expectedChildrenCount, inject(NestedSetNodeDao.class).countByParent(node));
			return this;
		}
		
		public <T extends AbstractIdentifiable> TestCase assertParents(Class<T> aClass,String code,Integer levelLimitIndex,String...parentsCodes){
			T identifiable = read(aClass, code);
	    	inject(BusinessInterfaceLocator.class).injectTyped(aClass).setParents(identifiable,levelLimitIndex);
	    	List<AbstractIdentifiable> parents = new ArrayList<>();
	    	if(ArrayHelper.getInstance().isNotEmpty(parentsCodes))
	    		for(String index : parentsCodes)
	    			parents.add(read(aClass, index));
	    	assertList(parents, (List<AbstractIdentifiable>)identifiable.getParents());
	    	return this;
		}
		
		public <T extends AbstractIdentifiable> TestCase assertParents(Class<T> aClass,String code,String...parentsCodes){
			return assertParents(aClass, code, null, parentsCodes);
		}
		
		public <T extends AbstractCollection<I>,I extends AbstractCollectionItem<T>> void assertCollection(Class<T> aClass,Class<I> itemClass,String collectionCode,String expectedNumberOfItem){
			assertCollection(aClass, itemClass, read(aClass, collectionCode), expectedNumberOfItem);
		}
		
		@SuppressWarnings("unchecked")
		public <T extends AbstractCollection<I>,I extends AbstractCollectionItem<T>> TestCase assertCollection(Class<T> aClass,Class<I> itemClass,T collection,String expectedNumberOfItem){
			assertEquals("number of "+itemClass.getSimpleName()+" is not equal", NumberHelper.getInstance().get(Long.class, expectedNumberOfItem), ((AbstractCollectionItemDao<I, T>)getPersistence(itemClass)).countByCollection(collection));
			return this;
		}
		
		public TestCase assertMovementCollection(String code,String expectedValue,String expectedNumberOfMovement){
	    	assertMovementCollection(inject(MovementCollectionDao.class).read(code), expectedValue,expectedNumberOfMovement);
	    	return this;
	    }
		
		public TestCase assertMovementCollection(MovementCollection movementCollection,String expectedValue,String expectedNumberOfMovement){
	    	assertBigDecimalEquals("Collection value",NumberHelper.getInstance().get(BigDecimal.class, expectedValue, null), movementCollection.getValue());
	    	assertCollection(MovementCollection.class, Movement.class, movementCollection, expectedNumberOfMovement);
	    	return this;
	    }
		
		public TestCase assertMovement(String code,String expectedValue,String expectedCumul,Boolean increment,String expectedSupportingDocumentProvider,String expectedSupportingDocumentIdentifier){
	    	assertMovement(read(Movement.class, code), expectedValue,expectedCumul, increment, expectedSupportingDocumentProvider, expectedSupportingDocumentIdentifier);
	    	return this;
	    }
		
		public TestCase assertMovement(String code,String expectedValue,String expectedCumul,Boolean increment){
			assertMovement(code, expectedValue,expectedCumul, increment, null, null);
			return this;
		}
		
		public TestCase assertMovement(String code,String expectedValue,String expectedCumul){
			assertMovement(code, expectedValue,expectedCumul, null);
			return this;
		}
		
		public TestCase assertMovement(Movement movement,String expectedValue,String expectedCumul,Boolean expectedIncrement,String expectedSupportingDocumentProvider,String expectedSupportingDocumentIdentifier){
	    	assertBigDecimalEquals("Movement value not equal",new BigDecimal(expectedValue), movement.getValue());
	    	assertBigDecimalEquals("Movement cumul not equal",new BigDecimal(expectedCumul), movement.getCumul());
	    	assertEquals("Movement action not equal",expectedIncrement == null ? null : (Boolean.TRUE.equals(expectedIncrement) ? movement.getCollection().getType().getIncrementAction() : movement.getCollection().getType().getDecrementAction()), movement.getAction());
	    	//assertEquals("Supporting Document Provider",expectedSupportingDocumentProvider, movement.getSupportingDocumentProvider());
	    	//assertEquals("Supporting Document Identifier",expectedSupportingDocumentIdentifier, movement.getSupportingDocumentIdentifier());
	    	return this;
	    }
		
		public TestCase assertMovements(MovementCollection movementCollection,Collection<String[]> movementsArray){
			Movement.Filter filter = new Movement.Filter();
			filter.addMaster(movementCollection);
			final Collection<Movement> movements = inject(MovementDao.class).readByFilter(filter, null);
			//System.out.println("AbstractBusinessTestHelper.TestCase.assertMovements()");
			//for(Movement m :movements)
			//	System.out.println(m.getBirthDate()+" : "+m.getValue()+" : "+m.getCumul());
			
			new CollectionHelper.Iterator.Adapter.Default<String[]>(movementsArray){
				private static final long serialVersionUID = 1L;

				protected void __executeForEach__(String[] array) {
					if(ArrayHelper.getInstance().isNotEmpty(array)){
						Integer index = NumberHelper.getInstance().getInteger(array[0], 0);
						Movement movement = CollectionHelper.getInstance().getElementAt(movements, index);
						
						assertMovement(movement, array[1], array[2], array[3] == null ? null : Boolean.parseBoolean(array[3]), null, null);	
					}
				}
			}.execute();
			return this;
		}
		
		public TestCase assertMovements(String collectionCode,Collection<String[]> movementsArray){
			assertMovements(read(MovementCollection.class, collectionCode), movementsArray);
			return this;
		}
		
		public TestCase assertMovements(MovementCollection movementCollection,String[]...movementsArrays){
			if(ArrayHelper.getInstance().isNotEmpty(movementsArrays))
				assertMovements(movementCollection, Arrays.asList(movementsArrays));
			return this;
		}
		
		public TestCase assertMovements(String movementCollectionCode,String[]...movementsArrays){
			assertMovements(read(MovementCollection.class, movementCollectionCode), movementsArrays);
			return this;
		}
		
		public TestCase assertCreateMovements(Date identifiablePeriodBirthDate,Date identifiablePeriodDeathDate,Object[][] arrays){
	    	String movementCollectionCode = getRandomHelper().getAlphabetic(5);
	    	create(instanciateOne(MovementCollection.class,movementCollectionCode).setValue(BigDecimal.ZERO));
	    	
	    	IdentifiablePeriod identifiablePeriod = instanciateOneWithRandomIdentifier(IdentifiablePeriod.class)
	    			.setBirthDate(identifiablePeriodBirthDate).setDeathDate(identifiablePeriodDeathDate);
			create(identifiablePeriod);
			
			for(Object[] array : arrays) {
				Movement movement = instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode)
		    			.setActionFromIncrementation(array[0] == null ? null : Boolean.parseBoolean((String)array[0]))
		    			.setIdentifiablePeriod(identifiablePeriod).setValue(NumberHelper.getInstance().get(BigDecimal.class, array[2],null));
				if(array[1]!=null)
					movement.setBirthDate((Date)array[1]);
				create(movement);
		    	
		    	assertMovementCollection(movementCollectionCode,(String)array[3], (String)array[4]);
		    	assertMovements(movementCollectionCode, (String[][])array[5]);
			}
			
			return this;
	    }
		
		 public TestCase assertComputedChanges(Movement movement,String previousCumul,String cumul){
	    	if(movement.getCollection()==null || movement.getCollection().getValue()==null){
	    		AbstractBusinessTestHelper.assertNull("expected movement previous cumul is not null",previousCumul); 
	    		AbstractBusinessTestHelper.assertNull("actual movement previous cumul is not null",movement.getPreviousCumul());
	    		
	    		AbstractBusinessTestHelper.assertNull("expected movement cumul is not null",cumul);
	    		AbstractBusinessTestHelper.assertNull("actual movement cumul is not null",movement.getCumul());
	    	}else{
	    		assertBigDecimalEquals("movement previous cumul is not equal",new BigDecimal(previousCumul), movement.getPreviousCumul());
	    		
	    		if(movement.getValue() == null){
	    			AbstractBusinessTestHelper.assertNull("expected movement cumul is not null",cumul);
	    			AbstractBusinessTestHelper.assertNull("actual movement cumul is not null",movement.getCumul());
	    		}else{
	    			assertBigDecimalEquals("movement cumul is not equal",new BigDecimal(cumul), movement.getCumul());
	    		}
	    	}
	    	return this;
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
		
		public TestCase deleteAll(Collection<Class<?>> classes){
			new CollectionHelper.Iterator.Adapter.Default<Class<?>>((Collection<Class<?>>) classes){
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("unchecked")
				protected void __executeForEach__(java.lang.Class<?> aClass) {
					Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
					identifiables.addAll(getPersistence((Class<AbstractIdentifiable>)aClass).readAll());
					inject(GenericBusiness.class).delete(identifiables);
				}
			}.execute();
			return this;
		}
		
		public TestCase deleteAll(Class<?>...classes){
			if(ArrayHelper.getInstance().isNotEmpty(classes))
				deleteAll(Arrays.asList(classes));
			return this;
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
		TestCase testCase = (TestCase) ClassHelper.getInstance().instanciateOne(org.cyk.utility.common.test.TestHelper.TestCase.class);
		testCase.setHelper(helper);
		return testCase;
	}
	
	public TestCase instanciateTestCase(){
		return instanciateTestCase(this);
	}
}
