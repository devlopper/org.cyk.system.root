package org.cyk.system.root.business.impl.party;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.message.SmtpPropertiesBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.security.ApplicationPropertiesProvider;
import org.cyk.system.root.business.api.security.LicenseBusiness;
import org.cyk.system.root.business.api.security.ShiroConfigurator;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessLayer;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.security.DefaultApplicationPropertiesProvider;
import org.cyk.system.root.business.impl.security.DefaultShiroConfigurator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.PartySearchCriteria;
import org.cyk.system.root.model.security.ApplicationAccount;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.PersistenceManager;
import org.cyk.system.root.persistence.api.party.ApplicationDao;
import org.cyk.system.root.persistence.api.security.RoleDao;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

@Stateless @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ApplicationBusinessImpl extends AbstractPartyBusinessImpl<Application, ApplicationDao,PartySearchCriteria> implements ApplicationBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public static final Collection<ApplicationBusinessImplListener> LISTENERS = new ArrayList<>();
	
	private static Application INSTANCE;
	private static ApplicationPropertiesProvider PROPERTIES_PROVIDER;
	private static ShiroConfigurator SHIRO_CONFIGURATOR;
	public static Collection<BusinessEntityInfos> BUSINESS_ENTITIES_INFOS;
	private static Map<String, ValueGenerator<?, ?>> VALUE_GENERATOR_MAP = new HashMap<String, ValueGenerator<?,?>>();
	
	@Inject private RoleDao roleDao;
	@Inject private BusinessManager businessManager;
	@Inject private PersistenceManager persistenceManager;
    @Inject private LanguageBusiness languageBusiness;
    @Inject private PersonBusiness personBusiness;
	@Inject private UserAccountBusiness userAccountBusiness;
	@Inject private LicenseBusiness licenseBusiness;
	@Inject private SmtpPropertiesBusiness smtpPropertiesBusiness;
	
	@Inject
	public ApplicationBusinessImpl(ApplicationDao dao) {
		super(dao); 
	}

	/**
	 * 1 - Creates data<br/>
	 * 2 - Creates the only one application user having the System role<br/>
	 * 
	 */
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void install(Installation installation) {
		if(findCurrentInstance()==null){
			logInfo("Installation starts.");
			try {
				for(ApplicationBusinessImplListener listener : LISTENERS)
					listener.installationStarted(installation);
				
				installData(installation);
				installAccounts(installation);
				installLicense(installation);
				
				if(installation.getSmtpProperties()!=null){
					smtpPropertiesBusiness.create(installation.getSmtpProperties());
					installation.getApplication().setSmtpProperties(installation.getSmtpProperties());
				}
				RootBusinessLayer.getInstance().setApplicationIdentifier(dao.select().one().getIdentifier());
				
				for(AbstractIdentifiable identifiable : installation.getIdentifiables())
					RootBusinessLayer.getInstance().getGenericBusiness().create(identifiable);
				
				for(ApplicationBusinessImplListener listener : LISTENERS)
					listener.installationEnded(installation);
				
				logInfo("Installation done.");
			} catch (Exception e) {
				e.printStackTrace();
				logThrowable(e);
				exceptionUtils().exception(Boolean.TRUE,"exception.install",new Object[]{e});
			}
		}
	}
	
	private void installData(Installation installation){
		logInfo("Creating data");
		for(BusinessLayer layer : businessManager.findBusinessLayers()){
			logInfo("Layer : "+ ((AbstractBusinessLayer)layer).getId());
			layer.createInitialData(installation.getFaked());
		}
	}
	
	private void installAccounts(Installation installation){
		logInfo("Creating administrator account");
		//installation.getAdministratorCredentials().setUsername(null);
		create(installation.getApplication());
		ApplicationAccount administratorAccount = new ApplicationAccount();
		administratorAccount.setUser(installation.getApplication());
		administratorAccount.setCredentials(installation.getAdministratorCredentials());
		//Installer : The one who delivers the system = SUPER SUPER USER = Configure what the system will do
		//administratorAccount.getRoles().add(RootBusinessLayer.getInstance().getUserRole());
		//administratorAccount.getRoles().add(RootBusinessLayer.getInstance().getAdministratorRole());
		administratorAccount.getRoles().addAll(roleDao.readAll());
		userAccountBusiness.create(administratorAccount);
		
		logInfo("Creating manager account");
		personBusiness.create(installation.getManager());
		UserAccount managerAccount = new UserAccount();
		managerAccount.setUser(installation.getManager());
		managerAccount.setCredentials(installation.getManagerCredentials());
		
		//Super User : The one who use the system
		
		managerAccount.getRoles().addAll(roleDao.readAllExclude(Arrays.asList(RootBusinessLayer.getInstance().getRoleAdministrator())));
		//managerAccount.getRoles().add(RootBusinessLayer.getInstance().getUserRole());
		
		userAccountBusiness.create(managerAccount);
		
		logInfo("Creating others accounts");
	}
	
	private void installLicense(Installation installation){
		logInfo("Creating license");
		installation.getApplication().setLicense(installation.getLicense());
		licenseBusiness.create(installation.getLicense());	
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void applySettings(Installation installation) {
		// TODO Auto-generated method stub
		
	}  

    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Application findCurrentInstance() {
    	if(INSTANCE==null){
    		Collection<Application> applications = dao.readAll();
    		INSTANCE = applications.isEmpty()?null:applications.iterator().next();
    	}
    	return INSTANCE;
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public BusinessEntityInfos findBusinessEntityInfos(Class<? extends AbstractIdentifiable> aClass) {
    	for(BusinessEntityInfos b : findBusinessEntitiesInfos())
    		if(b.getClazz().equals(aClass))
    			return b;
    	return null;
    }

    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
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

	@Override
	public void registerValueGenerator(ValueGenerator<?, ?> valueGenerator) {
		ValueGenerator<?, ?> current = findValueGenerator(valueGenerator.getIdentifier());
		VALUE_GENERATOR_MAP.put(valueGenerator.getIdentifier(), valueGenerator);
		logInfo("Value generator {}. identifier={} , description={}",current==null?"registered":"updated", valueGenerator.getIdentifier(),valueGenerator.getDescription());
	}

	@Override
	public ValueGenerator<?, ?> findValueGenerator(String identifier) {
		return VALUE_GENERATOR_MAP.get(identifier);
	}
	
	@Override
	public <INPUT, OUTPUT> OUTPUT generateValue(String identifier,Class<INPUT> inputClass,Class<OUTPUT> outputClass, INPUT input) {
		@SuppressWarnings("unchecked")
		ValueGenerator<INPUT, OUTPUT> valueGenerator = (ValueGenerator<INPUT, OUTPUT>) findValueGenerator(identifier);
		if(valueGenerator==null){
			logError("No value generator found for {} , input class={} , output class={}",identifier,inputClass,outputClass);
			return null;
		}
		OUTPUT output = valueGenerator.generate(input);
		logDebug("Generator id={} input={} output={}", identifier,input,output);
		//logStackTraceAsString("org.cyk.");
		return output;
	}

}
 