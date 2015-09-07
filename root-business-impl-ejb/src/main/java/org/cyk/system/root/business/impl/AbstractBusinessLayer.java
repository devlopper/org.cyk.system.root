package org.cyk.system.root.business.impl;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.Getter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessLayerListener;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.datasource.DataSource;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.security.PermissionBusiness;
import org.cyk.system.root.business.api.security.RoleSecuredViewBusiness;
import org.cyk.system.root.business.impl.datasource.JdbcDataSource;
import org.cyk.system.root.business.impl.file.report.AbstractReportRepository;
import org.cyk.system.root.business.impl.file.report.jasper.JasperReportBusinessImpl;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.business.impl.validation.FieldValidatorMethod;
import org.cyk.system.root.business.impl.validation.ValidatorMap;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.generator.StringGenerator;
import org.cyk.system.root.model.generator.StringValueGeneratorConfiguration;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.Permission;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleSecuredView;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.userinterface.InputName;
import org.cyk.utility.common.cdi.AbstractLayer;

public abstract class AbstractBusinessLayer extends AbstractLayer<AbstractIdentifiableBusinessServiceImpl<?>> implements BusinessLayer, Serializable {
    
	private static final long serialVersionUID = -4484371129296972868L;
	
	protected static final String PRIVATE_FOLDER_NAME = "private";
	protected static final String PRIVATE_ROLE_FOLDER_NAME = "private/__role__";
	
	protected static final String SHIRO_PRIVATE_FOLDER_FORMAT = "/%s/**";
	protected static final String SHIRO_PRIVATE_FOLDER = String.format(SHIRO_PRIVATE_FOLDER_FORMAT, PRIVATE_FOLDER_NAME);
	
	protected static final String SHIRO_ROLE_FOLDER_FORMAT = "/"+PRIVATE_ROLE_FOLDER_NAME+"/__%s__/**";
	
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected GenericBusiness genericBusiness;
    @Inject protected BusinessLocator businessLocator;
    @Inject protected BusinessManager businessManager;
    @Inject protected LanguageBusiness languageBusiness;
    @Inject protected JasperReportBusinessImpl reportBusiness;
    @Inject protected PermissionBusiness permissionBusiness;
    @Inject protected RoleSecuredViewBusiness roleSecuredViewBusiness;
    @Inject @Getter protected FileBusiness fileBusiness;
    
    protected ValidatorMap validatorMap = ValidatorMap.getInstance();
    @Getter protected Collection<BusinessLayerListener> businessLayerListeners = new ArrayList<>();
    
    @Override
    protected void initialisation() {
        super.initialisation();
        id = this.getClass().getName();
        registerTypedBusinessBean(businessLocator.getTypedBusinessBeanMap());
       
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
    	persistData();
    	setConstants();
    	if(Boolean.TRUE.equals(runFakeTransactions))
    		fakeTransactions();
    }
    
    protected abstract void persistData();
    protected abstract void setConstants();
    protected abstract void fakeTransactions();
    
	@Override
	public void registerTypedBusinessBean(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> arg0) {}
    
    /* shortcut methods */
    
    protected void registerResourceBundle(String id, ClassLoader aClassLoader) {
		languageBusiness.registerResourceBundle(id, aClassLoader);
	}

	@SuppressWarnings("unchecked")
    protected <T extends AbstractIdentifiable> T create(T anObject){
        return (T) genericBusiness.create(anObject);
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
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Installation buildInstallation() {
		Installation installation = new Installation();
    	installation.setAdministratorCredentials(new Credentials("admin", "123"));
    	installation.setApplication(new Application());
    	installation.getApplication().setName("Application");
    	installation.setLicense(new License());
    	installation.getLicense().setPeriod(new Period(new Date(), new Date()));
    	installation.setManager(new Person("ManagerFirstName","ManagerLastName"));
    	installation.setManagerCredentials(new Credentials("man", "123"));
    	return installation;
	}
	
	@Override
	public void installApplication(Installation installation) {
		for(BusinessLayerListener listener : businessLayerListeners)
			listener.beforeInstall(this, installation);
		applicationBusiness.install(installation);
		for(BusinessLayerListener listener : businessLayerListeners)
			listener.afterInstall(this, installation);
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
		for(BusinessLayerListener listener : businessLayerListeners)
			listener.handleObjectToInstall(this, object);
	}
	
	protected <T extends AbstractIdentifiable> void installObject(Integer identifier,String message,TypedBusiness<T> business,T object){
		handleObjectToInstall(object);
		business.create(object);
		logDebug(message);
	}
	protected void installObject(Integer identifier,String message,AbstractIdentifiable object){
		installObject(identifier,message, businessLocator.locate(object),object);
	}
	protected <T extends AbstractIdentifiable> void installObject(Integer identifier,TypedBusiness<T> business,T object){
		installObject(identifier,"Instance of "+object.getClass().getSimpleName()+" created", business, object);
	}
	protected <T extends AbstractIdentifiable> void installObject(Integer identifier,AbstractIdentifiable object){
		installObject(identifier,businessLocator.locate(object),object);
	}
	
	protected Permission createPermission(String code){
		return create(new Permission(code));
	}
    
	protected void createRole(Role role,String workspaceId,Permission...permissions){
   	 if(permissions!=null)
   		 for(Permission permission : permissions)
   			 role.getPermissions().add(permission);
   	 create(role);
   	createRoleSecuredView(role.getCode(),role,workspaceId);
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
	
	protected void createRoleSecuredView(String code,Role role,String viewId){
		roleSecuredViewBusiness.create(new RoleSecuredView(role, viewId, code, code));
	}
	
	
	
	/*
	@SuppressWarnings("unchecked")
	protected void createRole(String code,String name,Object[][] entityCruds){
		String[] permissions = new String[entityCruds.length];
		for(int i=0;i<permissions.length;i++)
			permissions[i] = permissionBusiness.computeCode((Class<AbstractIdentifiable>)entityCruds[i][0], (Crud)entityCruds[i][1]);
	}
	*/
    
    
    protected IntervalCollection intervalCollection(String...values){
    	IntervalCollection intervalCollection = new IntervalCollection();
    	create(intervalCollection);
    	for(int i=0;i<values.length;i=i+3){
    		Interval interval = new Interval();
    		interval.setCollection(intervalCollection);
    		interval.setName(values[i]);
    		interval.setLow(new BigDecimal(values[i+1]));
    		interval.setHigh(new BigDecimal(values[i+2]));
    		create(interval);
    	}
    	return intervalCollection;
    }
    
    protected Metric createMetric(String code,String name){
    	Metric metric = new Metric();
    	metric.setCode(code);
    	metric.setName(name);
    	return create(metric);
    }
    
    protected InputName inputName(String code,String name){
    	InputName inputName = new InputName();
    	inputName.setCode(code);
    	inputName.setName(name);
    	return create(inputName);
    }
    
    protected byte[] getResourceAsBytes(String relativePath){
    	String path = "/"+StringUtils.replace(this.getClass().getPackage().getName(), ".", "/")+"/";
    	try {
    		logDebug("Getting resource as bytes {}", path+relativePath);
    		return IOUtils.toByteArray(this.getClass().getResourceAsStream(path+relativePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
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
    	script.setFile(fileBusiness.process(text.getBytes(), name+".txt"));
    	return script;
    }
}
