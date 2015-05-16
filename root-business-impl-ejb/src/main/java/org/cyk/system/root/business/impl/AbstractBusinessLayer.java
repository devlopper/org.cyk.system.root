package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.datasource.DataSource;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.business.api.security.PermissionBusiness;
import org.cyk.system.root.business.impl.datasource.JdbcDataSource;
import org.cyk.system.root.business.impl.file.report.jasper.JasperReportBusinessImpl;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.business.impl.validation.FieldValidatorMethod;
import org.cyk.system.root.business.impl.validation.ValidatorMap;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootRandomDataProvider;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.security.Permission;
import org.cyk.system.root.model.security.Role;
import org.cyk.utility.common.cdi.AbstractLayer;

public abstract class AbstractBusinessLayer extends AbstractLayer<AbstractBusinessService<?>> implements BusinessLayer, Serializable {
    
	private static final long serialVersionUID = -4484371129296972868L;
	
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected GenericBusiness genericBusiness;
    @Inject protected BusinessLocator businessLocator;
    @Inject protected BusinessManager businessManager;
    @Inject protected LanguageBusiness languageBusiness;
    @Inject protected JasperReportBusinessImpl reportBusiness;
    @Inject protected PermissionBusiness permissionBusiness;
    
    protected ValidatorMap validatorMap = ValidatorMap.getInstance();
    
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
	
	protected Permission createPermission(String code){
		return create(new Permission(code));
	}
    
	protected void createRole(Role role,Permission...permissions){
   	 if(permissions!=null)
   		 for(Permission permission : permissions)
   			 role.getPermissions().add(permission);
   	 create(role);
   }
    
	protected void createRole(String code,String name,String...permissionCodes){
		Role role = new Role(code, name);
		Collection<Permission> permissions = new ArrayList<>();
		if(permissionCodes!=null)
			for(String permissionCode : permissionCodes){
				Permission permission = createPermission(permissionCode);
				permissions.add(permission);
			}
		createRole(role, permissions.toArray(new Permission[]{}));
	}
	
	@SuppressWarnings("unchecked")
	protected void createRole(String code,String name,Object[][] entityCruds){
		String[] permissions = new String[entityCruds.length];
		for(int i=0;i<permissions.length;i++)
			permissions[i] = permissionBusiness.computeCode((Class<AbstractIdentifiable>)entityCruds[i][0], (Crud)entityCruds[i][1]);
	}
	
    protected <T extends AbstractActor> T actor(AbstractActorBusiness<T> business,Class<T> aClass,String name,String lastName,Boolean male){
    	T actor = null;
		try {
			actor = aClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	actor.setPerson(RootRandomDataProvider.getInstance().person(male));
    	/*
    	if(name==null)
    		actor.getPerson().setName(name);
    	if(lastName!=null)
    		actor.getPerson().setLastName(lastName);
    	*/
    	business.create(actor);
    	return actor;
    }
    
    protected <T extends AbstractActor> T actor(AbstractActorBusiness<T> business,Class<T> aClass,Boolean male){
    	return actor(business, aClass, null, null, male);
    }
    
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
}
