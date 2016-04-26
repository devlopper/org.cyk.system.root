package org.cyk.system.root.business.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.ClazzBusiness;
import org.cyk.system.root.business.api.ClazzBusiness.ClazzBusinessListener;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.event.EventMissedBusiness;
import org.cyk.system.root.business.api.event.EventMissedReasonBusiness;
import org.cyk.system.root.business.api.event.EventParticipationBusiness;
import org.cyk.system.root.business.api.event.EventTypeBusiness;
import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.file.TagBusiness;
import org.cyk.system.root.business.api.generator.StringGeneratorBusiness;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.geography.CountryBusiness;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.business.api.geography.LocationTypeBusiness;
import org.cyk.system.root.business.api.geography.PhoneNumberTypeBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.markuplanguage.MarkupLanguageBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MathematicsBusiness;
import org.cyk.system.root.business.api.mathematics.MetricBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricValueBusiness;
import org.cyk.system.root.business.api.mathematics.MovementActionBusiness;
import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineAlphabetBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineBusiness;
import org.cyk.system.root.business.api.mathematics.machine.FiniteStateMachineStateBusiness;
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.network.UniformResourceLocatorParameterBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.business.api.party.person.JobFunctionBusiness;
import org.cyk.system.root.business.api.party.person.JobTitleBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonTitleBusiness;
import org.cyk.system.root.business.api.security.LicenseBusiness;
import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.api.security.RoleSecuredViewBusiness;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.api.time.TimeDivisionTypeBusiness;
import org.cyk.system.root.business.api.userinterface.GraphicBusiness;
import org.cyk.system.root.business.impl.file.FileValidator;
import org.cyk.system.root.business.impl.file.report.AbstractReportRepository;
import org.cyk.system.root.business.impl.party.person.PersonValidator;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Clazz;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.model.event.EventType;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Tag;
import org.cyk.system.root.model.generator.StringGenerator;
import org.cyk.system.root.model.generator.StringValueGenerator;
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.generator.ValueGenerator.GenerateMethod;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleSecuredView;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;
import org.cyk.system.root.persistence.api.geography.CountryDao;
import org.cyk.system.root.persistence.api.mathematics.IntervalDao;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.MetricDao;
import org.cyk.system.root.persistence.api.party.ApplicationDao;
import org.cyk.system.root.persistence.api.security.RoleDao;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;

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
	
	private PhoneNumberType landPhoneNumberType,mobilePhoneNumberType;
	private LocationType homeLocationType,officeLocationType;
	private LocalityType countryLocalityType,cityLocalityType,continentLocalityType;
	private Country countryCoteDivoire;
	private Role roleAdministrator,roleManager,roleSettingManager,roleSecurityManager,roleUser;
	private TimeDivisionType timeDivisionTypeYear,timeDivisionTypeTrimester,timeDivisionTypeSemester,timeDivisionTypeDay;
	private EventType anniversaryEventType,reminderEventType;
	private Sex sexMale,sexFemale;
	
	@Inject private LanguageBusiness languageBusiness;
	@Inject private MathematicsBusiness mathematicsBusiness;
	@Inject private TimeBusiness timeBusiness;
	@Inject private NumberBusiness numberBusiness;
	@Inject private GraphicBusiness graphicBusiness;
	@Inject private ApplicationBusiness applicationBusiness;
	@Inject private GenericBusiness genericBusiness;
	@Inject private ContactCollectionBusiness contactCollectionBusiness;
	@Inject private PhoneNumberTypeBusiness phoneNumberTypeBusiness;
	@Inject private LocationTypeBusiness locationTypeBusiness;
	@Inject private LocalityBusiness localityBusiness;
	@Inject private LocalityTypeBusiness localityTypeBusiness;
	@Inject private CountryBusiness countryBusiness;
	@Inject private TagBusiness tagBusiness;
    @Inject private EventBusiness eventBusiness;
    @Inject private EventParticipationBusiness eventParticipationBusiness;
    @Inject private EventMissedBusiness eventMissedBusiness;
    @Inject private EventMissedReasonBusiness eventMissedReasonBusiness;
    @Inject private PersonBusiness personBusiness;
    @Inject private IntervalCollectionBusiness intervalCollectionBusiness;
    @Inject private IntervalBusiness intervalBusiness;
    @Inject private IntervalDao intervalDao;
    @Inject private RoleBusiness roleBusiness;
    @Inject private RoleSecuredViewBusiness roleSecuredViewBusiness;
    @Inject private UserAccountBusiness userAccountBusiness;
    @Inject private TimeDivisionTypeBusiness timeDivisionTypeBusiness;
    @Inject private EventTypeBusiness eventTypeBusiness;
    @Inject private StringGeneratorBusiness stringGeneratorBusiness;
    @Inject private ClazzBusiness clazzBusiness;
    @Inject private JobTitleBusiness jobTitleBusiness;
    @Inject private PersonTitleBusiness personTitleBusiness;
    @Inject private JobFunctionBusiness jobFunctionBusiness;
    @Inject private MetricCollectionBusiness metricCollectionBusiness;
    @Inject private MetricCollectionDao metricCollectionDao;
    @Inject private MetricBusiness metricBusiness;
    @Inject private MetricDao metricDao;
    @Inject private MetricValueBusiness metricValueBusiness;
    @Inject private UniformResourceLocatorBusiness uniformResourceLocatorBusiness;
    @Inject private UniformResourceLocatorParameterBusiness uniformResourceLocatorParameterBusiness;
    @Inject private RoleUniformResourceLocatorBusiness roleUniformResourceLocatorBusiness;
    @Inject private LicenseBusiness licenseBusiness;
    @Inject private MovementBusiness movementBusiness;
    @Inject private MovementActionBusiness movementActionBusiness;
    @Inject private MovementCollectionBusiness movementCollectionBusiness;
    @Inject private MarkupLanguageBusiness markupLanguageBusiness;
    
    @Inject private FiniteStateMachineBusiness finiteStateMachineBusiness;
    @Inject private FiniteStateMachineStateBusiness finiteStateMachineStateBusiness;
    @Inject private FiniteStateMachineAlphabetBusiness finiteStateMachineAlphabetBusiness;
    
    @Inject private RoleDao roleDao;
    @Inject private NotificationTemplateDao notificationTemplateDao;
    @Inject private NotificationBusiness notificationBusiness;
    @Inject private CountryDao countryDao;
    @Inject private GenericDao genericDao;
    //@Inject private EventParticipationDao eventParticipationDao;
    
    @Inject private PersonValidator personValidator;
    @Inject private FileValidator fileValidator;
    @Inject private RootReportRepository reportRepository;
    
    @Inject private RootBusinessTestHelper rootBusinessTestHelper;
    @Inject private ApplicationDao applicationDao;
    
    //@Setter private Application application;
    @Setter private Long applicationIdentifier;
    
    //private Person personAdmin,personGuest;
    
    private static final Collection<Listener> ROOT_BUSINESS_LAYER_LISTENERS = new ArrayList<>();
    
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
        
        BusinessListener.COLLECTION.add(new BusinessListener.Adapter.Default(){
			private static final long serialVersionUID = 2105514784569748009L;
			@SuppressWarnings("unchecked")
			@Override
			public <T extends AbstractIdentifiable> Collection<T> find(Class<T> dataClass,DataReadConfiguration configuration) {
				if(Person.class.equals(dataClass)){
					PersonSearchCriteria criteria = new PersonSearchCriteria(configuration.getGlobalFilter());
					criteria.getReadConfig().set(configuration);
					return (Collection<T>) personBusiness.findByCriteria(criteria);
				}else if(AbstractActor.class.isAssignableFrom(dataClass)){
					System.out
							.println("RootBusinessLayer.initialisation().new Default() {...}.find() : ACTOR");
				}
				return super.find(dataClass, configuration);
			}
			
			@Override
			public <T extends AbstractIdentifiable> Long count(Class<T> dataClass,DataReadConfiguration configuration) {
				if(Person.class.equals(dataClass)){
					return personBusiness.countByCriteria(new PersonSearchCriteria(configuration.getGlobalFilter()));
				}
				return super.count(dataClass, configuration);
			}
        });
        
        registerFormatter(MetricValue.class, new AbstractFormatter<MetricValue>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(MetricValue metricValue, ContentType contentType) {
				return metricValueBusiness.format(metricValue);
			}
		});
        
        //application = applicationDao.select().one();
        
        rootBusinessTestHelper.setReportBusiness(reportBusiness);
        
        applicationBusiness.registerValueGenerator((ValueGenerator<?, ?>) new StringValueGenerator<Party>(
        		ValueGenerator.PARTY_CODE_IDENTIFIER,ValueGenerator.PARTY_CODE_DESCRIPTION, Party.class));
        applicationBusiness.registerValueGenerator((ValueGenerator<?, ?>) new StringValueGenerator<AbstractActor>(
        		ValueGenerator.ACTOR_REGISTRATION_CODE_IDENTIFIER,ValueGenerator.ACTOR_REGISTRATION_CODE_DESCRIPTION, AbstractActor.class));
        
        registerValidator(Person.class, personValidator);
        registerValidator(File.class, fileValidator);
        
        /*
        String systemName = StringUtils.split(this.getClass().getName(), '.')[3];
        registerResourceBundle("org.cyk.system."+systemName+".model.resources.entity", getClass().getClassLoader());
		registerResourceBundle("org.cyk.system."+systemName+".model.resources.message", getClass().getClassLoader());
		registerResourceBundle("org.cyk.system."+systemName+".business.impl.resources.message", getClass().getClassLoader());
        */
        
        @SuppressWarnings("unchecked")
		ValueGenerator<AbstractActor,String> actorRegistrationCodeGenerator = (ValueGenerator<AbstractActor, String>) 
				RootBusinessLayer.getInstance().getApplicationBusiness().findValueGenerator(ValueGenerator.ACTOR_REGISTRATION_CODE_IDENTIFIER);
		
		actorRegistrationCodeGenerator.setMethod(new GenerateMethod<AbstractActor, String>() {
				@Override
				public String execute(AbstractActor actor) {
					String generatedCode = null;
					for(Listener listener : ROOT_BUSINESS_LAYER_LISTENERS){
						String value = listener.generateActorRegistrationCode(actor,null);
						if(value!=null)
							generatedCode = value;
					}
					if(generatedCode==null)
						generatedCode = RandomStringUtils.randomAlphabetic(6);
					else{
						do{
							AbstractActorBusiness<AbstractActor,?> business =  null;
							for(Listener listener : ROOT_BUSINESS_LAYER_LISTENERS){
								AbstractActorBusiness<AbstractActor,?> value = listener.findActorBusiness(actor);
								if(value!=null)
									business = value;
							}
							if(business==null)
								break;
							AbstractActor existingActor = business.findByRegistrationCode(generatedCode);
							
							if(existingActor==null)
								break;
							else{
								String previousGeneratedCode = generatedCode;
								for(Listener listener : ROOT_BUSINESS_LAYER_LISTENERS){
									String value = listener.generateActorRegistrationCode(actor,previousGeneratedCode);
									if(value!=null)
										generatedCode = value;
								}
							}
						}while(true);
					}
					
					return generatedCode;
				}
			});
		
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
        
        create(countryCoteDivoire = new Country(new Locality(afrique, country,Country.COTE_DIVOIRE, "Cote d'Ivoire"),225));
        create(new Locality(afrique, country, "Benin"));
        
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
    	create(new EventType(EventType.ANNIVERSARY, "Anniversaire", null));
    	create(new EventType(EventType.APPOINTMENT, "Rendez vous", null));
        create(new EventType(EventType.MEETING, "Reunion", null));
        create(new EventType(EventType.REMINDER, "Rappel", null));
        
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
        beansMap.put((Class)Event.class, (TypedBusiness)eventBusiness);
        beansMap.put((Class)EventMissed.class, (TypedBusiness)eventMissedBusiness);
        beansMap.put((Class)EventMissedReason.class, (TypedBusiness)eventMissedReasonBusiness);
        beansMap.put((Class)Person.class, (TypedBusiness)personBusiness);
        beansMap.put((Class)Locality.class, (TypedBusiness)localityBusiness);
        beansMap.put((Class)Country.class, (TypedBusiness)countryBusiness);
        beansMap.put((Class)LocalityType.class, (TypedBusiness)localityTypeBusiness);
        beansMap.put((Class)Tag.class, (TypedBusiness)tagBusiness);
        beansMap.put((Class)UserAccount.class, (TypedBusiness)userAccountBusiness);
        beansMap.put((Class)StringGenerator.class, (TypedBusiness)stringGeneratorBusiness);
        beansMap.put((Class)RoleSecuredView.class, (TypedBusiness)roleSecuredViewBusiness);
        beansMap.put((Class)JobTitle.class, (TypedBusiness)jobTitleBusiness);
        beansMap.put((Class)PersonTitle.class, (TypedBusiness)personTitleBusiness);
        beansMap.put((Class)JobFunction.class, (TypedBusiness)jobFunctionBusiness);
        beansMap.put((Class)IntervalCollection.class, (TypedBusiness)intervalCollectionBusiness);
        beansMap.put((Class)Interval.class, (TypedBusiness)intervalBusiness);
        beansMap.put((Class)MetricCollection.class, (TypedBusiness)metricCollectionBusiness);
        beansMap.put((Class)Metric.class, (TypedBusiness)metricBusiness);
        beansMap.put((Class)UniformResourceLocator.class, (TypedBusiness)uniformResourceLocatorBusiness);
        beansMap.put((Class)UniformResourceLocatorParameter.class, (TypedBusiness)uniformResourceLocatorParameterBusiness);
        beansMap.put((Class)RoleUniformResourceLocator.class, (TypedBusiness)roleUniformResourceLocatorBusiness);
        beansMap.put((Class)Role.class, (TypedBusiness)roleBusiness);
        beansMap.put((Class)License.class, (TypedBusiness)licenseBusiness);
        beansMap.put((Class)MovementCollection.class, (TypedBusiness)movementCollectionBusiness);
        beansMap.put((Class)Movement.class, (TypedBusiness)movementBusiness);
        beansMap.put((Class)MovementAction.class, (TypedBusiness)movementActionBusiness);
    }
    
    @Override
    protected void setConstants(){
    	Application application = applicationDao.select().one();
    	if(application!=null)
    		applicationIdentifier = application.getIdentifier();
    	//application = applicationDao.select().one(); //applicationBusiness.findCurrentInstance();
        
    	landPhoneNumberType = phoneNumberTypeBusiness.find(PhoneNumberType.LAND);
    	mobilePhoneNumberType = phoneNumberTypeBusiness.find(PhoneNumberType.MOBILE);
    	
    	homeLocationType = locationTypeBusiness.find(LocationType.HOME);
    	officeLocationType = locationTypeBusiness.find(LocationType.OFFICE);
    	
    	countryCoteDivoire = countryDao.readByCode(Country.COTE_DIVOIRE);
    	countryLocalityType = localityTypeBusiness.find(LocalityType.COUNTRY);
    	continentLocalityType = localityTypeBusiness.find(LocalityType.CONTINENT);
    	cityLocalityType = localityTypeBusiness.find(LocalityType.CITY);
    	
    	roleAdministrator = getEnumeration(Role.class,Role.ADMINISTRATOR);
    	roleManager = getEnumeration(Role.class,Role.MANAGER);
    	roleSettingManager = getEnumeration(Role.class,Role.SETTING_MANAGER);
    	roleSecurityManager = getEnumeration(Role.class,Role.SECURITY_MANAGER);
    	roleUser = getEnumeration(Role.class,Role.USER);
    	
    	timeDivisionTypeDay = getEnumeration(TimeDivisionType.class,TimeDivisionType.DAY);
    	timeDivisionTypeTrimester = getEnumeration(TimeDivisionType.class,TimeDivisionType.TRIMESTER);
    	timeDivisionTypeSemester = getEnumeration(TimeDivisionType.class,TimeDivisionType.SEMESTER);
    	timeDivisionTypeYear = getEnumeration(TimeDivisionType.class,TimeDivisionType.YEAR);
    	
    	anniversaryEventType = getEnumeration(EventType.class,EventType.ANNIVERSARY);
    	reminderEventType = getEnumeration(EventType.class,EventType.REMINDER);
    	
    	sexMale = getEnumeration(Sex.class, Sex.MALE);
    	sexFemale = getEnumeration(Sex.class, Sex.FEMALE);
    	
    	RemoteEndPoint.USER_INTERFACE.alarmTemplate = notificationTemplateDao.read(NotificationTemplate.ALARM_USER_INTERFACE);
    	RemoteEndPoint.MAIL_SERVER.alarmTemplate = notificationTemplateDao.read(NotificationTemplate.ALARM_EMAIL);
    	RemoteEndPoint.PHONE.alarmTemplate = notificationTemplateDao.read(NotificationTemplate.ALARM_SMS);
    	
    	
    }
    
    public Application getApplication(){
    	if(applicationIdentifier==null)
    		return null;
    	return applicationDao.read(applicationIdentifier);
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
				notificationBusiness.run(remoteEndPoints);
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
    
    
    
    public <T extends AbstractIdentifiable> Long count(Class<T> aClass, DataReadConfiguration dataReadConfiguration) {
		for(BusinessListener listener : BusinessListener.COLLECTION){
			Long count = listener.count(aClass, dataReadConfiguration);
			if(count!=null)
				return count;
		}	
		return null;
	}

	public <T extends AbstractIdentifiable> Collection<T> find(Class<T> aClass,DataReadConfiguration dataReadConfiguration) {
		for(BusinessListener listener : BusinessListener.COLLECTION){
			Collection<T> collection = listener.find(aClass,dataReadConfiguration);
			if(collection!=null)
				return collection;
		}
		return null;
	}
	
	public String getRelativeCode(AbstractCollection<?> collection,String code){
		logTrace("Get relative code. {} , code={}", collection.getLogMessage(),code);
		return StringUtils.isBlank(collection.getItemCodeSeparator()) ? code 
				: StringUtils.split(code,collection.getItemCodeSeparator())[1];
	}
	
	/**/
	
	public interface Listener {

		String generateActorRegistrationCode(AbstractActor actor,String previousCode);
		
		<ACTOR extends AbstractActor> AbstractActorBusiness<ACTOR,?> findActorBusiness(ACTOR actor);
		
		void populateCandidateRoles(List<Role> roles);
		
		/**/
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			
			private static final long serialVersionUID = -7771053357545118564L;

			@Override
			public String generateActorRegistrationCode(AbstractActor actor,String previousCode) {
				return null;
			}

			@Override
			public <ACTOR extends AbstractActor> AbstractActorBusiness<ACTOR,?> findActorBusiness(ACTOR actor) {
				return null;
			}

			@Override
			public void populateCandidateRoles(List<Role> roles) {}
			
			/**/
			
			public static class Default extends Adapter implements Serializable {

				private static final long serialVersionUID = 3580112506828375899L;
				
			}
		}

		
	}

    
}
