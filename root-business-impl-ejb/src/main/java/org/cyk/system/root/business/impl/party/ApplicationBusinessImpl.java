package org.cyk.system.root.business.impl.party;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.security.ApplicationPropertiesProvider;
import org.cyk.system.root.business.api.security.LicenseBusiness;
import org.cyk.system.root.business.api.security.ShiroConfigurator;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessLayer;
import org.cyk.system.root.business.impl.security.DefaultApplicationPropertiesProvider;
import org.cyk.system.root.business.impl.security.DefaultShiroConfigurator;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.PartySearchCriteria;
import org.cyk.system.root.model.security.ApplicationAccount;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.PersistenceManager;
import org.cyk.system.root.persistence.api.party.ApplicationDao;
import org.cyk.system.root.persistence.api.security.RoleDao;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Stateless @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ApplicationBusinessImpl extends AbstractPartyBusinessImpl<Application, ApplicationDao,PartySearchCriteria> implements ApplicationBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	private static Application INSTANCE;
	private static ApplicationPropertiesProvider PROPERTIES_PROVIDER;
	private static ShiroConfigurator SHIRO_CONFIGURATOR;
	private static Collection<BusinessEntityInfos> BUSINESS_ENTITIES_INFOS;
	
	@Inject private RoleDao roleDao;
	@Inject private BusinessManager businessManager;
	@Inject private PersistenceManager persistenceManager;
    @Inject private LanguageBusiness languageBusiness;
    @Inject private PersonBusiness personBusiness;
	@Inject private UserAccountBusiness userAccountBusiness;
	@Inject private LicenseBusiness licenseBusiness;
	
	@Inject
	public ApplicationBusinessImpl(ApplicationDao dao) {
		super(dao); 
	}

	/**
	 * 1 - Creates data<br/>
	 * 2 - Creates the only one application user having the System role<br/>
	 * 
	 */
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void install(Installation installation) {
		try {
			__writeInfo__("Installation is running...");
			installData();
			installAccounts(installation);
			installLicense(installation);
			__writeInfo__("Installation done!");
		} catch (Exception e) {
			e.printStackTrace();
			exceptionUtils().exception(Boolean.TRUE,"exception.install",new Object[]{e});
		}
	}
	
	private void installData(){
		__writeInfo__("Creating data");
		for(BusinessLayer layer : businessManager.findBusinessLayers()){
			__writeInfo__("Layer : "+ ((AbstractBusinessLayer)layer).getId());
            layer.createInitialData();
		}
	}
	
	private void installAccounts(Installation installation){
		__writeInfo__("Creating administrator account");
		create(installation.getApplication());
		ApplicationAccount administratorAccount = new ApplicationAccount();
		administratorAccount.setUser(installation.getApplication());
		administratorAccount.setCredentials(installation.getAdministratorCredentials());
		administratorAccount.getRoles().add(roleDao.read(Role.ADMINISTRATOR));
		userAccountBusiness.create(administratorAccount);
		administratorAccount.getRoles().remove(roleDao.read(Role.BUSINESS_ACTOR));
		
		__writeInfo__("Creating manager account");
		personBusiness.create(installation.getManager());
		UserAccount managerAccount = new UserAccount();
		managerAccount.setUser(installation.getManager());
		managerAccount.setCredentials(installation.getManagerCredentials());
		managerAccount.getRoles().add(roleDao.read(Role.MANAGER));
		userAccountBusiness.create(managerAccount);
		
	}
	
	private void installLicense(Installation installation){
		__writeInfo__("Creating license");
		installation.getApplication().setLicense(installation.getLicense());
		licenseBusiness.create(installation.getLicense());	
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void applySettings(Installation installation) {
		// TODO Auto-generated method stub
		
	}  

    @Override
    public Application findCurrentInstance() {
    	if(INSTANCE==null){
    		Collection<Application> applications = dao.readAll();
    		INSTANCE = applications.isEmpty()?null:applications.iterator().next();
    	}
    	return INSTANCE;
    }
    
    @Override
    public Collection<BusinessEntityInfos> findBusinessEntitiesInfos() {
        if(BUSINESS_ENTITIES_INFOS==null){
        	BUSINESS_ENTITIES_INFOS = new HashSet<>();
            for(Class<? extends Identifiable<?>> clazz : persistenceManager.findEntities()){
                BusinessEntityInfos b = new BusinessEntityInfos(clazz, languageBusiness);
                
                BUSINESS_ENTITIES_INFOS.add(b);
            }
        }
        return BUSINESS_ENTITIES_INFOS;
    }

    @Override
    public Collection<BusinessEntityInfos> findBusinessEntitiesInfos(CrudStrategy crudStrategy) {
        Collection<BusinessEntityInfos> l = new HashSet<>();
        for(BusinessEntityInfos infos : findBusinessEntitiesInfos()){
            if(infos.getCrudStrategy()!=null && infos.getCrudStrategy().equals(crudStrategy))
                l.add(infos);
        }
        return l;
    }
    
    @Override
    public void setPropertiesProvider(ApplicationPropertiesProvider anApplicationPropertiesProvider) {
    	PROPERTIES_PROVIDER = anApplicationPropertiesProvider;
    }
    
    @Override
    public ApplicationPropertiesProvider findPropertiesProvider() {
    	if(PROPERTIES_PROVIDER==null)
    		PROPERTIES_PROVIDER = new DefaultApplicationPropertiesProvider();
    	return PROPERTIES_PROVIDER;
    }
    
    @Override
    public void setShiroConfigurator(ShiroConfigurator aShiroConfigurator) {
    	SHIRO_CONFIGURATOR = aShiroConfigurator;	
    }
    
    @Override
    public ShiroConfigurator findShiroConfigurator() {
    	if(SHIRO_CONFIGURATOR==null)
    		SHIRO_CONFIGURATOR = new DefaultShiroConfigurator();
    	return SHIRO_CONFIGURATOR;
    }
    
    @Override
    public void configureShiro() {
    	 findShiroConfigurator().configure(findPropertiesProvider());
    }
     
}
 