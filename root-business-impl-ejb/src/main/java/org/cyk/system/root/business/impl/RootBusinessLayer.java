package org.cyk.system.root.business.impl;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.Column;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.business.api.ClazzBusiness;
import org.cyk.system.root.business.api.ClazzBusiness.ClazzBusinessListener;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.TypedBusiness.CreateReportFileArguments;
import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.geography.CountryBusiness;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.event.NotificationBuilderAdapter;
import org.cyk.system.root.business.impl.file.FileValidator;
import org.cyk.system.root.business.impl.file.report.AbstractReportRepository;
import org.cyk.system.root.business.impl.network.UniformResourceLocatorBuilderAdapter;
import org.cyk.system.root.business.impl.party.person.PersonValidator;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Clazz;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;
import org.cyk.system.root.model.generator.StringValueGenerator;
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.generator.ValueGenerator.GenerateMethod;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.MetricValueInputted;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.message.SmtpSocketFactory;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Allergy;
import org.cyk.system.root.model.party.person.BloodGroup;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Medication;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.model.security.BusinessServiceCollection;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;
import org.cyk.system.root.persistence.api.geography.LocalityTypeDao;
import org.cyk.system.root.persistence.api.message.SmtpPropertiesDao;
import org.cyk.system.root.persistence.api.party.ApplicationDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeGroupDao;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration.Property;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.CommonUtils.ReadExcelSheetArguments;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.StringMethod;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.computation.DataReadConfiguration;

import lombok.Getter;
import lombok.Setter;

@Singleton
@Deployment(initialisationType=InitialisationType.EAGER,order=RootBusinessLayer.DEPLOYMENT_ORDER) @Getter
public class RootBusinessLayer extends AbstractBusinessLayer implements Serializable {
 
	public static final int DEPLOYMENT_ORDER = 0;
	private static final long serialVersionUID = 4576531258594638L;
	
	public static final Collection<Class<?>> GLOBAL_IDENTIFIER_UNBUILDABLE_CLASSES = new HashSet<>();
	
	private static RootBusinessLayer INSTANCE;
	
	private Timer alarmTimer;
	
	private final String parameterGenericReportBasedOnDynamicBuilder = "grbodb"; 
	private final String parameterGenericDashBoardReport = "gdbr"; 
	private final String parameterFromDate = "fd"; 
	private final String parameterToDate = "td"; 
	
	private final String actionPrint = "print";
	
	@Inject private RootBusinessTestHelper rootBusinessTestHelper;
    
    private Application application;
    @Setter private Long applicationIdentifier;
    @Setter @Getter private SmtpProperties defaultSmtpProperties;
    
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
        
        GlobalIdentifierPersistenceMappingConfiguration configuration = new GlobalIdentifierPersistenceMappingConfiguration();
        Property property = new Property(commonUtils.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER, GlobalIdentifier.FIELD_CODE),new Column() {
			@Override public Class<? extends Annotation> annotationType() {return null;}
			@Override public boolean updatable() {return false;}	
			@Override public boolean unique() {return Boolean.TRUE;}
			@Override public String table() {return null;}
			@Override public int scale() {return 0;}
			@Override public int precision() {return 0;}
			@Override public boolean nullable() {return false;}
			@Override public String name() {return null;}
			@Override public int length() {return 0;}
			@Override public boolean insertable() {return false;}
			@Override public String columnDefinition() {return null;}
		});
        configuration.addProperties(property);
        GlobalIdentifierPersistenceMappingConfiguration.register(Person.class, configuration);
        
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
        
        registerFormatter(IntervalExtremity.class, new AbstractFormatter<IntervalExtremity>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(IntervalExtremity intervalExtremity, ContentType contentType) {
				String marker = Boolean.TRUE.equals(intervalExtremity.getExcluded()) ? (Boolean.TRUE.equals(intervalExtremity.getIsLow())?"]":"[")
						:(Boolean.TRUE.equals(intervalExtremity.getIsLow())?"[":"]")
						,number=intervalExtremity.getValue()==null ? IntervalExtremity.INFINITE : inject(NumberBusiness.class).format(intervalExtremity.getValue());
				return String.format(IntervalExtremity.FORMAT, Boolean.TRUE.equals(intervalExtremity.getIsLow())?marker:number
						,Boolean.TRUE.equals(intervalExtremity.getIsLow())?number:marker);
			}
		});
        registerFormatter(Interval.class, new AbstractFormatter<Interval>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(Interval interval, ContentType contentType) {
				return String.format(Interval.FORMAT,formatterBusiness.format(interval.getLow()),formatterBusiness.format(interval.getHigh()));
			}
		});
        registerFormatter(MetricValue.class, new AbstractFormatter<MetricValue>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(MetricValue metricValue, ContentType contentType) {
				String value = null;
				switch(metricValue.getMetric().getCollection().getValueType()){
				case NUMBER:
					value = inject(NumberBusiness.class).format(metricValue.getNumberValue().get());
					break;
				case STRING:
					if(MetricValueInputted.VALUE_INTERVAL_CODE.equals(metricValue.getMetric().getCollection().getValueInputted()))
						value = RootBusinessLayer.getInstance().getRelativeCode(metricValue.getMetric().getCollection(), metricValue.getStringValue());
					else
						value = metricValue.getStringValue();//TODO must depends on string value type
					break;
				}
				return value;
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
				if(StringUtils.isBlank(file.getCode()))
					if(StringUtils.isBlank(file.getName()))
						return file.getUiString();
					else
						return file.getName();
				else
					if(StringUtils.isBlank(file.getName()))
						return file.getCode();
					else
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
        
        registerValidator(Person.class, inject(PersonValidator.class));
        registerValidator(File.class, inject(FileValidator.class));
        
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
				return !ArrayUtils.contains(new Class<?>[]{/*Application.class,NestedSet.class,NestedSetNode.class*/}, identifiable.getClass()) && 
						!GLOBAL_IDENTIFIER_UNBUILDABLE_CLASSES.contains(identifiable.getClass());
			}
		};
		
		Notification.Builder.Listener.COLLECTION.add(NotificationBuilderAdapter.DEFAULT);
		UniformResourceLocator.Builder.Listener.COLLECTION.add(UniformResourceLocatorBuilderAdapter.DEFAULT);
		TypedBusiness.CreateReportFileArguments.Builder.Listener.COLLECTION.add(new CreateReportFileArgumentsAdapter());
		
		defaultSmtpProperties = inject(SmtpPropertiesDao.class).read(SmtpProperties.DEFAULT);
    }
    
    
    
    public GenericBusiness getGenericBusiness(){
    	return inject(GenericBusiness.class);
    }
    
    public GenericDao getGenericDao(){
    	return inject(GenericDao.class);
    }
    
    @Override
    protected AbstractReportRepository getReportRepository() {
    	return inject(RootReportRepository.class);
    }
    
    /*@Override
    protected RootReportProducer getReportProducer() {
    	return inject(RootReportProducer.class);
    }*/
    
    @Override
    protected void persistData() {
    	geography();
        event();
        time();
        language();
        party();
        security();
        file();
        message();
        mathematics();
    }
    
    private void geography(){
        create(new LocalityType(null, LocalityType.CONTINENT, "Continent"));
        create(new LocalityType(inject(LocalityTypeDao.class).read(LocalityType.CONTINENT), LocalityType.COUNTRY, "Country"));
        create(new LocalityType(inject(LocalityTypeDao.class).read(LocalityType.COUNTRY), LocalityType.CITY, "City"));
        
        create(new Locality(null, inject(LocalityTypeDao.class).read(LocalityType.CONTINENT), Locality.AFRICA,"Africa"));
        create(new Locality(null, inject(LocalityTypeDao.class).read(LocalityType.CONTINENT), Locality.AMERICA,"America"));
        create(new Locality(null, inject(LocalityTypeDao.class).read(LocalityType.CONTINENT), Locality.EUROPE,"Europe"));
        create(new Locality(null, inject(LocalityTypeDao.class).read(LocalityType.CONTINENT), Locality.ASIA,"Asia"));
        create(new Locality(null, inject(LocalityTypeDao.class).read(LocalityType.CONTINENT), Locality.AUSTRALIA,"Australia"));
        
        ReadExcelSheetArguments readExcelSheetArguments;
		List<String[]> list;
		try {
			readExcelSheetArguments = new ReadExcelSheetArguments();
			readExcelSheetArguments.setWorkbookBytes(IOUtils.toByteArray(getClass().getResourceAsStream("geography/countries.xls")));
	    	readExcelSheetArguments.setSheetIndex(0);
	    	list = commonUtils.readExcelSheet(readExcelSheetArguments);
			inject(CountryBusiness.class).create(inject(CountryBusiness.class).instanciateMany(list));
			
			readExcelSheetArguments = new ReadExcelSheetArguments();
			readExcelSheetArguments.setWorkbookBytes(IOUtils.toByteArray(getClass().getResourceAsStream("geography/cities.xls")));
	    	readExcelSheetArguments.setSheetIndex(0);
	    	list = commonUtils.readExcelSheet(readExcelSheetArguments);
			inject(LocalityBusiness.class).create(inject(LocalityBusiness.class).instanciateMany(inject(LocalityTypeDao.class).read(LocalityType.CITY),list));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        create(new PhoneNumberType(PhoneNumberType.LAND, "Fixe"));
        create(new PhoneNumberType(PhoneNumberType.MOBILE, "Mobile"));
        
        create(new LocationType(LocationType.HOME, "Domicile"));
        create(new LocationType(LocationType.OFFICE, "Bureau"));
        
    }
    
    private void language(){
    	ReadExcelSheetArguments readExcelSheetArguments;
		List<String[]> list;
		try {
			readExcelSheetArguments = new ReadExcelSheetArguments();
			readExcelSheetArguments.setWorkbookBytes(IOUtils.toByteArray(getClass().getResourceAsStream("language/languages.xls")));
	    	readExcelSheetArguments.setSheetIndex(0);
	    	list = commonUtils.readExcelSheet(readExcelSheetArguments);
			inject(LanguageBusiness.class).create(inject(LanguageBusiness.class).instanciateMany(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
        	notificationTemplate.setTitle(inject(FileBusiness.class).process(IOUtils.toByteArray(getClass().getResourceAsStream(titleFileFolder+"/"+titleFileName)), titleFileName));
        	notificationTemplate.setMessage(inject(FileBusiness.class).process(IOUtils.toByteArray(getClass().getResourceAsStream(bodyFileFolder+"/"+bodyFileName)), bodyFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
        create(notificationTemplate);
    }
    
    private void notificationTemplate(String code,String name,String titleFileName,String bodyFileName){
    	notificationTemplate(code, name, "template", titleFileName, "template", bodyFileName);
    }
    
    private void file(){ 
    	createEnumeration(FileRepresentationType.class,FileRepresentationType.IDENTITY_IMAGE);
        createEnumeration(FileRepresentationType.class,FileRepresentationType.IDENTITY_DOCUMENT);
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
    	createEnumeration(Sex.class,Sex.FEMALE, "Féminin");
    	createEnumeration(MaritalStatus.class,"B", "Célibataire");
    	createEnumeration(MaritalStatus.class,"M", "Marié");
        
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
        
        createEnumeration(BloodGroup.class,"A");
        createEnumeration(BloodGroup.class,"B");
        createEnumeration(BloodGroup.class,"AB");
        createEnumeration(BloodGroup.class,"O");
        
        createEnumeration(Allergy.class,"FOOD");
        createEnumeration(Allergy.class,"INSECT");
        createEnumeration(Allergy.class,"MEDICINE");
     
        createEnumeration(Medication.class,"Acetaminophen");
        createEnumeration(Medication.class,"Adderall");
        createEnumeration(Medication.class,"Alprazolam");
        
        createEnumeration(PersonRelationshipTypeGroup.class,PersonRelationshipTypeGroup.FAMILY);
        createEnumeration(PersonRelationshipTypeGroup.class,PersonRelationshipTypeGroup.SOCIETY);
        
        create(new PersonRelationshipType(null, inject(PersonRelationshipTypeGroupDao.class).read(PersonRelationshipTypeGroup.FAMILY), PersonRelationshipType.FAMILY_FATHER,"Père"));
        create(new PersonRelationshipType(null, inject(PersonRelationshipTypeGroupDao.class).read(PersonRelationshipTypeGroup.FAMILY), PersonRelationshipType.FAMILY_MOTHER,"Mère"));
        
        create(new PersonRelationshipType(null, inject(PersonRelationshipTypeGroupDao.class).read(PersonRelationshipTypeGroup.SOCIETY)
        		, PersonRelationshipType.SOCIETY_DOCTOR,"Docteur"));
        create(new PersonRelationshipType(null, inject(PersonRelationshipTypeGroupDao.class).read(PersonRelationshipTypeGroup.SOCIETY)
        		, PersonRelationshipType.SOCIETY_TO_CONTACT_IN_EMERGENCY_CASE,"Personne à contacter en cas d'urgence"));
    }
    
    private void security(){ 
    	//Permission licenceRead = createPermission(permissionBusiness.computeCode(License.class, Crud.READ));
    	
    	createRole(Role.ADMINISTRATOR, "Administrator");
    	createRole(Role.MANAGER, "Manager");
        createRole(Role.SETTING_MANAGER, "Setting Manager");
        createRole(Role.SECURITY_MANAGER, "Security Manager");
        createRole(Role.USER, "User",SHIRO_PRIVATE_FOLDER);
        
        for(String code : new String[]{BusinessServiceCollection.EVENT,BusinessServiceCollection.FILE,BusinessServiceCollection.GEOGRAPHY
        		,BusinessServiceCollection.INFORMATION,BusinessServiceCollection.LANGUAGE,BusinessServiceCollection.MATHEMATICS,BusinessServiceCollection.MESSAGE
        		,BusinessServiceCollection.NETWORK,BusinessServiceCollection.PARTY,BusinessServiceCollection.SECURITY,BusinessServiceCollection.TIME
        		,BusinessServiceCollection.TREE})
        	createEnumeration(BusinessServiceCollection.class,code);
    }
    
    private void message(){ 
    	SmtpProperties smtpProperties = new SmtpProperties();
    	smtpProperties.setCode(SmtpProperties.DEFAULT);
    	smtpProperties.setFrom(PersistDataListener.Adapter.process(SmtpProperties.class, SmtpProperties.DEFAULT,SmtpProperties.FIELD_FROM, "yoursender@email.here"));
    	smtpProperties.setCredentials(new Credentials(PersistDataListener.Adapter.process(SmtpProperties.class, SmtpProperties.DEFAULT
    			,commonUtils.attributePath(SmtpProperties.FIELD_CREDENTIALS,Credentials.FIELD_USERNAME), "yourusername")
    			,PersistDataListener.Adapter.process(SmtpProperties.class, SmtpProperties.DEFAULT
    			,commonUtils.attributePath(SmtpProperties.FIELD_CREDENTIALS,Credentials.FIELD_PASSWORD), "yourpassword") ));
    	smtpProperties.setHost(PersistDataListener.Adapter.process(SmtpProperties.class, SmtpProperties.DEFAULT,SmtpProperties.FIELD_HOST, "yourhost"));
    	smtpProperties.setPort(PersistDataListener.Adapter.process(SmtpProperties.class, SmtpProperties.DEFAULT,SmtpProperties.FIELD_PORT, -9999));
    	
		smtpProperties.setSocketFactory(new SmtpSocketFactory());
		smtpProperties.getSocketFactory().setClazz("javax.net.ssl.SSLSocketFactory");
		smtpProperties.getSocketFactory().setFallback(Boolean.FALSE);
		smtpProperties.getSocketFactory().setPort(-9999);
		smtpProperties.setAuthenticated(Boolean.TRUE);
		
    	create(smtpProperties);
    }
    
    private void mathematics(){ 
    	createEnumeration(MetricCollectionType.class,MetricCollectionType.ATTENDANCE);
    	createEnumeration(MetricCollectionType.class,MetricCollectionType.BEHAVIOUR);
    }
    
    @Override
    protected void setConstants(){
    	Application application = inject(ApplicationDao.class).select().one();
    	if(application!=null)
    		applicationIdentifier = application.getIdentifier();
    	
    	RemoteEndPoint.USER_INTERFACE.alarmTemplate = inject(NotificationTemplateDao.class).read(NotificationTemplate.ALARM_USER_INTERFACE);
    	RemoteEndPoint.MAIL_SERVER.alarmTemplate = inject(NotificationTemplateDao.class).read(NotificationTemplate.ALARM_EMAIL);
    	RemoteEndPoint.PHONE.alarmTemplate = inject(NotificationTemplateDao.class).read(NotificationTemplate.ALARM_SMS);
    	setDefaultSmtpProperties(inject(SmtpPropertiesDao.class).read(SmtpProperties.DEFAULT));
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
    	
	public String getRelativeCode(AbstractCollection<?> collection,String code){
		logTrace("Get relative code. {} , code={}", collection.getLogMessage(),code);
		return AbstractCollectionItemBusinessImpl.getRelativeCode(collection, code);
	}
	
	/**/
	
	public static class GlobalIdentifierCodeGenerator implements GenerateMethod<AbstractIdentifiable, String> {

		@Override
		public String execute(AbstractIdentifiable input) {
			String generatedCode = null;
			
			for(Listener listener : Listener.COLLECTION){
				String value = listener.generateGlobalIdentifierCode(input,null);
				if(value!=null)
					generatedCode = value;
			}
			if(generatedCode==null)
				generatedCode = RandomStringUtils.randomAlphabetic(6);
			else{
				do{
					TypedBusiness<AbstractIdentifiable> business =  null;
					for(Listener listener : Listener.COLLECTION){
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
						for(Listener listener : Listener.COLLECTION){
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
	
	public interface Listener extends AbstractBusinessLayer.Listener {

		Collection<Listener> COLLECTION = new ArrayList<>();
		
		String generateGlobalIdentifierCode(AbstractIdentifiable identifiable,String previousCode);
		
		<IDENTIFIABLE extends AbstractIdentifiable> TypedBusiness<IDENTIFIABLE> findBusiness(IDENTIFIABLE identifiable);
		
		void populateCandidateRoles(List<Role> roles);
		
		<REPORT extends AbstractReportTemplateFile<REPORT>> Class<REPORT> getReportTemplateFileClass(CreateReportFileArguments<?> arguments);
		
		/**/
		
		public static class Adapter extends AbstractBusinessLayer.Listener.Adapter implements Listener,Serializable {
			
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
			
			@Override
			public <REPORT extends AbstractReportTemplateFile<REPORT>> Class<REPORT> getReportTemplateFileClass(CreateReportFileArguments<?> arguments) {
				return null;
			}
			/**/
			
			@Deprecated
			public static class Default extends Listener.Adapter implements Serializable {

				private static final long serialVersionUID = 3580112506828375899L;
				
				@Override
				public <IDENTIFIABLE extends AbstractIdentifiable> TypedBusiness<IDENTIFIABLE> findBusiness(IDENTIFIABLE identifiable) {
					return BusinessInterfaceLocator.getInstance().injectTypedByObject(identifiable);
				}
				
			}
		}		
	}

    
}
