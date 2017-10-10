package org.cyk.system.root.business.impl;

import java.io.Serializable;
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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.ClazzBusiness;
import org.cyk.system.root.business.api.ClazzBusiness.ClazzBusinessListener;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.TypedBusiness.CreateReportFileArguments;
import org.cyk.system.root.business.api.event.NotificationBusiness;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.event.NotificationBuilderAdapter;
import org.cyk.system.root.business.impl.file.FileValidator;
import org.cyk.system.root.business.impl.file.report.AbstractReportRepository;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.business.impl.network.UniformResourceLocatorBuilderAdapter;
import org.cyk.system.root.business.impl.party.person.PersonValidator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Clazz;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.model.event.Notification;
import org.cyk.system.root.model.event.Notification.RemoteEndPoint;
import org.cyk.system.root.model.event.NotificationTemplate;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptEvaluationEngine;
import org.cyk.system.root.model.file.report.AbstractReportTemplateFile;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.generator.StringValueGenerator;
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.generator.ValueGenerator.GenerateMethod;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.information.IdentifiableCollectionType;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.network.Computer;
import org.cyk.system.root.model.network.Service;
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
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRoleName;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.security.BusinessServiceCollection;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.Software;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.model.userinterface.UserInterfaceCommand;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuLocation;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNode;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNodeType;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuRenderType;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuType;
import org.cyk.system.root.model.value.Measure;
import org.cyk.system.root.model.value.MeasureType;
import org.cyk.system.root.model.value.NullString;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.event.NotificationTemplateDao;
import org.cyk.system.root.persistence.api.message.SmtpPropertiesDao;
import org.cyk.system.root.persistence.api.party.ApplicationDao;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.ClassHelper.Instanciation.Get;
import org.cyk.utility.common.helper.EventHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.InstanceHelper.Lookup.Source;
import org.cyk.utility.common.helper.ListenerHelper;
import org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod;
import org.cyk.utility.common.helper.MapHelper;
import org.cyk.utility.common.helper.StringHelper;

import lombok.Getter;
import lombok.Setter;

@Singleton
@Deployment(initialisationType=InitialisationType.EAGER,order=RootBusinessLayer.DEPLOYMENT_ORDER) @Getter
public class RootBusinessLayer extends AbstractBusinessLayer implements Serializable {
 
	public static final int DEPLOYMENT_ORDER = 0;
	private static final long serialVersionUID = 4576531258594638L;
	
	public static Class<? extends DataSet> DATA_SET_CLASS = RealDataSet.class;
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
        org.cyk.utility.common.cdi.annotation.Log.Interceptor.COLLECTION.add(new LogInterceptorAdapter() /*inject(LogInterceptorAdapter.class)*/);
        
        ClassHelper.Instanciation.Get.Adapter.Default.RESULT_METHOD_CLASS = (Class<ResultMethod<Object, Get<?>>>) ClassHelper.getInstance().getByName(org.cyk.system.root.business.impl.helper.ClassHelper.Instanciation.class);
        EventHelper.Event.Get.Datasource.CLASSES.add(org.cyk.system.root.business.impl.time.EventHelper.Get.class);
        
        InstanceHelper.Lookup.Source.Adapter.Default.RESULT_METHOD_CLASS = (Class<ListenerHelper.Executor.ResultMethod<Object, Source<?, ?>>>) ClassHelper.getInstance().getByName(org.cyk.system.root.business.impl.helper.InstanceHelper.Lookup.class);
        InstanceHelper.Pool.Listener.Adapter.Default.CLASSES.add(org.cyk.system.root.business.impl.helper.InstanceHelper.Pool.class);
        InstanceHelper.Listener.COLLECTION.add(new org.cyk.system.root.business.impl.helper.InstanceHelper.Listener());
        
        InstanceHelper.getInstance().setFieldValueGenerator(GlobalIdentifier.class, GlobalIdentifier.FIELD_IDENTIFIER, new InstanceHelper.Listener.FieldValueGenerator
    		.Adapter.Default<String>(String.class){
			private static final long serialVersionUID = 1L;
			@Override
			protected String __execute__(Object instance,String fieldName, Class<String> outputClass) {
				GlobalIdentifier globalIdentifier = (GlobalIdentifier) instance;
				StringBuilder stringBuilder = new StringBuilder();
    			if(globalIdentifier.getIdentifiable()==null){
    				
    			}else{
    				stringBuilder.append(globalIdentifier.getIdentifiable().getClass().getSimpleName()+Constant.CHARACTER_UNDESCORE);
    				if(StringUtils.isNotBlank(globalIdentifier.getCode())){
        				stringBuilder.append(globalIdentifier.getCode()+Constant.CHARACTER_UNDESCORE);
        			}
    			}
    			stringBuilder.append(System.currentTimeMillis()+Constant.CHARACTER_UNDESCORE+RandomStringUtils.randomAlphanumeric(10));
    			return stringBuilder.toString();
			}				
        });
        InstanceHelper.Stringifier.Label.Adapter.Default.DEFAULT_CLASS = org.cyk.system.root.business.impl.helper.InstanceHelper.Label.class;
        
        MapHelper.Stringifier.Entry.Adapter.Default.DEFAULT_LISTENER_CLASS = org.cyk.system.root.business.impl.helper.MapHelper.EntryStringifier.class;
        MapHelper.Stringifier.Adapter.Default.DEFAULT_MAP_LISTENER_CLASS = org.cyk.system.root.business.impl.helper.MapHelper.Listener.class;
		MapHelper.Stringifier.Entry.Adapter.Default.DEFAULT_MAP_LISTENER_CLASS = org.cyk.system.root.business.impl.helper.MapHelper.Listener.class;
        
        StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
		
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.system.root.model.language.i18n",LanguageBusinessImpl.class.getClassLoader());
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.system.root.model.language.word",LanguageBusinessImpl.class.getClassLoader());
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.system.root.model.language.entity",LanguageBusinessImpl.class.getClassLoader());
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.system.root.model.language.field",LanguageBusinessImpl.class.getClassLoader());
		
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.system.root.business.impl.language.ui",LanguageBusinessImpl.class.getClassLoader());
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.system.root.business.impl.language.misc",LanguageBusinessImpl.class.getClassLoader());
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.system.root.business.impl.language.exception",LanguageBusinessImpl.class.getClassLoader());
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.system.root.business.impl.language.validation",LanguageBusinessImpl.class.getClassLoader());
		
        ClazzBusiness.LISTENERS.add(new ClazzBusinessListener.Adapter() {
			private static final long serialVersionUID = 4056356640763766384L;
			@Override
			public void doSetUiLabel(Clazz clazz) {
				clazz.setUiLabel(languageBusiness.findText(clazz.getUiLabelId()));
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
        
        inject(RootGlobalIdentifierPersistenceMappingConfigurationsRegistrator.class).register();
        inject(RootFormattingConfigurationsRegistrator.class).register();
        
        AbstractIdentifiableBusinessServiceImpl.addAutoSetPropertyValueClass(new String[]{GlobalIdentifier.FIELD_CODE,GlobalIdentifier.FIELD_NAME}, ReportTemplate.class
        		,PersonRelationshipTypeRole.class,PersonRelationship.class,UniformResourceLocator.class,TimeDivisionType.class,UserInterfaceCommand.class
        		,UserInterfaceMenuNode.class,UserInterfaceMenuItem.class,UserAccount.class);
        
        AbstractIdentifiableBusinessServiceImpl.addAutoSetPropertyValueClass(new String[]{GlobalIdentifier.FIELD_CODE},ElectronicMailAddress.class, UniformResourceLocatorParameter.class
        		,UserInterfaceMenu.class,Credentials.class);
        
        rootBusinessTestHelper.setReportBusiness(reportBusiness);
        
        inject(ApplicationBusiness.class).registerValueGenerator((ValueGenerator<?, ?>) new StringValueGenerator<Party>(
        		ValueGenerator.GLOBAL_IDENTIFIER_CODE_IDENTIFIER,ValueGenerator.GLOBAL_IDENTIFIER_CODE_DESCRIPTION, Party.class));
        
        registerValidator(Person.class, inject(PersonValidator.class));
        registerValidator(File.class, inject(FileValidator.class));
        
        ValueGenerator<AbstractIdentifiable,String> globalIdentifierCodeGenerator = (ValueGenerator<AbstractIdentifiable, String>) 
        		inject(ApplicationBusiness.class).findValueGenerator(ValueGenerator.GLOBAL_IDENTIFIER_CODE_IDENTIFIER);
        globalIdentifierCodeGenerator.setMethod(new GlobalIdentifierCodeGenerator());
		
		/*AbstractIdentifiable.BUILD_GLOBAL_IDENTIFIER_VALUE = new StringMethod<AbstractIdentifiable>() {
			private static final long serialVersionUID = -206221150563679476L;
			@Override
			protected String __execute__(AbstractIdentifiable identifiable) {
				return identifiable.getClass().getSimpleName()+Constant.CHARACTER_UNDESCORE+System.currentTimeMillis()+Constant.CHARACTER_UNDESCORE
						+RandomStringUtils.randomAlphanumeric(10);
			}
		};*/
		
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
		/*
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
		};*/
		
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
		
		//defaultSmtpProperties = inject(SmtpPropertiesDao.class).read(RootConstant.Code.SmtpProperties.DEFAULT);
		
		AbstractGeneratable.Listener.COLLECTION.add(new AbstractGeneratable.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			@Override
			public Object format(Object object, Object fieldValue) {
				if(fieldValue instanceof Date)
					return inject(TimeBusiness.class).formatDate((Date)fieldValue);
				return super.format(object, fieldValue);
			}
			
			/*@Override
			public Locale getLocale() {
				return inject(LanguageBusiness.class).findCurrentLocale();
			}*/
		});
		
		//Configuration.get(SmtpProperties.class).setReadDefaulted(Boolean.TRUE);
        
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
    
    	/*DataSet dataSet = new DataSet(getClass());
    	
    	file(dataSet);
    	values(dataSet);
    	geography(dataSet);
        event(dataSet);
        time(dataSet);
        language(dataSet);
        party(dataSet);
        security(dataSet);
        
        network(dataSet);
        
        message(dataSet);
        mathematics(dataSet);
        information(dataSet);
        
        userInterface(dataSet);
        
        dataSet.instanciate();
    	dataSet.save();
    	*/
    	if(DATA_SET_CLASS==null)
    		;
    	else{
    		DataSet dataSet = ClassHelper.getInstance().instanciateOne(DATA_SET_CLASS);
        	dataSet.instanciate().save();	
    	}
    	
    }
    
    private void geography(DataSet dataSet){
    	dataSet.addClass(LocalityType.class);
    	dataSet.addClass(Locality.class);
        dataSet.addClass(Country.class);
        dataSet.addClass(PhoneNumberType.class);
        dataSet.addClass(LocationType.class);
        dataSet.addClass(ElectronicMailAddress.class);
    }
    
    private void language(DataSet dataSet){
    	dataSet.addClass(Language.class);
    }
    
    private void event(DataSet dataSet){ 
    	dataSet.addClass(NotificationTemplate.class);
    	dataSet.addClass(EventMissedReason.class);
    }
    
    private void file(DataSet dataSet){ 
    	dataSet.addClass(FileRepresentationType.class);
    	dataSet.addClass(ScriptEvaluationEngine.class);
    	dataSet.addClass(Script.class);
    }
    
    private void time(DataSet dataSet){ 
    	dataSet.addClass(TimeDivisionType.class);
    }
    
    private void party(DataSet dataSet){
    	dataSet.addClass(Sex.class);
    	dataSet.addClass(MaritalStatus.class);
    	dataSet.addClass(JobFunction.class);
    	dataSet.addClass(JobTitle.class);
    	dataSet.addClass(PersonTitle.class);
    	dataSet.addClass(BloodGroup.class);
    	dataSet.addClass(Allergy.class);
    	dataSet.addClass(Medication.class);
    	dataSet.addClass(PersonRelationshipTypeGroup.class);
    	dataSet.addClass(PersonRelationshipType.class);
    	dataSet.addClass(PersonRelationshipTypeRoleName.class);
    	dataSet.addClass(PersonRelationshipTypeRole.class);
    	
    }
    
    private void security(DataSet dataSet){ 
    	createRole(RootConstant.Code.Role.ADMINISTRATOR, "Administrator");
    	createRole(RootConstant.Code.Role.MANAGER, "Manager");
        createRole(RootConstant.Code.Role.SETTING_MANAGER, "Setting Manager");
        createRole(RootConstant.Code.Role.SECURITY_MANAGER, "Security Manager");
        createRole(RootConstant.Code.Role.USER, "User",SHIRO_PRIVATE_FOLDER);
        
        dataSet.addClass(Software.class);
        dataSet.addClass(Credentials.class);
        dataSet.addClass(BusinessServiceCollection.class);
    }
    
    private void network(DataSet dataSet){ 
    	dataSet.addClass(Computer.class);
        dataSet.addClass(Service.class);
    }
    
    private void message(DataSet dataSet){ 
    	dataSet.addClass(SmtpProperties.class);
    }
    
    private void mathematics(DataSet dataSet){ 
    	dataSet.addClass(IntervalCollection.class);
    	dataSet.addClass(Interval.class);
    	dataSet.addClass(MetricCollectionType.class);
    	dataSet.addClass(MovementAction.class);
    }
    
    private void values(DataSet dataSet){ 
    	dataSet.addClass(MeasureType.class);
    	dataSet.addClass(Measure.class);
    	dataSet.addClass(NullString.class);
    	dataSet.addClass(ValueProperties.class);
    	dataSet.addClass(Value.class);
    }
    
    private void information(DataSet dataSet){ 
    	dataSet.addClass(IdentifiableCollectionType.class);
    }
    
    private void userInterface(DataSet dataSet){ 
    	dataSet.addClass(UserInterfaceMenuRenderType.class);
    	dataSet.addClass(UserInterfaceMenuLocation.class);
    	dataSet.addClass(UserInterfaceMenuNodeType.class);
    	dataSet.addClass(UserInterfaceMenuType.class);
    }
    
    @Override
    protected void setConstants(){
    	Application application = inject(ApplicationDao.class).select().one();
    	if(application!=null)
    		applicationIdentifier = application.getIdentifier();
    	
    	RemoteEndPoint.USER_INTERFACE.alarmTemplate = inject(NotificationTemplateDao.class).read(RootConstant.Code.NotificationTemplate.ALARM_USER_INTERFACE);
    	RemoteEndPoint.MAIL_SERVER.alarmTemplate = inject(NotificationTemplateDao.class).read(RootConstant.Code.NotificationTemplate.ALARM_EMAIL);
    	RemoteEndPoint.PHONE.alarmTemplate = inject(NotificationTemplateDao.class).read(RootConstant.Code.NotificationTemplate.ALARM_SMS);
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
