package org.cyk.system.root.business.impl;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.geography.LocationTypeBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachine;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineAlphabet;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineFinalState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineState;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineTransition;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.geography.CountryDao;
import org.cyk.system.root.persistence.api.geography.PhoneNumberTypeDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineAlphabetDao;
import org.cyk.system.root.persistence.api.mathematics.machine.FiniteStateMachineStateDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.computation.ArithmeticOperator;
import org.cyk.utility.common.database.DatabaseUtils;
import org.cyk.utility.common.database.DatabaseUtils.CreateParameters;
import org.cyk.utility.common.database.DatabaseUtils.DropParameters;
import org.cyk.utility.common.file.ExcelSheetReader;

import lombok.Getter;
import lombok.Setter;

@Singleton
public class RootDataProducerHelper extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 2282674526022995453L;
	
	private static RootDataProducerHelper INSTANCE;
	
	@Inject private GenericBusiness genericBusiness;
	@Inject private IntervalCollectionBusiness intervalCollectionBusiness;
	@Inject private FileBusiness fileBusiness;
	@Inject private GenericDao genericDao;
	@Inject private FiniteStateMachineStateDao finiteStateMachineStateDao;
	@Inject private FiniteStateMachineAlphabetDao finiteStateMachineAlphabetDao;
	@Inject private DatabaseUtils databaseUtils;
	
	@Getter private Package basePackage;
	private Deque<Package> basePackageQueue = new ArrayDeque<>();
	private Boolean basePackageQueueingEnabled = Boolean.FALSE;
	
	@Getter @Setter private Collection<UniformResourceLocator> uniformResourceLocators;
	@Getter @Setter private Collection<RoleUniformResourceLocator> roleUniformResourceLocators;
	@Getter @Setter private Collection<UserAccount> userAccounts;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	//TODO those are instance creation methods. They must be move to their corresponding business interface
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> T create(T object){
		return (T) genericBusiness.create(object);
	}
	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> T update(T object){
		return (T) genericBusiness.update(object);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractIdentifiable> void createMany(T...objects){
		if(objects!=null){
			Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
			for(T t : objects)
				identifiables.add(t);
			genericBusiness.create(identifiables);
		}
	}
	
	@SuppressWarnings("unchecked") @Deprecated
	public <T extends AbstractEnumeration> T createEnumeration(Class<T> aClass,String code,String name){
		T data = newInstance(aClass);
		data.setCode(code);
		data.setName(name);
		for(Listener listener : Listener.COLLECTION)
			listener.set(data);
		return (T) genericBusiness.create(data);
	}
	
	@SuppressWarnings("unchecked") @Deprecated
	public <T extends AbstractEnumeration> T updateEnumeration(Class<T> aClass,String code,String name){
		T data = getEnumeration(aClass, code);
		data.setName(name);
		for(Listener listener : Listener.COLLECTION)
			listener.set(data);
		return (T) genericBusiness.update(data);
	}
	public <T extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<T>> T updateCollectionItem(Class<T> aClass,Class<COLLECTION> collectionClass,String collectionCode,String code,String name){
		AbstractCollection<T> collection = getEnumeration(collectionClass, collectionCode);
		code = RootConstant.Code.generate(collection, code);
		return updateEnumeration(aClass, code, name);
	}
	
	public <T extends AbstractEnumeration> T createEnumeration(Class<T> aClass,String name){
		return createEnumeration(aClass, getCode(name), name);
	}
	
	public String getCode(String name){
		return StringUtils.remove(name, Constant.CHARACTER_SPACE);
	}
	
	public <T extends AbstractEnumeration> void createEnumerations(Class<T> aClass,Object[] values){
		Collection<AbstractIdentifiable> collection = new ArrayList<>();
		for(Object value : values){
			T data = newInstance(aClass);
			data.setCode(getCode((String)value));
			data.setName((String)value);
			for(Listener listener : Listener.COLLECTION)
				listener.set(data);
			collection.add(data);
		}
		genericBusiness.create(collection);
	}
	
	public IntervalCollection createIntervalCollection(String code,String[][] values,String codeSeparator,Boolean create){
		IntervalCollection collection = new IntervalCollection(code);
		for(String[] v : values){
			collection.getCollection().add(new Interval(collection,StringUtils.isBlank(codeSeparator)?v[0]:(code+codeSeparator+v[0])
					,v[1],new BigDecimal(v[2]),new BigDecimal(v[3])));
		}
		if(Boolean.TRUE.equals(create))
			return intervalCollectionBusiness.create(collection);
		return collection;
	}
	public IntervalCollection createIntervalCollection(String code,String[][] values,String codeSeparator){
		return createIntervalCollection(code,values,codeSeparator, Boolean.TRUE);
	}
	public IntervalCollection createIntervalCollection(String code,String[][] values){
		return createIntervalCollection(code,values,null);
	}
	
	public Interval createInterval(IntervalCollection collection,String code,String name,String low,String high){
		Interval interval = new Interval(collection, code, name, commonUtils.getBigDecimal(low), commonUtils.getBigDecimal(high));
		
		return interval;
	}
	
	/*public MetricCollection createMetricCollection(String code,String name,MetricValueType metricValueType,MetricValueInputted metricValueInputted
			,Byte numberOfDecimalAfterDot,String[] items,String[][] intervals){
		MetricCollection metricCollection = new MetricCollection(code,name);
		metricCollection.setValueInputted(metricValueInputted);
		metricCollection.setValueType(metricValueType);
		for(int i=0;i<items.length;i++){
			metricCollection.addItem(code+"_"+i+"",items[i]);
		}
		
		metricCollection.setValueIntervalCollection(new IntervalCollection(code+"_METRIC_IC"));
		metricCollection.getValueIntervalCollection().setNumberOfDecimalAfterDot(numberOfDecimalAfterDot);
		for(String[] interval : intervals){
			metricCollection.getValueIntervalCollection().addItem(interval[0], interval[1], interval[2], interval[3]);
		}
		create(metricCollection);
		return metricCollection;
	}*/
	
	@Deprecated
	public File createFile(Package basePackage,String relativePath,String code,String name){
		basePackage = PersistDataListener.Adapter.process(File.class, code,PersistDataListener.BASE_PACKAGE, basePackage);
		relativePath = PersistDataListener.Adapter.process(File.class, code, PersistDataListener.RELATIVE_PATH, relativePath);
		
		File file = null;
		if(StringUtils.isNotBlank(relativePath)){
			if(StringUtils.isBlank(name))
				name = FilenameUtils.getName(relativePath);
			file = fileBusiness.process(getResourceAsBytes(basePackage,relativePath),name);
			if(StringUtils.isNotBlank(code))
				file.setCode(code);
			file = fileBusiness.create(file);
		}else
			;//System.out.println("Relative path is blank : "+relativePath+" : "+code+" , "+name+" , "+basePackage);
		return file;
	}
	@Deprecated
	public File createFile(Package basePackage,String relativePath,String code){
		String name = FilenameUtils.getName(relativePath);
		return createFile(basePackage, relativePath,code, name);
	}
	
	@Deprecated
	public byte[] getResourceAsBytes(Package basePackage,String relativePath){
    	String path = "/"+StringUtils.replace( (basePackage==null?(this.basePackage==null?this.getClass().getPackage():this.basePackage):basePackage).getName(), ".", "/")+"/";
    	path += relativePath;
    	try {
    		logDebug("Getting resource as bytes {}", path);
    		return IOUtils.toByteArray(this.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			logError("Cannot get resource as bytes using path {}", path);
			e.printStackTrace();
			return null;
		}
    }
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractEnumeration> T getEnumeration(Class<T> aClass,String code){
		return (T) genericDao.use(aClass).select().where(null,"globalIdentifier.code","code", code,ArithmeticOperator.EQ).one();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractEnumeration> Collection<T> getEnumerations(Class<T> aClass){
		Collection<T> collection = new ArrayList<>();
		for(AbstractIdentifiable identifiable : genericBusiness.use(aClass).find().all())
			collection.add((T) identifiable);
		return collection;
	}
	
	public PhoneNumber addPhoneNumber(ContactCollection collection,Country country,PhoneNumberType type,String number){
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setCollection(collection);
		phoneNumber.setCountry(country);
		phoneNumber.setType(type);
		phoneNumber.setNumber(number);
		if(collection.getPhoneNumbers()==null)
			collection.setPhoneNumbers(new ArrayList<PhoneNumber>());
		collection.getPhoneNumbers().add(phoneNumber);
		return phoneNumber;
	}
	
	public PhoneNumber addPhoneNumber(ContactCollection collection,PhoneNumberType type,String number){
		return addPhoneNumber(collection,inject(CountryDao.class).read(RootConstant.Code.Country.COTE_DIVOIRE), type, number);
	}
	public PhoneNumber addLandPhoneNumber(ContactCollection collection,String number){
		return addPhoneNumber(collection,inject(CountryDao.class).read(RootConstant.Code.Country.COTE_DIVOIRE), inject(PhoneNumberTypeDao.class)
				.read(RootConstant.Code.PhoneNumberType.LAND), number);
	}
	public PhoneNumber addMobilePhoneNumber(ContactCollection collection,String number){
		return addPhoneNumber(collection,inject(CountryDao.class).read(RootConstant.Code.Country.COTE_DIVOIRE), inject(PhoneNumberTypeDao.class)
				.read(RootConstant.Code.PhoneNumberType.MOBILE), number);
	}
	public void addContacts(ContactCollection collection,String[] addresses,String[] landNumbers,String[] mobileNumbers,String[] postalBoxes,String[] emails,String[] websites){
		if(addresses!=null)
			for(String address : addresses){
				Location location = new Location(collection, inject(CountryDao.class).read(RootConstant.Code.Country.COTE_DIVOIRE).getLocality());
				location.setOtherDetails(address);
				location.setType(inject(LocationTypeBusiness.class).find(RootConstant.Code.LocationType.OFFICE));
				if(collection.getLocations()==null)
					collection.setLocations(new ArrayList<Location>());
				collection.getLocations().add(location);
			}
		if(landNumbers!=null)
			for(String number : landNumbers)
				addLandPhoneNumber(collection, number);
		if(mobileNumbers!=null)
			for(String number : mobileNumbers)
				addMobilePhoneNumber(collection, number);
		if(postalBoxes!=null)
			for(String postalbox : postalBoxes){
				PostalBox postalBox = new PostalBox(postalbox);
				if(collection.getPostalBoxs()==null)
					collection.setPostalBoxs(new ArrayList<PostalBox>());
				collection.getPostalBoxs().add(postalBox);
			}
		if(emails!=null)
			for(String email : emails){
				ElectronicMail electronicMail = new ElectronicMail(collection, email);
				if(collection.getElectronicMails()==null)
					collection.setElectronicMails(new ArrayList<ElectronicMail>());
				collection.getElectronicMails().add(electronicMail);
			}
		/*if(websites!=null)
			for(String websitev : websites){
				Website website;
				website = new Website(websitev);
				if(collection.getWebsites()==null)
					collection.setWebsites(new ArrayList<Website>());
				collection.getWebsites().add(website);
			}
		*/
	}
	
	public MovementCollection createMovementCollection(String code,String incrementActionName,String decrementActionName){
		MovementCollection movementCollection = new MovementCollection(code, BigDecimal.ZERO, createInterval(null, code+"int", code+"int", "0", null));
		movementCollection.setIncrementAction(createMovementAction(getCode(incrementActionName), incrementActionName));
		movementCollection.setDecrementAction(createMovementAction(getCode(decrementActionName), decrementActionName));
		return movementCollection;
	}
	
	public MovementAction createMovementAction(String code, String name){
		MovementAction movementAction = new MovementAction(code, name);
		movementAction.setInterval(createInterval(null, code+"int", code+"int", "0", null));
		return movementAction;
	}
	
	public FiniteStateMachine createFiniteStateMachine(String machineCode,String[] alphabetCodes,String[] stateCodes,String initialStateCode,String[] finalStateCodes,String[][] transitions){
		FiniteStateMachine machine = createEnumeration(FiniteStateMachine.class, machineCode);
		
		Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
		for(String code : alphabetCodes){
			FiniteStateMachineAlphabet alphabet = new FiniteStateMachineAlphabet();
			alphabet.setCode(code);
			alphabet.setName(code);
			alphabet.setMachine(machine);
			identifiables.add(alphabet);
		}
		genericBusiness.create(identifiables);
		
		identifiables = new ArrayList<>();
		for(String code : stateCodes){
			FiniteStateMachineState state = new FiniteStateMachineState();
			state.setCode(code);
			state.setName(code);
			state.setMachine(machine);
			identifiables.add(state);
		}
		genericBusiness.create(identifiables);
		
		machine.setInitialState(finiteStateMachineStateDao.readByGlobalIdentifierCode(initialStateCode));
		machine.setCurrentState(machine.getInitialState());
		genericBusiness.update(machine);
		
		identifiables = new ArrayList<>();
		for(String code : finalStateCodes){
			FiniteStateMachineFinalState state = new FiniteStateMachineFinalState();
			state.setState(finiteStateMachineStateDao.readByGlobalIdentifierCode(code));
			identifiables.add(state);
		}
		genericBusiness.create(identifiables);
		
		identifiables = new ArrayList<>();
		for(String[] transitionInfos : transitions){
			FiniteStateMachineTransition transition = new FiniteStateMachineTransition();
			transition.setFromState(finiteStateMachineStateDao.readByGlobalIdentifierCode(transitionInfos[0]));
			transition.setAlphabet(finiteStateMachineAlphabetDao.readByGlobalIdentifierCode(transitionInfos[1]));
			transition.setToState(finiteStateMachineStateDao.readByGlobalIdentifierCode(transitionInfos[2]));
			identifiables.add(transition);
		}
		genericBusiness.create(identifiables);
		
		return machine;
	}
	
	public void createDatabase(){
		CreateParameters parameters = new CreateParameters();
		parameters.setDatabaseName(getDatabaseName());
		try {
			databaseUtils.createDatabase(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void dropDatabase(){
		DropParameters parameters = new DropParameters();
		parameters.setDatabaseName(getDatabaseName());
		try {
			databaseUtils.dropDatabase(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportDatabase(String databaseName,Boolean autoTimeStampAction,String fileSuffix){
		try {
			databaseUtils.exportDatabase(databaseName,autoTimeStampAction,fileSuffix);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void exportDatabase(Boolean autoTimeStampAction,String fileSuffix){
		exportDatabase(getDatabaseName(), autoTimeStampAction, fileSuffix);
	}
	public void exportDatabase(){
		exportDatabase(Boolean.FALSE, Constant.EMPTY_STRING);
	}
	
	private String getDatabaseName(){
		return String.format(DATABASE_NAME_FORMAT,StringUtils.substringBetween(basePackage.getName(), "org.cyk.system.", ".business.impl"));
	}
	
	@SuppressWarnings("unchecked")
	public void instanciateRoleUniformResourceLocator(Collection<Role> roles,Object...uniformResourceLocatorArray){
		for(Role role : roles)
			for(Object object : uniformResourceLocatorArray){
				Collection<UniformResourceLocator> uniformResourceLocators;
				if( object instanceof Collection )
					uniformResourceLocators = (Collection<UniformResourceLocator>) object;
				else if( object instanceof UniformResourceLocator)
					uniformResourceLocators = Arrays.asList((UniformResourceLocator)object);
				else
					uniformResourceLocators = null;
				if(uniformResourceLocators!=null)
					if(roleUniformResourceLocators==null)
						roleUniformResourceLocators = new ArrayList<>();
				for(UniformResourceLocator uniformResourceLocator : uniformResourceLocators){
					Boolean found = Boolean.FALSE;
					/*if(this.uniformResourceLocators!=null)
						for(UniformResourceLocator v : this.uniformResourceLocators)
							if(v.getCode().equals(uniformResourceLocator.getCode())){
								found = Boolean.TRUE;
								break;
							}*/
					if(Boolean.FALSE.equals(found)){
						if(this.uniformResourceLocators==null)
							this.uniformResourceLocators = new ArrayList<>();
						this.uniformResourceLocators.add(uniformResourceLocator);
					}
					roleUniformResourceLocators.add(new RoleUniformResourceLocator(role, uniformResourceLocator));
				}
			}
	}
	
	public void instanciateUserAccounts(Collection<Party> parties, Role... roles) {
		if(this.userAccounts==null)
			this.userAccounts = new ArrayList<>();
		this.userAccounts = inject(UserAccountBusiness.class).instanciateManyFromParties(parties, roles);
	}
	
	public void instanciateUserAccountsFromActors(Collection<? extends AbstractActor> actors, Role... roles) {
		Collection<Party> parties = new ArrayList<>();
		for(AbstractActor actor : actors)
			parties.add(actor.getPerson());
		instanciateUserAccounts(parties, roles);
	}

	@Deprecated
	public ReportTemplate createReportTemplate(String code,String name,Boolean male,String templateRelativeFileName,File headerImage,File backgroundImage,File draftBackgroundImage){
		Package basePackage = PersistDataListener.Adapter.process(File.class, code,PersistDataListener.BASE_PACKAGE, this.basePackage);
		templateRelativeFileName = PersistDataListener.Adapter.process(ReportTemplate.class, code,PersistDataListener.RELATIVE_PATH, templateRelativeFileName);
		
		String fileName = StringUtils.substringAfterLast(templateRelativeFileName, Constant.CHARACTER_SLASH.toString());
		if(StringUtils.isBlank(name))
			name = fileName;
		File file = createFile(basePackage,templateRelativeFileName, fileName);
		
		return create(new ReportTemplate(code,name,male,file,headerImage,backgroundImage,draftBackgroundImage));
	}
	
	public <T extends AbstractIdentifiable> void createFromExcelSheet(Class<?> inputStreamResourceLocation,String woorkbookName,Class<T> aClass){
		try {
			final ExcelSheetReader excelSheetReader = new ExcelSheetReader.Adapter.Default().setWorkbookBytes(inputStreamResourceLocation.getResourceAsStream(woorkbookName))
					.setName(aClass).setFromColumnIndex(0).setFromRowIndex(1);
			
			listenerUtils.getValue(ExcelSheetReader.class, Listener.COLLECTION, new ListenerUtils.ResultMethod<Listener, ExcelSheetReader>() {

				@Override
				public ExcelSheetReader execute(Listener listener) {
					return listener.processExcelSheetReader(excelSheetReader);
				}

				@Override
				public ExcelSheetReader getNullValue() {
					return null;
				}
			});
			
			//ReadExcelSheetArguments readExcelSheetArguments = new ReadExcelSheetArguments(inputStreamResourceLocation.getResourceAsStream(woorkbookName),aClass);
	    	
			List<String[]> list = excelSheetReader.execute(); //commonUtils.readExcelSheet(readExcelSheetArguments);
	    	TypedBusiness<?> business = inject(BusinessInterfaceLocator.class).injectTyped(aClass);
	    	@SuppressWarnings("unchecked")
			Collection<T> collection = (Collection<T>) business.instanciateMany(list);
	    	if(collection==null){
	    		System.out.println("Instanciate many <<"+aClass+">> has return null collection");
	    	}else{
	    		inject(GenericBusiness.class).create(commonUtils.castCollection(collection,AbstractIdentifiable.class));
				System.out.println(aClass.getSimpleName()+" created : "+list.size());	
	    	}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**/
	
	public void setBasePackage(Package basePackage) {
		if(Boolean.TRUE.equals(basePackageQueueingEnabled)){
			if(basePackageQueue.peek().equals(basePackage))
				;
			else
				basePackageQueue.push(this.basePackage);
		}
		__setBasePackage__(basePackage);
	}
	
	public void setToPreviousBasePackage(){
		if(Boolean.TRUE.equals(basePackageQueueingEnabled)){
			if(basePackageQueue.isEmpty())
				return;
			__setBasePackage__(basePackageQueue.pop());
		}else
			;
	}
	
	private void __setBasePackage__(Package basePackage) {
		this.basePackage = basePackage;
		logTrace("Base package set to {}", basePackage);
	}
	
	public static RootDataProducerHelper getInstance() {
		return INSTANCE;
	}

	/**/
	
	public static interface Listener{
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		void set(Object object);
		ExcelSheetReader processExcelSheetReader(ExcelSheetReader excelSheetReader);
		/**/
		
		public static class Adapter extends BeanAdapter implements Listener,Serializable{
			private static final long serialVersionUID = 581887995233346336L;
			@Override
			public void set(Object object) {}
			
			@Override
			public ExcelSheetReader processExcelSheetReader(ExcelSheetReader excelSheetReader) {
				return excelSheetReader;
			}
			
			/**/
			
			public static class Default extends Adapter implements Serializable{
				private static final long serialVersionUID = 581887995233346336L;
		
			}
		}
	}
	
	public static final String DATABASE_NAME_FORMAT = "cyk_%s_db";
	
}
