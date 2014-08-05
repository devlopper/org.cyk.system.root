package org.cyk.system.root.persistence.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.persistence.api.PersistenceManager;
import org.cyk.system.root.persistence.impl.PersistenceManagerImpl;
import org.cyk.system.root.persistence.impl.data.PersonTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class PersistenceManagerIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Inject private PersistenceManager persistenceManager;
	 
	@Deployment
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{PersonTest.class,PersistenceManager.class,PersistenceManagerImpl.class}).getArchive();
	} 
		
	@Override
	protected void populate() {}
					
	@Override
	protected void create() {}

	@Override
	protected void read() {}

	@Override
	protected void update() {}

	@Override
	protected void delete() {}
	
	@Override
	protected void queries() {
	    //System.out.println("Entities : "+persistenceManager.findEntities());
		Assert.assertEquals(1,persistenceManager.findEntities().size());
	}
	
	
	
	
}
