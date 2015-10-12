package org.cyk.system.root.business.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import lombok.Getter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.ClazzBusiness;
import org.cyk.system.root.business.api.ClazzBusiness.ClazzBusinessAdapter;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.RootBusinessLayerListener;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
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
import org.cyk.system.root.business.api.mathematics.MathematicsBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.api.security.RoleSecuredViewBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.api.time.TimeDivisionTypeBusiness;
import org.cyk.system.root.business.api.userinterface.GraphicBusiness;
import org.cyk.system.root.business.impl.file.FileValidator;
import org.cyk.system.root.business.impl.file.report.AbstractReportRepository;
import org.cyk.system.root.business.impl.party.person.PersonValidator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Clazz;
import org.cyk.system.root.model.event.Event;
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
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleSecuredView;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Deployment(initialisationType=InitialisationType.EAGER,order=RootBusinessLayer.DEPLOYMENT_ORDER)
public class RootBusinessLayer extends AbstractBusinessLayer implements Serializable {
 
	public static final int DEPLOYMENT_ORDER = 0;
	private static final long serialVersionUID = 4576531258594638L;
	
	private static RootBusinessLayer INSTANCE;
	
	private Timer alarmTimer;
	
	@Getter private final String parameterGenericReportBasedOnDynamicBuilder = "grbodb"; 
	@Getter private final String parameterGenericDashBoardReport = "gdbr"; 
	@Getter private final String parameterFromDate = "fd"; 
	@Getter private final String parameterToDate = "td"; 
	
	@Getter private PhoneNumberType landPhoneNumberType,mobilePhoneNumberType;
	@Getter private LocationType homeLocationType,officeLocationType;
	@Getter private LocalityType countryLocalityType,cityLocalityType,continentLocalityType;
	@Getter private Country countryCoteDivoire;
	@Getter private Role administratorRole,managerRole,businessActorRole,settingManagerRole,securityManagerRole,userRole;
	@Getter private TimeDivisionType timeDivisionTypeYear,timeDivisionTypeTrimester,timeDivisionTypeSemester,timeDivisionTypeDay;
	@Getter private EventType anniversaryEventType,reminderEventType;
	
	@Inject @Getter private LanguageBusiness languageBusiness;
	@Inject @Getter private MathematicsBusiness mathematicsBusiness;
	@Inject @Getter private TimeBusiness timeBusiness;
	@Inject @Getter private NumberBusiness numberBusiness;
	@Inject @Getter private GraphicBusiness graphicBusiness;
	@Inject @Getter private ApplicationBusiness applicationBusiness;
	@Inject @Getter private GenericBusiness genericBusiness;
	@Inject @Getter private ContactCollectionBusiness contactCollectionBusiness;
	@Inject private PhoneNumberTypeBusiness phoneNumberTypeBusiness;
	@Inject private LocationTypeBusiness locationTypeBusiness;
	@Inject private LocalityBusiness localityBusiness;
	@Inject private LocalityTypeBusiness localityTypeBusiness;
	@Inject private CountryBusiness countryBusiness;
	@Inject private TagBusiness tagBusiness;
    @Inject private EventBusiness eventBusiness;
    @Inject private PersonBusiness personBusiness;
    @Inject private RoleBusiness roleBusiness;
    @Inject private RoleSecuredViewBusiness roleSecuredViewBusiness;
    @Inject private UserAccountBusiness userAccountBusiness;
    @Inject private TimeDivisionTypeBusiness timeDivisionTypeBusiness;
    @Inject private EventTypeBusiness eventTypeBusiness;
    @Inject @Getter private StringGeneratorBusiness stringGeneratorBusiness;
    @Inject @Getter private ClazzBusiness clazzBusiness;
    
    
    @Inject private NotificationTemplateDao notificationTemplateDao;
    @Inject private NotificationBusiness notificationBusiness;
    //@Inject private EventParticipationDao eventParticipationDao;
    
    @Inject private PersonValidator personValidator;
    @Inject private FileValidator fileValidator;
    @Inject private RootReportRepository reportRepository;
    
    @Inject private RootTestHelper rootTestHelper;
    
    //private Person personAdmin,personGuest;
    
    private static final Collection<RootBusinessLayerListener> ROOT_BUSINESS_LAYER_LISTENERS = new ArrayList<>();
    
    @Override
    protected void initialisation() {
    	INSTANCE = this; 
        super.initialisation();
        
        ClazzBusiness.LISTENERS.add(new ClazzBusinessAdapter() {
			private static final long serialVersionUID = 4056356640763766384L;
			@Override
			public void doSetUiLabel(Clazz clazz) {
				clazz.setUiLabel(languageBusiness.findText(clazz.getUiLabelId()));
			}
		});
        
        rootTestHelper.setReportBusiness(reportBusiness);
        rootTestHelper.setRootBusinessLayer(this); 
        
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
					for(RootBusinessLayerListener listener : ROOT_BUSINESS_LAYER_LISTENERS){
						String value = listener.generateActorRegistrationCode(actor,null);
						if(value!=null)
							generatedCode = value;
					}
					if(generatedCode==null)
						generatedCode = RandomStringUtils.randomAlphabetic(6);
					else{
						do{
							AbstractActorBusiness<AbstractActor> business =  null;
							for(RootBusinessLayerListener listener : ROOT_BUSINESS_LAYER_LISTENERS){
								AbstractActorBusiness<AbstractActor> value = listener.findActorBusiness(actor);
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
								for(RootBusinessLayerListener listener : ROOT_BUSINESS_LAYER_LISTENERS){
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
    	create(new TimeDivisionType(TimeDivisionType.DAY, "Journalier",DateUtils.MILLIS_PER_DAY ,Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.WEEK, "Hebdomadaire",DateUtils.MILLIS_PER_DAY*7, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.MONTH, "Mensuel",DateUtils.MILLIS_PER_DAY*30, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.TRIMESTER, "Trimestre",DateUtils.MILLIS_PER_DAY*30*3, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.SEMESTER, "Semestre",DateUtils.MILLIS_PER_DAY*30*6, Boolean.TRUE));
        create(new TimeDivisionType(TimeDivisionType.YEAR, "Annuel",DateUtils.MILLIS_PER_DAY*30*12,Boolean.TRUE));
        
    }
    
    private void party(){
    	create(new Sex("M", "Masculin"));
        create(new Sex("F", "Feminin"));
        create(new MaritalStatus("B", "Celibataire"));
        create(new MaritalStatus("M", "Marie"));
    }
    
    private void security(){ 
    	//Permission licenceRead = createPermission(permissionBusiness.computeCode(License.class, Crud.READ));
    	
    	createRole(Role.ADMINISTRATOR, "Administrator");
    	createRole(Role.MANAGER, "Manager");
    	createRole(Role.BUSINESS_ACTOR, "Business actor");
        createRole(Role.SETTING_MANAGER, "Setting Manager");
        createRole(Role.SECURITY_MANAGER, "Security Manager");
        createRole(Role.USER, "User",SHIRO_PRIVATE_FOLDER);
        
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void registerTypedBusinessBean(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> beansMap) {
        beansMap.put((Class)Event.class, (TypedBusiness)eventBusiness);
        beansMap.put((Class)Person.class, (TypedBusiness)personBusiness);
        beansMap.put((Class)Locality.class, (TypedBusiness)localityBusiness);
        beansMap.put((Class)Country.class, (TypedBusiness)countryBusiness);
        beansMap.put((Class)LocalityType.class, (TypedBusiness)localityTypeBusiness);
        beansMap.put((Class)Tag.class, (TypedBusiness)tagBusiness);
        beansMap.put((Class)UserAccount.class, (TypedBusiness)userAccountBusiness);
        beansMap.put((Class)StringGenerator.class, (TypedBusiness)stringGeneratorBusiness);
        beansMap.put((Class)RoleSecuredView.class, (TypedBusiness)roleSecuredViewBusiness);
    }
    
    @Override
    protected void setConstants(){
    	landPhoneNumberType = phoneNumberTypeBusiness.find(PhoneNumberType.LAND);
    	mobilePhoneNumberType = phoneNumberTypeBusiness.find(PhoneNumberType.MOBILE);
    	
    	homeLocationType = locationTypeBusiness.find(LocationType.HOME);
    	officeLocationType = locationTypeBusiness.find(LocationType.OFFICE);
    	
    	countryCoteDivoire = countryBusiness.findByCode(Country.COTE_DIVOIRE);
    	countryLocalityType = localityTypeBusiness.find(LocalityType.COUNTRY);
    	continentLocalityType = localityTypeBusiness.find(LocalityType.CONTINENT);
    	cityLocalityType = localityTypeBusiness.find(LocalityType.CITY);
    	
    	administratorRole = roleBusiness.find(Role.ADMINISTRATOR);
    	managerRole = roleBusiness.find(Role.MANAGER);
    	settingManagerRole = roleBusiness.find(Role.SETTING_MANAGER);
    	securityManagerRole = roleBusiness.find(Role.SECURITY_MANAGER);
    	businessActorRole = roleBusiness.find(Role.BUSINESS_ACTOR);
    	userRole = roleBusiness.find(Role.USER);
    	
    	timeDivisionTypeDay = timeDivisionTypeBusiness.find(TimeDivisionType.DAY);
    	timeDivisionTypeTrimester = timeDivisionTypeBusiness.find(TimeDivisionType.TRIMESTER);
    	timeDivisionTypeSemester = timeDivisionTypeBusiness.find(TimeDivisionType.SEMESTER);
    	timeDivisionTypeYear = timeDivisionTypeBusiness.find(TimeDivisionType.YEAR);
    	
    	anniversaryEventType = eventTypeBusiness.find(EventType.ANNIVERSARY);
    	reminderEventType = eventTypeBusiness.find(EventType.REMINDER);
    	
    	RemoteEndPoint.USER_INTERFACE.alarmTemplate = notificationTemplateDao.read(NotificationTemplate.ALARM_USER_INTERFACE);
    	RemoteEndPoint.MAIL_SERVER.alarmTemplate = notificationTemplateDao.read(NotificationTemplate.ALARM_EMAIL);
    	RemoteEndPoint.PHONE.alarmTemplate = notificationTemplateDao.read(NotificationTemplate.ALARM_SMS);
    	
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
    
    public Collection<RootBusinessLayerListener> getRootBusinessLayerListeners() {
		return ROOT_BUSINESS_LAYER_LISTENERS;
	}
    
    
    
}
