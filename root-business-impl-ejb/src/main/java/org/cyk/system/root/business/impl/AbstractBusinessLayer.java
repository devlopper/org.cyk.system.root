package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.datasource.DataSource;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.security.CredentialsBusiness;
import org.cyk.system.root.business.api.security.PermissionBusiness;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.datasource.JdbcDataSource;
import org.cyk.system.root.business.impl.file.report.AbstractReportRepository;
import org.cyk.system.root.business.impl.file.report.jasper.JasperReportBusinessImpl;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.business.impl.validation.FieldValidatorMethod;
import org.cyk.system.root.business.impl.validation.ValidatorMap;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.generator.StringGenerator;
import org.cyk.system.root.model.generator.StringValueGeneratorConfiguration;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.Permission;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.AbstractLayer;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.InstanceHelper;

public abstract class AbstractBusinessLayer extends AbstractLayer<AbstractIdentifiableBusinessServiceImpl<?>> implements BusinessLayer, Serializable {
    
	private static final long serialVersionUID = -4484371129296972868L;
	
	protected static final String PRIVATE_FOLDER_NAME = "private";
	protected static final String PRIVATE_ROLE_FOLDER_NAME = "private/__role__";
	
	protected static final String SHIRO_PRIVATE_FOLDER_FORMAT = "/%s/**";
	protected static final String SHIRO_PRIVATE_FOLDER = String.format(SHIRO_PRIVATE_FOLDER_FORMAT, PRIVATE_FOLDER_NAME);
	
	protected static final String SHIRO_ROLE_FOLDER_FORMAT = "/"+PRIVATE_ROLE_FOLDER_NAME+"/__%s__/**";
	protected static final String SHIRO_ROLE_FILE_FORMAT = "/"+PRIVATE_ROLE_FOLDER_NAME+"/__%s__/%s";
	
	protected static final String[] RESOURCE_BUNDLE_FORMATS = {
			"org.cyk.system.%s.model.resources.entity",
			"org.cyk.system.%s.model.resources.message",
			"org.cyk.system.%s.business.impl.resources.message"
	}; 
	
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected GenericBusiness genericBusiness;
    @Inject protected BusinessInterfaceLocator businessInterfaceLocator;
    @Inject protected BusinessManager businessManager;
    @Inject protected LanguageBusiness languageBusiness;
    @Inject @Getter protected JasperReportBusinessImpl reportBusiness;
    @Inject protected PermissionBusiness permissionBusiness;
    @Inject @Getter protected FormatterBusiness formatterBusiness;
    
    @Inject protected RootDataProducerHelper rootDataProducerHelper;
    
    protected ValidatorMap validatorMap = ValidatorMap.getInstance();
    //@Getter protected Collection<BusinessLayerListener> businessLayerListeners = new ArrayList<>();
    
    @Override
    protected void initialisation() {
        super.initialisation();
        id = this.getClass().getName();
        systemIdentifier = StringUtils.split(this.getClass().getName(), Constant.CHARACTER_DOT)[3];
        registerResourceBundles(systemIdentifier);
        //registerTypedBusinessBean(businessInterfaceLocator.getTypedBusinessBeanMap());
        
        rootDataProducerHelper.setBasePackage(this.getClass().getPackage());
        
    }
    
    @Override
    protected void afterInitialisation() {
    	super.afterInitialisation();
    	setConstants();
    	AbstractReportRepository reportRepository = getReportRepository();
    	if(reportRepository!=null)
    		reportRepository.build();
    }
    
    @Override
    public void createInitialData(Boolean runFakeTransactions) {
    	rootDataProducerHelper.setBasePackage(getClass().getPackage());
    	persistData();
    	setConstants();
    	if(Boolean.TRUE.equals(runFakeTransactions))
    		fakeTransactions();
    }
    
    protected void persistData(){
    	persistStructureData();
    	
    	persistSecurityData();
    	if(RootDataProducerHelper.getInstance().getUniformResourceLocators()!=null){
    		System.out.println("Uniform resource locators : "+RootDataProducerHelper.getInstance().getUniformResourceLocators().size());
    		inject(UniformResourceLocatorBusiness.class).create(RootDataProducerHelper.getInstance().getUniformResourceLocators());
    		RootDataProducerHelper.getInstance().setUniformResourceLocators(null);
    	}
    	if(RootDataProducerHelper.getInstance().getRoleUniformResourceLocators()!=null){
    		System.out.println("Role uniform resource locators : "+RootDataProducerHelper.getInstance().getRoleUniformResourceLocators().size());
    		inject(RoleUniformResourceLocatorBusiness.class).create(RootDataProducerHelper.getInstance().getRoleUniformResourceLocators());
    		RootDataProducerHelper.getInstance().setRoleUniformResourceLocators(null);
    	}
    	if(RootDataProducerHelper.getInstance().getUserAccounts()!=null){
    		System.out.println("User accounts : "+RootDataProducerHelper.getInstance().getUserAccounts().size());
    		inject(UserAccountBusiness.class).create(RootDataProducerHelper.getInstance().getUserAccounts());	
    		RootDataProducerHelper.getInstance().setUserAccounts(null);
    	}
    	
    }
    protected void persistStructureData(){
    	
    }
    protected void persistSecurityData(){
    	
    }
    
    public void enableEnterpriseResourcePlanning(){
		
	}
    
    protected abstract void setConstants();
    protected abstract void fakeTransactions();
    
    protected void registerResourceBundles(String systemName){
    	for(String format : RESOURCE_BUNDLE_FORMATS)
    		registerResourceBundle(String.format(format, systemName), getClass().getClassLoader());
    }
    
	@Override
	public void registerTypedBusinessBean(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> arg0) {}

	protected <T extends AbstractIdentifiable> T read(Class<T> aClass,String code){
		return inject(GenericDao.class).read(aClass, code);
	}
	
    /* shortcut methods */
	 
    protected void registerResourceBundle(String id, ClassLoader aClassLoader) {
		languageBusiness.registerResourceBundle(id, aClassLoader);
	}
    
    protected <T> void registerValidator(Class<T> clazz,AbstractValidator<T> validator){
        validatorMap.registerValidator(clazz, validator);
    }
    
    protected void registerFieldValidator(Field field,FieldValidatorMethod method){
        validatorMap.registerFieldValidator(field, method);
    }
    
    protected DataSource createShiroDataSource(){
    	return new JdbcDataSource();
    }

	protected <MODEL, REPORT extends AbstractReport<?>> void registerReportConfiguration(AbstractReportConfiguration<MODEL, REPORT> configuration) {
		reportBusiness.registerConfiguration(configuration);
	}
	
	protected AbstractReportRepository getReportRepository(){
		return null;
	}
	
	//protected abstract RootReportProducer getReportProducer();
	
	protected <T> void registerFormatter(Class<T> aClass,AbstractFormatter<T> formatter){
		formatterBusiness.registerFormatter(aClass, formatter);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Installation buildInstallation() {
		Installation installation = new Installation();
    	installation.setAdministratorCredentials(inject(CredentialsBusiness.class).instanciateOne("admin", "123"));
    	installation.setApplication(new Application());
    	installation.getApplication().setName("Application");
    	installation.setLicense(new License());
    	//installation.getLicense().setPeriod(new Period(new Date(), new Date()));
    	installation.setManager(new Person("ManagerFirstName","ManagerLastName"));
    	installation.setManagerCredentials(inject(CredentialsBusiness.class).instanciateOne("manager", "123"));
    	return installation;
	}
	
	@Override
	public void installApplication(Installation installation) {
		//for(BusinessLayerListener listener : businessLayerListeners)
		//	listener.beforeInstall(this, installation);
		applicationBusiness.install(installation);
		//for(BusinessLayerListener listener : businessLayerListeners)
		//	listener.afterInstall(this, installation);
	}
	
	@Override
	public void installApplication() {
		installApplication(buildInstallation());	
	}
	
	@Override
	public void installApplication(Boolean fake) {
		Installation installation = buildInstallation();
		installation.setFaked(fake);
		installApplication(installation);
	}
	
	protected void handleObjectToInstall(Object object){
		//for(BusinessLayerListener listener : businessLayerListeners)
		//	listener.handleObjectToInstall(this, object);
	}
	
	protected <T extends AbstractIdentifiable> void installObject(Integer identifier,String message,TypedBusiness<T> business,T object){
		handleObjectToInstall(object);
		business.create(object);
		logDebug(message);
	}
	protected void installObject(Integer identifier,String message,AbstractIdentifiable object){
		installObject(identifier,message, businessInterfaceLocator.injectTypedByObject(object),object);
	}
	protected <T extends AbstractIdentifiable> void installObject(Integer identifier,TypedBusiness<T> business,T object){
		installObject(identifier,"Instance of "+object.getClass().getSimpleName()+" created", business, object);
	}
	protected <T extends AbstractIdentifiable> void installObject(Integer identifier,AbstractIdentifiable object){
		installObject(identifier,businessInterfaceLocator.injectTypedByObject(object),object);
	}
	
	protected Permission createPermission(String code){
		return create(createPermissionInstance(code));
	}
	
	protected Permission createPermissionInstance(String code){
		return new Permission(code);
	}
    
	protected void createRole(Role role, String workspaceId, Permission... permissions) {
		if (permissions != null)
			for (Permission permission : permissions)
				role.getPermissions().add(permission);
		create(role);
		//createRoleSecuredView(role.getCode(), role, workspaceId);
	}
	
	protected void createRole(String code,String name,String[] viewIds) {
		Role role = new Role(code, name);
		create(role);
		//for(String viewId : viewIds)
		//	createRoleSecuredView(role.getCode(), role, viewId);
	}
    
	protected void createRole(String code,String name,String workspaceId,String...permissionCodes){
		Role role = new Role(code, name);
		Collection<Permission> permissions = new ArrayList<>();
		if(permissionCodes!=null)
			for(String permissionCode : permissionCodes){
				Permission permission = createPermission(permissionCode);
				permissions.add(permission);
			}
		createRole(role,workspaceId, permissions.toArray(new Permission[]{}));
	}
	
	protected void createRole(String code,String name){
		createRole(code, name, String.format(SHIRO_ROLE_FOLDER_FORMAT, StringUtils.lowerCase(StringUtils.remove(code, "_"))));
	}
	
	protected Role createRoleInstance(String code,String name,String...permissionCodes){
		Role role = new Role(code, name);
		if(permissionCodes!=null)
			for(String permissionCode : permissionCodes)
				role.getPermissions().add(createPermissionInstance(permissionCode));
		return role;
	}
	
	/*
	@SuppressWarnings("unchecked")
	protected void createRole(String code,String name,Object[][] entityCruds){
		String[] permissions = new String[entityCruds.length];
		for(int i=0;i<permissions.length;i++)
			permissions[i] = permissionBusiness.computeCode((Class<AbstractIdentifiable>)entityCruds[i][0], (Crud)entityCruds[i][1]);
	}
	*/
        
    protected Metric createMetric(String code,String name){
    	Metric metric = new Metric();
    	metric.setCode(code);
    	metric.setName(name);
    	return create(metric);
    }
        
    protected StringGenerator stringGenerator(String leftPrefix,String leftPattern,Long leftLenght,String rightPattern,Long rightLenght,Long lenght){
    	StringGenerator stringGenerator = new StringGenerator();
    	stringGenerator.setConfiguration(new StringValueGeneratorConfiguration());
    	stringGenerator.getConfiguration().getLeftPadding().setPrefix(leftPrefix);
    	stringGenerator.getConfiguration().getLeftPadding().setPattern(leftPattern);
    	stringGenerator.getConfiguration().getLeftPadding().setLenght(leftLenght);
    	
    	stringGenerator.getConfiguration().getRightPadding().setPattern(rightPattern);
    	stringGenerator.getConfiguration().getRightPadding().setLenght(rightLenght);
    	
    	stringGenerator.getConfiguration().setLenght(lenght);
    	
    	return stringGenerator;
    }
    
    protected StringGenerator stringGenerator(String script,String name){
    	StringGenerator stringGenerator = new StringGenerator();
    	stringGenerator.setScript(script(script, script));
    	return stringGenerator;
    }
    
    protected Script script(String text,String name){
    	Script script = new Script();
    	script.setFile(inject(FileBusiness.class).process(text.getBytes(), name+".txt"));
    	return script;
    }

    /* Data creation */
    
    @Deprecated
    public <T extends AbstractEnumeration> T createEnumeration(Class<T> aClass,String code, String name) {
		return rootDataProducerHelper.createEnumeration(aClass, code, name);
	}

    @Deprecated
	public <T extends AbstractEnumeration> T createEnumeration(Class<T> aClass,String name) {
		return rootDataProducerHelper.createEnumeration(aClass, name);
	}
	
    @Deprecated
	public <T extends AbstractEnumeration> void createEnumerations(Class<T> aClass,String...names) {
		for(String name : names)
			rootDataProducerHelper.createEnumeration(aClass, name);
	}
	
    @Deprecated
	public <T extends AbstractEnumeration> T updateEnumeration(Class<T> aClass,String code,String name){
		return rootDataProducerHelper.updateEnumeration(aClass, code, name);
	}

	public IntervalCollection createIntervalCollection(String code, String[][] values, String codeSeparator,Boolean create) {
		return rootDataProducerHelper.createIntervalCollection(code, values, codeSeparator, create);
	}

	public IntervalCollection createIntervalCollection(String code, String[][] values, String codeSeparator) {
		return rootDataProducerHelper.createIntervalCollection(code, values, codeSeparator);
	}

	public IntervalCollection createIntervalCollection(String code, String[][] values) {
		return rootDataProducerHelper.createIntervalCollection(code, values);
	}

	public <T extends AbstractIdentifiable> T create(T object) {
		return rootDataProducerHelper.create(object);
	}
	public <T extends AbstractIdentifiable> T update(T object) {
		return rootDataProducerHelper.update(object);
	}

	public <T extends AbstractIdentifiable> void createMany(@SuppressWarnings("unchecked") T...objects) {
		rootDataProducerHelper.createMany(objects);
	}
	
	public void create(Collection<? extends AbstractIdentifiable> collection) {
		inject(GenericBusiness.class).create(commonUtils.castCollection(collection, AbstractIdentifiable.class));
	}

	public <T extends AbstractEnumeration> T getEnumeration(Class<T> aClass,String code) {
		return rootDataProducerHelper.getEnumeration(aClass, code);
	}

	public void addContacts(ContactCollection collection, String[] addresses, String[] landNumbers,
			String[] mobileNumbers, String[] postalBoxes, String[] emails, String[] websites) {
		rootDataProducerHelper.addContacts(collection, addresses, landNumbers, mobileNumbers, postalBoxes, emails,
				websites);
	}
	
	public MovementCollection createMovementCollection(String code,String incrementActionName,String decrementActionName){
		return rootDataProducerHelper.createMovementCollection(code, incrementActionName, decrementActionName);
	}

	protected void instanciateRoleUniformResourceLocator(Collection<Role> roles,Object...uniformResourceLocatorArray){
		rootDataProducerHelper.instanciateRoleUniformResourceLocator(roles, uniformResourceLocatorArray);
	}
	
	protected void instanciateRoleUniformResourceLocator(Role role,Object...uniformResourceLocatorArray){
		rootDataProducerHelper.instanciateRoleUniformResourceLocator(Arrays.asList(role), uniformResourceLocatorArray);
	}
	
	protected void instanciateUserAccounts(Collection<Party> parties, Role... roles) {
		rootDataProducerHelper.instanciateUserAccounts(parties, roles);
	}
	
	protected void instanciateUserAccountsFromActors(Collection<? extends AbstractActor> actors, Role... roles) {
		rootDataProducerHelper.instanciateUserAccountsFromActors(actors, roles);
	}
	
	/**/
	
	protected <T extends AbstractIdentifiable> void createFromExcelSheet(String woorkbookName,Class<T> aClass){
		rootDataProducerHelper.createFromExcelSheet(getClass(), woorkbookName, aClass);
	}
	
	protected <T extends AbstractIdentifiable> void createFromExcelSheet(Class<T> aClass){
		createFromExcelSheet(getDataExcelFileName(), aClass);
	}
	
	protected <T extends AbstractIdentifiable> void createIdentifiable(Class<T> aClass,String...fields){
		rootDataProducerHelper.createIdentifiable(aClass, getClass().getResourceAsStream(getDataExcelFileName()), Boolean.FALSE,fields);
	}
	
	protected <T extends AbstractIdentifiable> void createIdentifiable(Class<T> aClass,InstanceHelper.Builder.OneDimensionArray<T> instanceBuilder
			,ArrayHelper.Dimension.Key.Builder keyBuilder){
		rootDataProducerHelper.createIdentifiable(aClass, instanceBuilder, keyBuilder, getClass().getResourceAsStream(getDataExcelFileName()));
	}
	
	protected <T extends AbstractIdentifiable> void createIdentifiable(Class<T> aClass,InstanceHelper.Builder.OneDimensionArray<T> instanceBuilder){
		rootDataProducerHelper.createIdentifiable(aClass, instanceBuilder, new org.cyk.system.root.business.impl.helper.ArrayHelper.KeyBuilder()
		, getClass().getResourceAsStream(getDataExcelFileName()));
	}
	
	protected <T extends AbstractIdentifiable> void createIdentifiableFromMicrosoftExcelSheet(Class<T> aClass){
		createIdentifiable(aClass,new org.cyk.system.root.business.impl.helper.InstanceHelper.BuilderOneDimensionArray<T>(aClass).addFieldCodeName());
	}
	
	protected String getDataExcelFileName(){
		return systemIdentifier+"data.xls";
	}
	
	/**/
	
	public static interface Listener {

		void handleObjectToInstall(BusinessLayer businessLayer,Object object);
		
		/**/
		
		public static class Adapter extends AbstractBean implements Serializable, Listener {

			private static final long serialVersionUID = -3142367274228861058L;

			@Override
			public void handleObjectToInstall(BusinessLayer businessLayer,Object object) {}

			
			/**/
			
			public static class Default extends Adapter implements Serializable {

				private static final long serialVersionUID = 8396655646771082967L;
				
			}
		}

	}
	
	/**/
	
	
    
	
}
