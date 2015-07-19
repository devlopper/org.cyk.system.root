package org.cyk.system.root.business.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import lombok.Getter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.event.EventTypeBusiness;
import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.TagBusiness;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.business.api.geography.LocationTypeBusiness;
import org.cyk.system.root.business.api.geography.PhoneNumberTypeBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.api.time.TimeDivisionTypeBusiness;
import org.cyk.system.root.business.api.userinterface.GraphicBusiness;
import org.cyk.system.root.business.impl.file.FileValidator;
import org.cyk.system.root.business.impl.file.report.DefaultReportBasedOnDynamicBuilder;
import org.cyk.system.root.business.impl.file.report.jasper.DefaultJasperReportBasedOnDynamicBuilder;
import org.cyk.system.root.business.impl.party.person.PersonValidator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventType;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Tag;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderIdentifiableConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderListener;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.generator.StringValueGenerator;
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.time.Period;
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
	@Getter private Locality countryCoteDivoire;
	@Getter private Role administratorRole,managerRole,businessActorRole,settingManagerRole,securityManagerRole,userRole;
	@Getter private TimeDivisionType timeDivisionTypeYear,timeDivisionTypeTrimester,timeDivisionTypeSemester,timeDivisionTypeDay;
	@Getter private EventType anniversaryEventType,reminderEventType;
	
	@Inject @Getter private LanguageBusiness languageBusiness;
	@Inject @Getter private TimeBusiness timeBusiness;
	@Inject @Getter private NumberBusiness numberBusiness;
	@Inject @Getter private GraphicBusiness graphicBusiness;
	@Inject @Getter private ApplicationBusiness applicationBusiness;
	@Inject @Getter private GenericBusiness genericBusiness;
	@Inject private PhoneNumberTypeBusiness phoneNumberTypeBusiness;
	@Inject private LocationTypeBusiness locationTypeBusiness;
	@Inject private LocalityBusiness localityBusiness;
	@Inject private LocalityTypeBusiness localityTypeBusiness;
	@Inject private TagBusiness tagBusiness;
    @Inject private EventBusiness eventBusiness;
    @Inject private PersonBusiness personBusiness;
    @Inject private RoleBusiness roleBusiness;
    @Inject private UserAccountBusiness userAccountBusiness;
    @Inject private TimeDivisionTypeBusiness timeDivisionTypeBusiness;
    @Inject private EventTypeBusiness eventTypeBusiness;
    
    @Inject @Getter private FileBusiness fileBusiness;
    @Inject private NotificationTemplateDao notificationTemplateDao;
    @Inject private NotificationBusiness notificationBusiness;
    //@Inject private EventParticipationDao eventParticipationDao;
    
    @Inject private PersonValidator personValidator;
    @Inject private FileValidator fileValidator;
    
    @Inject private RootTestHelper rootTestHelper;
    
    //private Person personAdmin,personGuest;
        
    @Override
    protected void initialisation() {
    	INSTANCE = this;
        super.initialisation();
       
        rootTestHelper.setReportBusiness(reportBusiness);
        rootTestHelper.setRootBusinessLayer(this); 
        
        applicationBusiness.registerValueGenerator((ValueGenerator<?, ?>) new StringValueGenerator<Party>(
        		ValueGenerator.PARTY_CODE_IDENTIFIER,ValueGenerator.PARTY_CODE_DESCRIPTION, Party.class));
        applicationBusiness.registerValueGenerator((ValueGenerator<?, ?>) new StringValueGenerator<AbstractActor>(
        		ValueGenerator.ACTOR_REGISTRATION_CODE_IDENTIFIER,ValueGenerator.ACTOR_REGISTRATION_CODE_DESCRIPTION, AbstractActor.class));
        
        registerValidator(Person.class, personValidator);
        registerValidator(File.class, fileValidator);
        /*
        registerFieldValidator(commonUtils.getFieldFromClass(Person.class, "name"), new FieldValidatorMethod() {
            
			private static final long serialVersionUID = 1L;

			@Override
            protected Void __execute__(Object[] parameter) {
                String name = (String) parameter[0];
                if(!"cj".equals(name))
                    throw new BusinessException("Method exception oohhh");
                return null;
            }
        });
        */
        
        registerReportConfiguration(new ReportBasedOnDynamicBuilderConfiguration<Object, ReportBasedOnDynamicBuilder<Object>>(parameterGenericReportBasedOnDynamicBuilder) {        	
			@SuppressWarnings("unchecked")
			@Override
			public ReportBasedOnDynamicBuilder<Object> build(ReportBasedOnDynamicBuilderParameters<Object> parameters) {
				parameters.setDatas(new ArrayList<Object>());
				if(parameters.getIdentifiableClass()==null){
					
				}else{
					ReportBasedOnDynamicBuilderIdentifiableConfiguration<AbstractIdentifiable, Object> identifiableConfiguration = null;
					for(ReportBasedOnDynamicBuilderIdentifiableConfiguration<AbstractIdentifiable, Object> ic : ReportBasedOnDynamicBuilderListener.IDENTIFIABLE_CONFIGURATIONS)
						if(parameterGenericReportBasedOnDynamicBuilder.equals(ic.getReportBasedOnDynamicBuilderIdentifier()) 
								&& ic.getIdentifiableClass().equals(parameters.getIdentifiableClass())
								//&& ic.getModelClass().equals(parameters.getModelClass())
								){
							identifiableConfiguration = ic;
							if(parameters.getModelClass()==null)
								parameters.setModelClass((Class<Object>) ic.getModelClass());
							break;
						}
					Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
					
					if(identifiableConfiguration==null || !Boolean.TRUE.equals(identifiableConfiguration.useCustomIdentifiableCollection()))
						identifiables.addAll(genericBusiness.use(parameters.getIdentifiableClass()).find().all());
					else
						identifiables.addAll(identifiableConfiguration.identifiables(parameters));	
						
					for(AbstractIdentifiable identifiable : identifiables){
			        	Object value = identifiableConfiguration == null ? identifiable:identifiableConfiguration.model(identifiable);
						parameters.getDatas().add(value);	
					}
					
				}
		        parameters.getReportBasedOnDynamicBuilderListeners().add(new DefaultReportBasedOnDynamicBuilder());
		        parameters.getReportBasedOnDynamicBuilderListeners().add(new DefaultJasperReportBasedOnDynamicBuilder());
				return (ReportBasedOnDynamicBuilder<Object>) reportBusiness.build(parameters);
			}
		});
        /*
        registerReportConfiguration(new ReportBasedOnDynamicBuilderConfiguration<Object, ReportBasedOnDynamicBuilder<Object>>(parameterGenericDashBoardReport) {        	
			@SuppressWarnings("unchecked")
			@Override
			public ReportBasedOnDynamicBuilder<Object> build(ReportBasedOnDynamicBuilderParameters<Object> parameters) {
				parameters.setDatas(new ArrayList<Object>());
				if(parameters.getIdentifiableClass()==null){
					
				}else{
					ReportBasedOnDynamicBuilderIdentifiableConfiguration<AbstractIdentifiable, Object> identifiableConfiguration = null;
					for(ReportBasedOnDynamicBuilderIdentifiableConfiguration<AbstractIdentifiable, Object> ic : ReportBasedOnDynamicBuilderListener.IDENTIFIABLE_CONFIGURATIONS)
						if(parameterGenericObjectReportTable.equals(ic.getReportBasedOnDynamicBuilderIdentifier()) && ic.getIdentifiableClass().equals(parameters.getIdentifiableClass())){
							identifiableConfiguration = ic;
							if(parameters.getModelClass()==null)
								parameters.setModelClass((Class<Object>) ic.getModelClass());
							break;
						}
					Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
					
					if(identifiableConfiguration==null || !Boolean.TRUE.equals(identifiableConfiguration.useCustomIdentifiableCollection()))
						identifiables.addAll(genericBusiness.use(parameters.getIdentifiableClass()).find().all());
					else
						identifiables.addAll(identifiableConfiguration.identifiables(parameters));	
						
					for(AbstractIdentifiable identifiable : identifiables){
			        	Object value = identifiableConfiguration == null ? identifiable:identifiableConfiguration.model(identifiable);
						parameters.getDatas().add(value);	
					}
					
				}
		        parameters.getReportBasedOnDynamicBuilderListeners().add(new DefaultReportBasedOnDynamicBuilder());
		        parameters.getReportBasedOnDynamicBuilderListeners().add(new DefaultJasperReportBasedOnDynamicBuilder());
				return (ReportBasedOnDynamicBuilder<Object>) reportBusiness.build(parameters);
			}
		});
		*/
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
        
        create(countryCoteDivoire = new Locality(afrique, country,Locality.COUNTRY_COTE_DIVOIRE, "Cote d'Ivoire"));
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
        beansMap.put((Class)LocalityType.class, (TypedBusiness)localityTypeBusiness);
        beansMap.put((Class)Tag.class, (TypedBusiness)tagBusiness);
        beansMap.put((Class)UserAccount.class, (TypedBusiness)userAccountBusiness);
    }
    
    @Override
    protected void setConstants(){
    	landPhoneNumberType = phoneNumberTypeBusiness.find(PhoneNumberType.LAND);
    	mobilePhoneNumberType = phoneNumberTypeBusiness.find(PhoneNumberType.MOBILE);
    	
    	homeLocationType = locationTypeBusiness.find(LocationType.HOME);
    	officeLocationType = locationTypeBusiness.find(LocationType.OFFICE);
    	
    	countryCoteDivoire = localityBusiness.find(Locality.COUNTRY_COTE_DIVOIRE);
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
    
    /**/
    
    public static Installation fakeInstallation() {
    	Installation installation = new Installation();
    	installation.setAdministratorCredentials(new Credentials("admin", "123"));
    	installation.setApplication(new Application());
    	installation.getApplication().setName("app");
    	installation.setLicense(new License());
    	installation.getLicense().setPeriod(new Period(new Date(), new Date()));
    	installation.setManager(new Person("fn","ln"));
    	installation.setManagerCredentials(new Credentials("man", "123"));
    	installation.setFaked(Boolean.TRUE);
    	return installation;
    }
    
}
