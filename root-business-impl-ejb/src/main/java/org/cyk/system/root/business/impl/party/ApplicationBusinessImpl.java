package org.cyk.system.root.business.impl.party;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.message.SmtpPropertiesBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.security.ApplicationPropertiesProvider;
import org.cyk.system.root.business.api.security.ShiroConfigurator;
import org.cyk.system.root.business.api.security.SoftwareBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessLayer;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.security.DefaultApplicationPropertiesProvider;
import org.cyk.system.root.business.impl.security.DefaultShiroConfigurator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.PersistenceManager;
import org.cyk.system.root.persistence.api.party.ApplicationDao;
import org.cyk.system.root.persistence.api.security.RoleDao;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.StackTraceHelper;

@Stateless @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ApplicationBusinessImpl extends AbstractPartyBusinessImpl<Application, ApplicationDao> implements ApplicationBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;
	
	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class, Boolean.FALSE);
	}
	
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
	@Inject private SmtpPropertiesBusiness smtpPropertiesBusiness;
	
	@Inject
	public ApplicationBusinessImpl(ApplicationDao dao) {
		super(dao); 
	}
	
	@Override
	protected void beforeCreate(Application application) {
		super.beforeCreate(application);
		createIfNotIdentified(application.getSmtpProperties());
	}

	/**
	 * 1 - Creates data<br/>
	 * 2 - Creates the only one application user having the System role<br/>
	 * 
	 */
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void install(final Installation installation) {
		Long count = dao.countAll();
		if(/*findCurrentInstance()==null*/ count == 0){
			new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2),getClass()){
				private static final long serialVersionUID = 1L;
				
				public Object __execute__() {
					installation.getApplication().setCode("APP");
					Listener listener = ClassHelper.getInstance().instanciateOne(Listener.class);
					try {
						listener.installationStarted(installation);
						installData(installation);
						if(Boolean.TRUE.equals(installation.getIsCreateAccounts()))
							installAccounts(installation);
						
						if(installation.getSmtpProperties()!=null){
							smtpPropertiesBusiness.create(installation.getSmtpProperties());
							installation.getApplication().setSmtpProperties(installation.getSmtpProperties());
						}
						RootBusinessLayer.getInstance().setApplication(INSTANCE = dao.select().one());
						
						for(AbstractIdentifiable identifiable : installation.getIdentifiables())
							RootBusinessLayer.getInstance().getGenericBusiness().create(identifiable);
						
						listener.installationEnded(installation);
					} catch (Exception e) {
						e.printStackTrace();
						//logThrowable(e);
						exceptionUtils().exception(Boolean.TRUE,"exception.install",new Object[]{e});
					}
					return null;
				}
			}.execute();
		}
	}
	
	private void installData(Installation installation){
		for(BusinessLayer layer : businessManager.findBusinessLayers()){
			logInfo("Installing {} system data...", ((AbstractBusinessLayer)layer).getId());
			layer.createInitialData();
		}
	}
	
	private void installAccounts(Installation installation){
		logInfo("Creating administrator account");
		//installation.getAdministratorCredentials().setUsername(null);
		create(installation.getApplication());
		UserAccount administratorAccount = new UserAccount();
		administratorAccount.setCode(RootConstant.Code.UserAccount.APPLICATION);
		administratorAccount.setUser(installation.getApplication());
		administratorAccount.setCredentials(installation.getAdministratorCredentials());
		if(installation.getAdministratorCredentials().getSoftware()==null)
			installation.getAdministratorCredentials().setSoftware(inject(SoftwareBusiness.class).findDefaulted());
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
		if(installation.getManagerCredentials().getSoftware()==null)
			installation.getManagerCredentials().setSoftware(inject(SoftwareBusiness.class).findDefaulted());
		//Super User : The one who use the system
		
		managerAccount.getRoles().addAll(roleDao.readAllExclude(Arrays.asList(inject(RoleDao.class).read(RootConstant.Code.Role.ADMINISTRATOR))));
		//managerAccount.getRoles().add(RootBusinessLayer.getInstance().getUserRole());
		
		userAccountBusiness.create(managerAccount);
		
		logInfo("Creating others accounts");
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Application update(Application application) {
		INSTANCE = super.update(application);
		RootBusinessLayer.getInstance().setApplication(INSTANCE);
		return INSTANCE;
	} 

    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Application findCurrentInstance() {
    	if(INSTANCE==null){
    		Collection<Application> applications = dao.readAll();
    		INSTANCE = applications.isEmpty()?null:applications.iterator().next();
    		RootBusinessLayer.getInstance().setApplication(INSTANCE);
    	}
    	return INSTANCE;
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Collection<BusinessEntityInfos> findBusinessEntitiesInfos() {
        if(BUSINESS_ENTITIES_INFOS==null){
        	BUSINESS_ENTITIES_INFOS = new HashSet<>();
            for(Class<? extends Identifiable<?>> entityClass : persistenceManager.findEntities()){
                BusinessEntityInfos b = new BusinessEntityInfos(entityClass, languageBusiness);
                //find all classes having attribute of type class
                Collection<Class<? extends Annotation>> annotations = new ArrayList<>();
                annotations.add(ManyToOne.class);
                for(Class<? extends Identifiable<?>> childClass : persistenceManager.findEntities()){
                	for(Field field : FieldHelper.getInstance().get(childClass, annotations)){
                		if(field.getType().equals(entityClass))
                			b.getManyToOneClasses().add(childClass);
                	}
                }
                
                annotations.clear();
                annotations.add(OneToOne.class);
                for(Class<? extends Identifiable<?>> childClass : persistenceManager.findEntities()){
                	for(Field field : FieldHelper.getInstance().get(childClass, annotations)){
                		if(field.getType().equals(entityClass))
                			b.getOneToOneClasses().add(childClass);
                	}
                }
                
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
    public BusinessEntityInfos findBusinessEntityInfos(String identifier) {
    	for(BusinessEntityInfos b : findBusinessEntitiesInfos())
    		if(b.getIdentifier().equals(identifier))
    			return b;
    	return null;
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public BusinessEntityInfos findBusinessEntityInfosByTableName(String name) {
    	for(BusinessEntityInfos b : findBusinessEntitiesInfos())
    		if(b.getIdentifier().equalsIgnoreCase(name))
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
	
	/**/
	
	public static interface Listener{
		
		void installationStarted(Installation installation);
		void installationEnded(Installation installation);
		
		/**/
		public static class Adapter implements Listener,Serializable {
			private static final long serialVersionUID = -2983067114876599661L;
			@Override public void installationStarted(Installation installation) {}
			@Override public void installationEnded(Installation installation) {}
			
			/**/
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = -8533811278793391794L;
				
			}
		}
	}

}
 