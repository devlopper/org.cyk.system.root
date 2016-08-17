package org.cyk.system.root.business.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.ClazzBusiness;
import org.cyk.system.root.business.api.ClazzBusiness.ClazzBusinessListener;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.file.FileValidator;
import org.cyk.system.root.business.impl.file.report.AbstractReportRepository;
import org.cyk.system.root.business.impl.party.person.PersonValidator;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Clazz;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.generator.StringValueGenerator;
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.generator.ValueGenerator.GenerateMethod;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleSecuredView;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;
import org.cyk.system.root.persistence.api.party.ApplicationDao;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.StringMethod;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;

import lombok.Getter;
import lombok.Setter;

@Singleton
@Deployment(initialisationType=InitialisationType.EAGER,order=RootBusinessLayer.DEPLOYMENT_ORDER) @Getter
public class RootBusinessLayer extends AbstractBusinessLayer implements Serializable {
 
	public static final int DEPLOYMENT_ORDER = 0;
	private static final long serialVersionUID = 4576531258594638L;
	
	private static RootBusinessLayer INSTANCE;
	
	private Timer alarmTimer;
	
	private final String parameterGenericReportBasedOnDynamicBuilder = "grbodb"; 
	private final String parameterGenericDashBoardReport = "gdbr"; 
	private final String parameterFromDate = "fd"; 
	private final String parameterToDate = "td"; 
	
	//private Role roleAdministrator,roleManager,roleSettingManager,roleSecurityManager,roleUser;
	
    @Inject private PersonValidator personValidator;
    @Inject private FileValidator fileValidator;
    @Inject private RootReportRepository reportRepository;
    
    @Inject private RootBusinessTestHelper rootBusinessTestHelper;
    //@Inject private ApplicationDao applicationDao;
    
    private Application application;
    @Setter private Long applicationIdentifier;
    
    //private Person personAdmin,personGuest;
    
    private static final Collection<Listener> ROOT_BUSINESS_LAYER_LISTENERS = new ArrayList<>();
    
    @SuppressWarnings("unchecked")
	@Override
    protected void initialisation() {
    	INSTANCE = this; 
        super.initialisation();
        ClazzBusiness.LISTENERS.add(new ClazzBusinessListener.Adapter() {
			private static final long serialVersionUID = 4056356640763766384L;
			@Override
			public void doSetUiLabel(Clazz clazz) {
				clazz.setUiLabel(languageBusiness.findText(clazz.getUiLabelId()));
			}
		}); 
        
        BusinessServiceProvider.Identifiable.COLLECTION.add(new BusinessServiceProvider.Identifiable.Adapter.Default<Person>(Person.class){
			private static final long serialVersionUID = 1322416788278558869L;
			@Override
			public Collection<Person> find(DataReadConfiguration configuration) {
				Person.SearchCriteria criteria = new Person.SearchCriteria(configuration.getGlobalFilter());
				criteria.getReadConfig().set(configuration);
				return inject(PersonBusiness.class).findByCriteria(criteria);
			}
			
			@Override
			public Long count(DataReadConfiguration configuration) {
				return inject(PersonBusiness.class).countByCriteria(new Person.SearchCriteria(configuration.getGlobalFilter()));
			}
        });
         
        registerFormatter(MetricValue.class, new AbstractFormatter<MetricValue>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(MetricValue metricValue, ContentType contentType) {
				return inject(MetricValueBusiness.class).format(metricValue);
			}
		});
        registerFormatter(NestedSet.class, new AbstractFormatter<NestedSet>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(NestedSet nestedSet, ContentType contentType) {
				return nestedSet.getIdentifier().toString();
			}
		});
        registerFormatter(NestedSetNode.class, new AbstractFormatter<NestedSetNode>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(NestedSetNode nestedSetNode, ContentType contentType) {
				return nestedSetNode.getLeftIndex()+Constant.CHARACTER_COMA.toString()+nestedSetNode.getRightIndex();
			}
		});
        registerFormatter(GlobalIdentifier.class, new AbstractFormatter<GlobalIdentifier>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(GlobalIdentifier globalIdentifier, ContentType contentType) {
				return globalIdentifier.getIdentifier()+Constant.CHARACTER_SLASH+globalIdentifier.getCode();
			}
		});
        
        registerFormatter(File.class, new AbstractFormatter<File>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(File file, ContentType contentType) {
				return file.getCode()+Constant.CHARACTER_SLASH+file.getName();
			}
		});
        
        registerFormatter(PhoneNumber.class, new AbstractFormatter<PhoneNumber>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(PhoneNumber phoneNumber, ContentType contentType) {
				if(StringUtils.isBlank(phoneNumber.getNumber()))
					return null;
				StringBuilder stringBuilder = new StringBuilder();
				if(phoneNumber.getCountry()!=null)
					stringBuilder.append(Constant.CHARACTER_PLUS+phoneNumber.getCountry().getPhoneNumberCode().toString()+Constant.CHARACTER_SPACE);
				stringBuilder.append(phoneNumber.getNumber());
				return stringBuilder.toString();
			}
		});
        
        UniformResourceLocatorParameter.Builder.Listener.COLLECTION.add(new UniformResourceLocatorParameter.Builder.Listener.Adapter.Default(){
			private static final long serialVersionUID = -6619563566928210717L;
        	@Override
        	public String getValueAsString(Object object) {
        		if(object instanceof Class<?>){
    				if(AbstractIdentifiable.class.isAssignableFrom((Class<?>) object))
    					return inject(ApplicationBusiness.class).findBusinessEntityInfos((Class<? extends AbstractIdentifiable>) object).getIdentifier();
    			}else if(object instanceof AbstractIdentifiable)
    				return ((AbstractIdentifiable)object).getIdentifier().toString();
        		return super.getValueAsString(object);
        	}
        });
        
        //application = applicationDao.select().one();
        
        rootBusinessTestHelper.setReportBusiness(reportBusiness);
        
        applicationBusiness.registerValueGenerator((ValueGenerator<?, ?>) new StringValueGenerator<Party>(
        		ValueGenerator.GLOBAL_IDENTIFIER_CODE_IDENTIFIER,ValueGenerator.GLOBAL_IDENTIFIER_CODE_DESCRIPTION, Party.class));
        
        registerValidator(Person.class, personValidator);
        registerValidator(File.class, fileValidator);
        
        /*
        String systemName = StringUtils.split(this.getClass().getName(), '.')[3];
        registerResourceBundle("org.cyk.system."+systemName+".model.resources.entity", getClass().getClassLoader());
		registerResourceBundle("org.cyk.system."+systemName+".model.resources.message", getClass().getClassLoader());
		registerResourceBundle("org.cyk.system."+systemName+".business.impl.resources.message", getClass().getClassLoader());
        */
        
        ValueGenerator<AbstractIdentifiable,String> globalIdentifierCodeGenerator = (ValueGenerator<AbstractIdentifiable, String>) 
        		inject(ApplicationBusiness.class).findValueGenerator(ValueGenerator.GLOBAL_IDENTIFIER_CODE_IDENTIFIER);
        globalIdentifierCodeGenerator.setMethod(new GlobalIdentifierCodeGenerator());
		
		AbstractIdentifiable.BUILD_GLOBAL_IDENTIFIER_VALUE = new StringMethod<AbstractIdentifiable>() {
			private static final long serialVersionUID = -206221150563679476L;
			@Override
			protected String __execute__(AbstractIdentifiable identifiable) {
				return identifiable.getClass().getSimpleName()+Constant.CHARACTER_UNDESCORE+System.currentTimeMillis()+Constant.CHARACTER_UNDESCORE
						+RandomStringUtils.randomAlphanumeric(10);
			}
		};
		
		AbstractIdentifiable.CREATE_GLOBAL_IDENTIFIER = new AbstractMethod<Object, GlobalIdentifier>() {
			private static final long serialVersionUID = 153358109323471469L;
			@Override
			protected Object __execute__(GlobalIdentifier globalIdentifier) {
				return inject(GlobalIdentifierBusiness.class).create(globalIdentifier);
			}
		};
		
		AbstractIdentifiable.UPDATE_GLOBAL_IDENTIFIER = new AbstractMethod<Object, GlobalIdentifier>() {
			private static final long serialVersionUID = 153358109323471469L;
			@Override
			protected Object __execute__(GlobalIdentifier globalIdentifier) {
				return inject(GlobalIdentifierBusiness.class).update(globalIdentifier);
			}
		};
		
		AbstractIdentifiable.BUILD_GLOBAL_IDENTIFIER_CREATED_BY = new AbstractMethod<Party, AbstractIdentifiable>() {
			private static final long serialVersionUID = 153358109323471469L;
			@Override
			protected Party __execute__(AbstractIdentifiable identifiable) {
				return identifiable.getProcessing().getParty() == null ? application : identifiable.getProcessing().getParty();
			}
		};
		
		AbstractIdentifiable.BUILD_GLOBAL_IDENTIFIER_CREATION_DATE = new AbstractMethod<Date, AbstractIdentifiable>() {
			private static final long serialVersionUID = 153358109323471469L;
			@Override
			protected Date __execute__(AbstractIdentifiable identifiable) {
				return inject(TimeBusiness.class).findUniversalTimeCoordinated();
			}
		};
		
		AbstractIdentifiable.GLOBAL_IDENTIFIER_BUILDABLE = new AbstractMethod<Boolean, AbstractIdentifiable>() {
			private static final long serialVersionUID = 153358109323471469L;
			@Override
			protected Boolean __execute__(AbstractIdentifiable identifiable) {
				return !ArrayUtils.contains(new Class<?>[]{/*Application.class,NestedSet.class,NestedSetNode.class*/}, identifiable.getClass());
			}
		};
    }
    
    public GenericBusiness getGenericBusiness(){
    	return inject(GenericBusiness.class);
    }
    
    public GenericDao getGenericDao(){
    	return inject(GenericDao.class);
    }
    
    @Override
    protected AbstractReportRepository getReportRepository() {
    	return reportRepository;
    }
    
    @Override
    protected void persistData() {
    	geography();
        event();
        time();
        language();
        party();
        security();
        
    }
    
    private void geography(){
        LocalityType continent=new LocalityType(null, LocalityType.CONTINENT, "Continent");
        create(continent);
        LocalityType country=new LocalityType(continent, LocalityType.COUNTRY, "Country");
        create(country);
        LocalityType city=new LocalityType(country, LocalityType.CITY, "City");
        create(city);
        
        Locality afrique;
        
        create(afrique = new Locality(null, continent, "Afrique"));
        create(new Locality(null, continent, "Amerique"));
        create(new Locality(null, continent, "Europe"));
        
        create(new Country(new Locality(afrique, country,Country.COTE_DIVOIRE, "Côte d'Ivoire"),225));
        create(new Locality(afrique, country, "Bénin"));
        
        create(new PhoneNumberType(PhoneNumberType.LAND, "Fixe"));
        create(new PhoneNumberType(PhoneNumberType.MOBILE, "Mobile"));
        
        create(new LocationType(LocationType.HOME, "Domicile"));
        create(new LocationType(LocationType.OFFICE, "Bureau"));
        
    }
    
    private void language(){
    	create(new Language("fr","Francais"));
        create(new Language("en","Anglais"));
        create(new Language("es","Espagnol"));
    }
    
    private void event(){ 
    	/*create(new EventType(EventType.ANNIVERSARY, "Anniversaire", null));
    	create(new EventType(EventType.APPOINTMENT, "Rendez vous", null));
        create(new EventType(EventType.MEETING, "Reunion", null));
        create(new EventType(EventType.REMINDER, "Rappel", null));
        */
        notificationTemplate(NotificationTemplate.ALARM_USER_INTERFACE,"Alarm User Interface Notification Template","alarmUITitle.txt","alarmUIMessage.html");
        notificationTemplate(NotificationTemplate.ALARM_EMAIL,"Alarm Email Notification Template","alarmEmailTitle.txt","alarmEmailMessage.html");
        notificationTemplate(NotificationTemplate.ALARM_SMS,"Alarm Sms Notification Template","alarmSmsTitle.txt","alarmSmsMessage.html");
        
        createEnumeration(EventMissedReason.class,EventMissedReason.DISEASE, "Maladie");
        createEnumeration(EventMissedReason.class,EventMissedReason.LATE, "Retard");
    }
    
    private void notificationTemplate(String code,String name,String titleFileFolder,String titleFileName,String bodyFileFolder,String bodyFileName){
    	NotificationTemplate notificationTemplate = new NotificationTemplate();
        notificationTemplate.setCode(code);
        notificationTemplate.setName(name);
        try {
        	notificationTemplate.setTitle(fileBusiness.process(IOUtils.toByteArray(getClass().getResourceAsStream(titleFileFolder+"/"+titleFileName)), titleFileName));
        	notificationTemplate.setMessage(fileBusiness.process(IOUtils.toByteArray(getClass().getResourceAsStream(bodyFileFolder+"/"+bodyFileName)), bodyFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
        create(notificationTemplate);
    }
    
    private void notificationTemplate(String code,String name,String titleFileName,String bodyFileName){
    	notificationTemplate(code, name, "template", titleFileName, "template", bodyFileName);
    }
    
    private void time(){ 
    	create(new TimeDivisionType(TimeDivisionType.DAY, "Jour",DateUtils.MILLIS_PER_DAY ,Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.WEEK, "Semaine",DateUtils.MILLIS_PER_DAY*7, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.MONTH, "Mois",DateUtils.MILLIS_PER_DAY*30, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.TRIMESTER, "Trimestre",DateUtils.MILLIS_PER_DAY*30*3, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.SEMESTER, "Semestre",DateUtils.MILLIS_PER_DAY*30*6, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.YEAR, "Année",DateUtils.MILLIS_PER_DAY*30*12,Boolean.TRUE));
        
    }
    
    private void party(){
    	createEnumeration(Sex.class,Sex.MALE, "Masculin");
    	createEnumeration(Sex.class,Sex.FEMALE, "Feminin");
    	createEnumeration(MaritalStatus.class,"B", "Celibataire");
    	createEnumeration(MaritalStatus.class,"M", "Marie");
        
        createEnumeration(JobFunction.class,"DJ","Développeur Java");
        createEnumeration(JobFunction.class,"RV","Responsable des ventes");
        createEnumeration(JobFunction.class,"CP","Chargé des projets");
        
        createEnumeration(JobTitle.class,"Directeur");
        createEnumeration(JobTitle.class,"Manager");
        createEnumeration(JobTitle.class,"Conseiller");
           
        createEnumeration(PersonTitle.class,PersonTitle.MISTER, "Mr");
        createEnumeration(PersonTitle.class,PersonTitle.MISS, "Ms");
        createEnumeration(PersonTitle.class,PersonTitle.MADAM, "Mme");
        createEnumeration(PersonTitle.class,PersonTitle.DOCTOR, "Dr");
    }
    
    private void security(){ 
    	//Permission licenceRead = createPermission(permissionBusiness.computeCode(License.class, Crud.READ));
    	
    	createRole(Role.ADMINISTRATOR, "Administrator");
    	createRole(Role.MANAGER, "Manager");
        createRole(Role.SETTING_MANAGER, "Setting Manager");
        createRole(Role.SECURITY_MANAGER, "Security Manager");
        createRole(Role.USER, "User",SHIRO_PRIVATE_FOLDER);
        
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void registerTypedBusinessBean(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> beansMap) {
        /*beansMap.put((Class)Event.class, (TypedBusiness)eventBusiness);
        beansMap.put((Class)EventParty.class, (TypedBusiness)eventPartyBusiness);
        beansMap.put((Class)EventMissed.class, (TypedBusiness)eventMissedBusiness);
        beansMap.put((Class)EventMissedReason.class, (TypedBusiness)eventMissedReasonBusiness);*/
        
        //beansMap.put((Class)Person.class, (TypedBusiness)personBusiness);
        
        //beansMap.put((Class)LocalityType.class, (TypedBusiness)localityTypeBusiness);
        //beansMap.put((Class)Locality.class, (TypedBusiness)localityBusiness);
        //beansMap.put((Class)Country.class, (TypedBusiness)countryBusiness);
        //beansMap.put((Class)ContactCollection.class, (TypedBusiness)contactCollectionBusiness);
        //beansMap.put((Class)ElectronicMail.class, (TypedBusiness)electronicMailBusiness);
        //beansMap.put((Class)PhoneNumber.class, (TypedBusiness)phoneNumberBusiness);
        
        
        //beansMap.put((Class)Tag.class, (TypedBusiness)tagBusiness);
        //beansMap.put((Class)TagIdentifiableGlobalIdentifier.class, (TypedBusiness)tagIdentifiableGlobalIdentifierBusiness);
        //beansMap.put((Class)UserAccount.class, (TypedBusiness)userAccountBusiness);
        //beansMap.put((Class)StringGenerator.class, (TypedBusiness)stringGeneratorBusiness);
        beansMap.put((Class)RoleSecuredView.class, (TypedBusiness)roleSecuredViewBusiness);
        /*beansMap.put((Class)JobTitle.class, (TypedBusiness)jobTitleBusiness);
        beansMap.put((Class)PersonTitle.class, (TypedBusiness)personTitleBusiness);
        beansMap.put((Class)JobFunction.class, (TypedBusiness)jobFunctionBusiness);*/
        //beansMap.put((Class)IntervalCollection.class, (TypedBusiness)intervalCollectionBusiness);
        //beansMap.put((Class)Interval.class, (TypedBusiness)intervalBusiness);
        /*beansMap.put((Class)MetricCollection.class, (TypedBusiness)metricCollectionBusiness);
        beansMap.put((Class)Metric.class, (TypedBusiness)metricBusiness);
        beansMap.put((Class)UniformResourceLocator.class, (TypedBusiness)uniformResourceLocatorBusiness);
        beansMap.put((Class)UniformResourceLocatorParameter.class, (TypedBusiness)uniformResourceLocatorParameterBusiness);
        beansMap.put((Class)RoleUniformResourceLocator.class, (TypedBusiness)roleUniformResourceLocatorBusiness);*/
        //beansMap.put((Class)Role.class, (TypedBusiness)roleBusiness);
        //beansMap.put((Class)License.class, (TypedBusiness)licenseBusiness);
        /*beansMap.put((Class)MovementCollection.class, (TypedBusiness)movementCollectionBusiness);
        beansMap.put((Class)Movement.class, (TypedBusiness)movementBusiness);
        beansMap.put((Class)MovementAction.class, (TypedBusiness)movementActionBusiness);*/
        //beansMap.put((Class)FiniteStateMachineState.class, (TypedBusiness)finiteStateMachineStateBusiness);
        //beansMap.put((Class)FiniteStateMachineStateLog.class, (TypedBusiness)finiteStateMachineStateLogBusiness);
        //beansMap.put((Class)NestedSetNode.class, (TypedBusiness)nestedSetNodeBusiness);
        //beansMap.put((Class)Comment.class, (TypedBusiness)commentBusiness);
        
        /*beansMap.put((Class)DataTree.class, (TypedBusiness)dataTreeBusiness);
        beansMap.put((Class)DataTreeType.class, (TypedBusiness)dataTreeTypeBusiness);
        beansMap.put((Class)DataTreeIdentifiableGlobalIdentifier.class, (TypedBusiness)dataTreeIdentifiableGlobalIdentifierBusiness);
        */
        beansMap.put((Class)Application.class, (TypedBusiness)applicationBusiness);
        //beansMap.put((Class)Sex.class, (TypedBusiness)sexBusiness);
        
        beansMap.put((Class)File.class, (TypedBusiness)fileBusiness);
        //beansMap.put((Class)FileIdentifiableGlobalIdentifier.class, (TypedBusiness)fileIdentifiableGlobalIdentifierBusiness);
        //beansMap.put((Class)Script.class, (TypedBusiness)scriptBusiness);
        //beansMap.put((Class)ScriptVariable.class, (TypedBusiness)scriptVariableBusiness);
    }
    
    @Override
    protected void setConstants(){
    	Application application = inject(ApplicationDao.class).select().one();
    	if(application!=null)
    		applicationIdentifier = application.getIdentifier();
    	//application = applicationDao.select().one(); //applicationBusiness.findCurrentInstance();
        /*
    	landPhoneNumberType = inject(PhoneNumberTypeBusiness.class).findByGlobalIdentifierCode(PhoneNumberType.LAND);
    	mobilePhoneNumberType = inject(PhoneNumberTypeBusiness.class).findByGlobalIdentifierCode(PhoneNumberType.MOBILE);
    	
    	homeLocationType = inject(LocationTypeBusiness.class).findByGlobalIdentifierCode(LocationType.HOME);
    	officeLocationType = inject(LocationTypeBusiness.class).findByGlobalIdentifierCode(LocationType.OFFICE);
    	
    	countryCoteDivoire = inject(CountryDao.class).readByCode(Country.COTE_DIVOIRE);
    	countryLocalityType = inject(LocalityTypeBusiness.class).findByGlobalIdentifierCode(LocalityType.COUNTRY);
    	continentLocalityType = inject(LocalityTypeBusiness.class).findByGlobalIdentifierCode(LocalityType.CONTINENT);
    	cityLocalityType = inject(LocalityTypeBusiness.class).findByGlobalIdentifierCode(LocalityType.CITY);
    	*/
    	/*
    	roleAdministrator = getEnumeration(Role.class,Role.ADMINISTRATOR);
    	roleManager = getEnumeration(Role.class,Role.MANAGER);
    	roleSettingManager = getEnumeration(Role.class,Role.SETTING_MANAGER);
    	roleSecurityManager = getEnumeration(Role.class,Role.SECURITY_MANAGER);
    	roleUser = getEnumeration(Role.class,Role.USER);
    	*/
    	/*
    	timeDivisionTypeDay = getEnumeration(TimeDivisionType.class,TimeDivisionType.DAY);
    	timeDivisionTypeTrimester = getEnumeration(TimeDivisionType.class,TimeDivisionType.TRIMESTER);
    	timeDivisionTypeSemester = getEnumeration(TimeDivisionType.class,TimeDivisionType.SEMESTER);
    	timeDivisionTypeYear = getEnumeration(TimeDivisionType.class,TimeDivisionType.YEAR);
    	*/
    	/*
    	anniversaryEventType = getEnumeration(EventType.class,EventType.ANNIVERSARY);
    	reminderEventType = getEnumeration(EventType.class,EventType.REMINDER);
    	*/
    	
    	RemoteEndPoint.USER_INTERFACE.alarmTemplate = inject(NotificationTemplateDao.class).readByGlobalIdentifierCode(NotificationTemplate.ALARM_USER_INTERFACE);
    	RemoteEndPoint.MAIL_SERVER.alarmTemplate = inject(NotificationTemplateDao.class).readByGlobalIdentifierCode(NotificationTemplate.ALARM_EMAIL);
    	RemoteEndPoint.PHONE.alarmTemplate = inject(NotificationTemplateDao.class).readByGlobalIdentifierCode(NotificationTemplate.ALARM_SMS);
    	
    	
    }
    
    public Application getApplication(){
    	if(applicationIdentifier==null)
    		return null;
    	return inject(ApplicationDao.class).read(applicationIdentifier);
    }
    
    public void setApplication(Application application){
    	this.application = application;
    	if(this.application==null)
    		;
    	else
    		applicationIdentifier = this.application.getIdentifier();
    }
   
    @Override
    protected void fakeTransactions() {
    	
    }
    
    public static RootBusinessLayer getInstance() {
		return INSTANCE;
	}

    /**/
    
    public void enableAlarmScanning(Long delay,Long period,final Set<RemoteEndPoint> remoteEndPoints) {
    	disableAlarmScanning();
    	alarmTimer = new Timer("EventAlarmTimer", Boolean.FALSE);
    	alarmTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				inject(NotificationBusiness.class).run(remoteEndPoints);
			}
		}, delay, period);
    	logInfo("Event Alarm Scanning Enabled");
    }
    
    public void disableAlarmScanning() {
    	if(alarmTimer!=null){
    		alarmTimer.cancel();
    		logInfo("Event Alarm Scanning disabled");
    	}
    }
    
    public Collection<Listener> getRootBusinessLayerListeners() {
		return ROOT_BUSINESS_LAYER_LISTENERS;
	}
	
	public String getRelativeCode(AbstractCollection<?> collection,String code){
		logTrace("Get relative code. {} , code={}", collection.getLogMessage(),code);
		return StringUtils.isBlank(collection.getItemCodeSeparator()) ? code 
				: StringUtils.split(code,collection.getItemCodeSeparator())[1];
	}
	
	/**/
	
	public static class GlobalIdentifierCodeGenerator implements GenerateMethod<AbstractIdentifiable, String> {

		@Override
		public String execute(AbstractIdentifiable input) {
			String generatedCode = null;
			
			for(Listener listener : ROOT_BUSINESS_LAYER_LISTENERS){
				String value = listener.generateGlobalIdentifierCode(input,null);
				if(value!=null)
					generatedCode = value;
			}
			if(generatedCode==null)
				generatedCode = RandomStringUtils.randomAlphabetic(6);
			else{
				do{
					TypedBusiness<AbstractIdentifiable> business =  null;
					for(Listener listener : ROOT_BUSINESS_LAYER_LISTENERS){
						TypedBusiness<AbstractIdentifiable> value = listener.findBusiness(input);
						if(value!=null)
							business = value;
					}
					if(business==null)
						break;
					AbstractIdentifiable existing = business.findByGlobalIdentifierCode(generatedCode);
					
					if(existing==null)
						break;
					else{
						String previousGeneratedCode = generatedCode;
						for(Listener listener : ROOT_BUSINESS_LAYER_LISTENERS){
							String value = listener.generateGlobalIdentifierCode(input,previousGeneratedCode);
							if(value!=null)
								generatedCode = value;
						}
					}
				}while(true);
				
			}
			return generatedCode;
		}
		
	}
	
	/**/
	
	public interface Listener {

		String generateGlobalIdentifierCode(AbstractIdentifiable identifiable,String previousCode);
		
		<IDENTIFIABLE extends AbstractIdentifiable> TypedBusiness<IDENTIFIABLE> findBusiness(IDENTIFIABLE identifiable);
		
		void populateCandidateRoles(List<Role> roles);
		
		/**/
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			
			private static final long serialVersionUID = -7771053357545118564L;

			@Override
			public String generateGlobalIdentifierCode(AbstractIdentifiable identifiable,String previousCode) {
				return null;
			}

			@Override
			public <IDENTIFIABLE extends AbstractIdentifiable> TypedBusiness<IDENTIFIABLE> findBusiness(IDENTIFIABLE identifiable) {
				return null;
			}

			@Override
			public void populateCandidateRoles(List<Role> roles) {}
			
			/**/
			
			public static class Default extends Adapter implements Serializable {

				private static final long serialVersionUID = 3580112506828375899L;
				
				@Override
				public <IDENTIFIABLE extends AbstractIdentifiable> TypedBusiness<IDENTIFIABLE> findBusiness(IDENTIFIABLE identifiable) {
					return BusinessInterfaceLocator.getInstance().injectTypedByObject(identifiable);
				}
				
			}
		}		
	}

    
}
