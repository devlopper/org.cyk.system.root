package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.impl.BusinessIntegrationTestHelper;
import org.cyk.system.root.business.impl.validation.DefaultValidator;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;
import org.cyk.utility.common.test.AbstractIntegrationTestJpaBased;
import org.cyk.utility.common.test.ArchiveBuilder;
import org.jboss.shrinkwrap.api.Archive;

public abstract class AbstractBusinessIT extends AbstractIntegrationTestJpaBased {

	public static ArchiveBuilder deployment(Class<?>[] classes){
		ArchiveBuilder builder = new ArchiveBuilder();
		builder.create().addClasses(BusinessIntegrationTestHelper.BASE_CLASSES).business(classes);
		return builder;
	}
	
	@Inject protected ExceptionUtils exceptionUtils;
	private @Inject GenericDaoImpl g;
	protected @Inject GenericBusiness genericBusiness;
    
    @Override
    public EntityManager getEntityManager() {
        return g.getEntityManager();
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
    
	protected abstract void finds();
	
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
    
    public static Archive<?> createRootDeployment() {
        return _deploymentOfPackage("org.cyk.system.root").getArchive().addPackage(ExceptionUtils.class.getPackage());
    } 
}
