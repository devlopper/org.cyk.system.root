package org.cyk.system.root.business.impl.integration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.system.root.business.api.AbstractBusinessThrowable;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.BusinessIntegrationTestHelper;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.RootBusinessTestHelper;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.business.impl.party.ApplicationBusinessImpl;
import org.cyk.system.root.business.impl.validation.DefaultValidator;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.business.impl.validation.ValidatorMap;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;
import org.cyk.system.root.persistence.impl.PersistenceIntegrationTestHelper;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.ThrowableHelper;
import org.cyk.utility.common.test.TestEnvironmentListener;
import org.cyk.utility.test.ArchiveBuilder;
import org.cyk.utility.test.integration.AbstractIntegrationTestJpaBased;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public abstract class AbstractBusinessIT extends AbstractIntegrationTestJpaBased {
	private static final long serialVersionUID = 7531234257367131255L;
	
	static {
		TestEnvironmentListener.COLLECTION.add(new TestEnvironmentListener.Adapter.Default(){
			private static final long serialVersionUID = -2347039842308401189L;
			@Override
			protected Throwable getThrowable(Throwable throwable) {
				return ThrowableHelper.getInstance().getInstanceOf(throwable, AbstractBusinessThrowable.class);
			}
			@Override
    		public void assertEquals(String message, Object expected, Object actual) {
    			Assert.assertEquals(message, expected, actual);
    		}
    		@Override
    		public String formatBigDecimal(BigDecimal value) {
    			return inject(NumberBusiness.class).format(value);
    		}
    		
    		@SuppressWarnings("unchecked")
			@Override
    		public void assertCodeExists(Class<?> aClass, String code) {
    			assertThat(code+" exists", inject(BusinessInterfaceLocator.class).injectTyped((Class<AbstractIdentifiable>)aClass).find(code)!=null);
    		}
    	});
		ClassHelper.getInstance().map(ApplicationBusinessImpl.Listener.class, ApplicationBusinessAdapter.class,Boolean.FALSE);
	}
	
	@Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
	
	@Inject protected ExceptionUtils exceptionUtils;
	@Inject protected DefaultValidator defaultValidator;
	@Inject private GenericDaoImpl g;
	@Inject protected GenericBusiness genericBusiness;
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected RootBusinessTestHelper rootBusinessTestHelper;
	
	@Inject protected ValidatorMap validatorMap;// = ValidatorMap.getInstance();
	@Inject protected RootBusinessLayer rootBusinessLayer;
	
    @Override
    public EntityManager getEntityManager() {
        return g.getEntityManager();
    }
    @Override
    protected Boolean populateInTransaction() {
    	return Boolean.FALSE;
    }
    
    protected void listenPopulateStart(){}
    
    @Override
    protected void populate() {
    	listenPopulateStart();
    	installApplication();
    }
    
    protected org.cyk.system.root.business.impl.__test__.TestCase instanciateTestCase(){
    	return (org.cyk.system.root.business.impl.__test__.TestCase) rootBusinessTestHelper.instanciateTestCase().setName(MethodHelper.getInstance().getNameFromStackTraceAt(3))
    			.prepare();
	}
	  
    @Override
    protected void _execute_() {
        super._execute_();
        create();    
        read(); 
        update();    
        delete();    
        finds();
        businesses();
    }
    
	protected void finds(){}
	
	protected void businesses(){}
	
	/* Shortcut */
    
    @SuppressWarnings("unchecked")
	protected <T extends AbstractIdentifiable> T create(T object){
        return (T) genericBusiness.create(object);
    }
    @SuppressWarnings("unchecked")
	protected <T extends AbstractIdentifiable> void create(Collection<T> objects){
        genericBusiness.create((Collection<AbstractIdentifiable>) objects);
    }
    
    @SuppressWarnings("unchecked")
	protected <T extends AbstractIdentifiable> T update(T object){
        return (T) genericBusiness.update(object);
    }
    
    @SuppressWarnings("unchecked")
	protected <T extends AbstractIdentifiable> T update(T object,Collection<? extends AbstractIdentifiable> identifiables){
        return (T) genericBusiness.update(object,identifiables);
    }
    
    @SuppressWarnings("unchecked")
	protected <T extends AbstractIdentifiable> T delete(T object){
        return (T) genericBusiness.delete(object);
    }
    
    protected <T extends AbstractIdentifiable> void deleteByCodes(Class<T> aClass,Collection<String> codes){
        genericBusiness.deleteByCodes(aClass,codes);
    }
    
    protected <T extends AbstractIdentifiable> void deleteByCode(Class<T> aClass,String code){
        genericBusiness.deleteByCode(aClass,code);
    }
    
    protected void installApplication(){
    	RootBusinessLayer.getInstance().installApplication();
    }
    
    protected void assertEquals(Object actual,ObjectFieldValues expected){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.assertEquals(actual, expected);
	}
    
    public static Archive<?> createRootDeployment() {
        return  
                new ArchiveBuilder().create().getArchive().
                    addClasses(BusinessIntegrationTestHelper.classes()).
                    addPackages(Boolean.FALSE, BusinessIntegrationTestHelper.packages())
                    .addClasses(RootBusinessLayer.class,RootBusinessTestHelper.class)
                    .addClasses(PersistenceIntegrationTestHelper.classes())
                //_deploymentOfPackages("org.cyk.system.root").getArchive()
              
                //.addPackages(Boolean.FALSE,BusinessIntegrationTestHelper.PACKAGES)
                //.addClasses(BusinessIntegrationTestHelper.CLASSES)
                //.addPackage(ExceptionUtils.class.getPackage())
                //.addPackage(PersonValidator.class.getPackage())
                ;
    } 
    
    @Override protected void create() {}
    @Override protected void read() {}
    @Override protected void update() {}
    @Override protected void delete() {}
 
    /**/
    
    public static class ApplicationBusinessAdapter extends ApplicationBusinessImpl.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@Override
		public void installationStarted(Installation installation) {
			super.installationStarted(installation);
			installation.setIsCreateAccounts(Boolean.FALSE);
			installation.setIsCreateLicence(Boolean.FALSE);
		}
		
    }
}
