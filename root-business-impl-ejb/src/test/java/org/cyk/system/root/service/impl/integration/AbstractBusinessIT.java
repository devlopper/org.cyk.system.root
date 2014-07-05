package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.impl.BusinessIntegrationTestHelper;
import org.cyk.system.root.business.impl.party.PersonValidator;
import org.cyk.system.root.business.impl.validation.AbstractValidator;
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
	//@Inject protected DefaultValidator defaultValidator;
    
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
    
    protected void validate(Object object){
        if(object==null)
            return;
        @SuppressWarnings("unchecked")
        AbstractValidator<Object> validator = (AbstractValidator<Object>) AbstractValidator.validatorOf(object.getClass());
        if(validator==null){
            //log.warning("No validator has been found. The default one will be used");
            //validator = defaultValidator;
            return;
        }
        try {
            validator.validate(object);
        } catch (Exception e) {}
        
        if(!Boolean.TRUE.equals(validator.isSucces()))
            System.out.println(validator.getMessagesAsString());
        
    }
    
    public static Archive<?> createRootDeployment() {
        return _deploymentOfPackage("org.cyk.system.root").getArchive()
              //FIXME those classes are ignored. WHY 
                .addPackage(ExceptionUtils.class.getPackage())
                .addPackage(PersonValidator.class.getPackage())
                ;
    } 
}
