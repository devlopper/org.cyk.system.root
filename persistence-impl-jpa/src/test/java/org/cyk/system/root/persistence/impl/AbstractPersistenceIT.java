package org.cyk.system.root.persistence.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import lombok.Getter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.utility.common.test.AbstractIntegrationTestJpaBased;
import org.cyk.utility.common.test.ArchiveBuilder;

/**
 * Persistence integration test (IT)
 * @author Komenan Y .Christian
 *
 */ 
public abstract class AbstractPersistenceIT extends AbstractIntegrationTestJpaBased {
	 
	private static final long serialVersionUID = -3977685343817022628L;

	public static ArchiveBuilder deployment(Class<?>[] classes){
		ArchiveBuilder builder = new ArchiveBuilder();
		builder.create().addClasses(PersistenceIntegrationTestHelper.BASE_CLASSES)
		.persistence(classes);
		return builder;
	}
		
	@Inject @Getter private GenericDao genericDao;
	
	@Override
	protected void _execute_() {
		super._execute_();
		queries();
	}
	
	/**/
	
	@Override
	public EntityManager getEntityManager() {
	    return ((AbstractPersistenceService<?>)genericDao).getEntityManager();
	}
	
	protected abstract void queries();
	
	/* Shortcut */
	
	protected void create(AbstractIdentifiable object){
		genericDao.create(object.getClass(), object);
	}
	
	protected void update(AbstractIdentifiable object){
		genericDao.update(object.getClass(), object);
	}
	
}
