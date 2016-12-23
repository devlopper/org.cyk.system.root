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
import org.cyk.system.root.business.api.ClazzBusiness;
import org.cyk.system.root.business.api.ClazzBusiness.ClazzBusinessListener;
import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.TypedBusiness.CreateReportFileArguments;
import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.api.value.MeasureBusiness;
import org.cyk.system.root.business.impl.event.NotificationBuilderAdapter;
import org.cyk.system.root.business.impl.file.FileValidator;
import org.cyk.system.root.business.impl.file.report.AbstractReportRepository;
import org.cyk.system.root.business.impl.network.UniformResourceLocatorBuilderAdapter;
import org.cyk.system.root.business.impl.party.person.PersonValidator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Clazz;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.RootConstant;
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
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.message.SmtpProperties;
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
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.model.value.Measure;
import org.cyk.system.root.model.value.MeasureType;
import org.cyk.system.root.model.value.NullString;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueSet;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;
import org.cyk.system.root.persistence.api.message.SmtpPropertiesDao;
import org.cyk.system.root.persistence.api.party.ApplicationDao;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration;
import org.cyk.system.root.persistence.impl.globalidentification.GlobalIdentifierPersistenceMappingConfiguration.Property;
import org.cyk.utility.common.AbstractMethod;
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
        registerFormatter(Value.class, new AbstractFormatter<Value>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(Value value, ContentType contentType) {
				if(value.get()==null && Boolean.TRUE.equals(value.getNullable()))
					return value.getNullString().getCode();
				switch(value.getType()){
				case BOOLEAN:
					return inject(LanguageBusiness.class).findResponseText(value.getBooleanValue().get());
				case NUMBER:
					if(value.getMeasure()==null)
						return inject(NumberBusiness.class).format(value.getNumberValue().get());
					return inject(NumberBusiness.class).format(inject(MeasureBusiness.class).computeQuotient(value.getMeasure(),value.getNumberValue().get()));
				case STRING:
					if(ValueSet.INTERVAL_RELATIVE_CODE.equals(value.getSet())){
						return value.getStringValue().get();
					}else
						return value.getStringValue().get();//TODO must depends on string value type
				}
				return null;
			}
		});
        registerFormatter(Metric.class, new AbstractFormatter<Metric>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(Metric metric, ContentType contentType) {
				return metric.getName()+(metric.getMeasure()==null ? Constant.EMPTY_STRING 
						:(Constant.CHARACTER_LEFT_PARENTHESIS+inject(FormatterBusiness.class).format(metric.getMeasure(), contentType)+Constant.CHARACTER_RIGHT_PARENTHESIS));
			}
		});
        registerFormatter(MetricValue.class, new AbstractFormatter<MetricValue>() {
			private static final long serialVersionUID = -4793331650394948152L;
			@Override
			public String format(MetricValue metricValue, ContentType contentType) {
				return inject(FormatterBusiness.class).format(metricValue.getMetric(), contentType)+Constant.CHARACTER_LEFT_PARENTHESIS
						+inject(FormatterBusiness.class).format(metricValue.getValue(), contentType)+Constant.CHARACTER_RIGHT_PARENTHESIS;
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
		
		defaultSmtpProperties = inject(SmtpPropertiesDao.class).read(RootConstant.Code.SmtpProperties.DEFAULT);
		
		AbstractIdentifiableBusinessServiceImpl.addAutoSetPropertyValueClass(new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME}, TimeDivisionType.class);
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
    
    @Override
    protected void persistStructureData() {
    	super.persistStructureData();
    	values();
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
        createFromExcelSheet(LocalityType.class);
        createFromExcelSheet(Locality.class);
        createFromExcelSheet(Country.class);
        createFromExcelSheet(PhoneNumberType.class);
        createFromExcelSheet(LocationType.class);
    }
    
    private void language(){
    	createFromExcelSheet(Language.class);
    }
    
    private void event(){ 
        notificationTemplate(NotificationTemplate.ALARM_USER_INTERFACE,"Alarm User Interface Notification Template","alarmUITitle.txt","alarmUIMessage.html");
        notificationTemplate(NotificationTemplate.ALARM_EMAIL,"Alarm Email Notification Template","alarmEmailTitle.txt","alarmEmailMessage.html");
        notificationTemplate(NotificationTemplate.ALARM_SMS,"Alarm Sms Notification Template","alarmSmsTitle.txt","alarmSmsMessage.html");
        
        createFromExcelSheet(EventMissedReason.class);
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
    	createFromExcelSheet(FileRepresentationType.class);
    }
    
    private void time(){ 
    	createFromExcelSheet(TimeDivisionType.class);
    }
    
    private void party(){
    	createFromExcelSheet(Sex.class);
    	createFromExcelSheet(MaritalStatus.class);
    	createFromExcelSheet(JobFunction.class);
    	createFromExcelSheet(JobTitle.class);
    	createFromExcelSheet(PersonTitle.class);
    	createFromExcelSheet(BloodGroup.class);
    	createFromExcelSheet(Allergy.class);
    	createFromExcelSheet(Medication.class);
    	createFromExcelSheet(PersonRelationshipTypeGroup.class);
    	createFromExcelSheet(PersonRelationshipType.class);
    }
    
    private void security(){ 
    	createRole(Role.ADMINISTRATOR, "Administrator");
    	createRole(Role.MANAGER, "Manager");
        createRole(Role.SETTING_MANAGER, "Setting Manager");
        createRole(Role.SECURITY_MANAGER, "Security Manager");
        createRole(Role.USER, "User",SHIRO_PRIVATE_FOLDER);
        
        createFromExcelSheet(BusinessServiceCollection.class);
    }
    
    private void message(){ 
    	createFromExcelSheet(SmtpProperties.class);
    }
    
    private void mathematics(){ 
    	createFromExcelSheet(MetricCollectionType.class);
    }
    
    private void values(){ 
    	createFromExcelSheet(MeasureType.class);
    	createFromExcelSheet(Measure.class);
    	createFromExcelSheet(NullString.class);
    }
    
    @Override
    protected void setConstants(){
    	Application application = inject(ApplicationDao.class).select().one();
    	if(application!=null)
    		applicationIdentifier = application.getIdentifier();
    	
    	RemoteEndPoint.USER_INTERFACE.alarmTemplate = inject(NotificationTemplateDao.class).read(NotificationTemplate.ALARM_USER_INTERFACE);
    	RemoteEndPoint.MAIL_SERVER.alarmTemplate = inject(NotificationTemplateDao.class).read(NotificationTemplate.ALARM_EMAIL);
    	RemoteEndPoint.PHONE.alarmTemplate = inject(NotificationTemplateDao.class).read(NotificationTemplate.ALARM_SMS);
    	setDefaultSmtpProperties(inject(SmtpPropertiesDao.class).read(RootConstant.Code.SmtpProperties.DEFAULT));
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
