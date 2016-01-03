package org.cyk.system.root.service.impl.integration;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.system.root.business.api.AbstractBusinessException;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.BusinessIntegrationTestHelper;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.RootTestHelper;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
import org.cyk.system.root.business.impl.validation.DefaultValidator;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.business.impl.validation.ValidatorMap;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;
import org.cyk.system.root.persistence.impl.PersistenceIntegrationTestHelper;
import org.cyk.utility.common.test.TestEnvironmentListener;
import org.cyk.utility.test.ArchiveBuilder;
import org.cyk.utility.test.integration.AbstractIntegrationTestJpaBased;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert; 

public abstract class AbstractBusinessIT extends AbstractIntegrationTestJpaBased {

	static {
		TestEnvironmentListener.COLLECTION.add(new TestEnvironmentListener.Adapter.Default(){
			private static final long serialVersionUID = -2347039842308401189L;
			@Override
			protected Throwable getThrowable(Throwable throwable) {
				return commonUtils.getThrowableInstanceOf(throwable, AbstractBusinessException.class);
			}
			@Override
    		public void assertEquals(String message, Object expected, Object actual) {
    			Assert.assertEquals(message, expected, actual);
    		}
    		@Override
    		public String formatBigDecimal(BigDecimal value) {
    			return RootBusinessLayer.getInstance().getNumberBusiness().format(value);
    		}
    	});
	}
	
	@Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
	
    /*
	public static ArchiveBuilder deployment(Class<?>[] classes){
		ArchiveBuilder builder = new ArchiveBuilder();
		builder.create().addClasses(BusinessIntegrationTestHelper.BASE_CLASSES).business(classes);
		return builder;
	}*/
	 
	/**
	 *  
	 */
	private static final long serialVersionUID = 7531234257367131255L;
	@Inject protected ExceptionUtils exceptionUtils;
	@Inject protected DefaultValidator defaultValidator;
	@Inject private GenericDaoImpl g;
	@Inject protected GenericBusiness genericBusiness;
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected RootTestHelper rootTestHelper;
	
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
    @Override
    protected void populate() {
    	installApplication();
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
	
	protected abstract void businesses();
	/*
	@SuppressWarnings("unchecked")
    protected <T extends AbstractIdentifiable> T create(T anObject){
        return (T) genericBusiness.create(anObject);
    }*/
	
	/* Shortcut */
    
    protected AbstractIdentifiable create(AbstractIdentifiable object){
        return genericBusiness.create(object);
    }
    
    protected AbstractIdentifiable update(AbstractIdentifiable object){
        return genericBusiness.update(object);
    }
    
    protected void validate(Object object){
        if(object==null)
            return;
        @SuppressWarnings("unchecked")
        AbstractValidator<Object> validator = (AbstractValidator<Object>) validatorMap.validatorOf(object.getClass());
        if(validator==null){
            //log.warning("No validator has been found. The default one will be used");
            //validator = defaultValidator;
            return;
        }
        try {
            validator.validate(object);
        } catch (Exception e) {}
        
        //if(!Boolean.TRUE.equals(validator.isSuccess()))
         //   ystem.out.println(validator.getMessagesAsString());
        
    }
    
    protected void installApplication(){
    	RootBusinessLayer.getInstance().installApplication();
    }
    
    public static Archive<?> createRootDeployment() {
        return  
                new ArchiveBuilder().create().getArchive().
                    addClasses(BusinessIntegrationTestHelper.classes()).
                    addPackages(Boolean.FALSE, BusinessIntegrationTestHelper.packages())
                    .addClasses(RootBusinessLayer.class,RootTestHelper.class)
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
    
}
