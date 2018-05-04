package org.cyk.system.root.business.impl.__test__;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.WordUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.report.ReportBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineAlphabetBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineStateBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
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
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineFinalState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineTransition;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementAction;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineAlphabetDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.ClassRepository.ClassField;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.NumberHelper;
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
    	//write(file, StringUtils.defaultIfBlank(new FileNameNormaliser.Adapter.Default().setInput(file.getName()).execute(), "file")+System.currentTimeMillis());
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
		/*MovementCollection movementCollection = inject(MovementCollectionBusiness.class).find(movementCollectionCode);
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
    	}*/
    	return null;
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
		/*MovementCollection movementCollection = inject(MovementCollectionBusiness.class).findByGlobalIdentifierCode(movementCollectionCode);
		BigDecimal value = Boolean.TRUE.equals(incrementAction) 
				? inject(IntervalBusiness.class).findLowestGreatestValue(movementCollection.getType().getInterval()).add(BigDecimal.ONE) 
				: inject(IntervalBusiness.class).findGreatestLowestValue(movementCollection.getType().getInterval()).subtract(BigDecimal.ONE);
		if(value==null)
			return;
		createMovement(movementCollectionCode,value.toString(), null,getThrowableMessage(movementCollectionCode, isIncrementAction(value.toString()),3));*/
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
	
	public org.cyk.system.root.business.impl.__test__.TestCase instanciateTestCase(AbstractBusinessTestHelper helper){
		org.cyk.system.root.business.impl.__test__.TestCase testCase = (org.cyk.system.root.business.impl.__test__.TestCase) ClassHelper.getInstance().instanciateOne(org.cyk.utility.common.test.TestCase.class);
		return testCase;
	}
	
	public org.cyk.system.root.business.impl.__test__.TestCase instanciateTestCase(){
		return instanciateTestCase(this);
	}
}
