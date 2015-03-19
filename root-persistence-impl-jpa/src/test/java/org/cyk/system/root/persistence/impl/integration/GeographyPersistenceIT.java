package org.cyk.system.root.persistence.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.persistence.api.geography.ContactCollectionDao;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.system.root.persistence.api.geography.ElectronicMailDao;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class GeographyPersistenceIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	} 
	 
	@Inject private ContactDao contactDao;
	@Inject private ContactCollectionDao contactCollectionDao;
	@Inject private ElectronicMailDao electronicMailDao;
		
	@Override
	protected void populate() {
		create(new ElectronicMail("a@mail.com"));
		
	}
	 
	@Override
	protected void queries() {
		
	}

	@Override protected void create() {}
	@Override protected void delete() {}
	@Override protected void read() {}
	@Override protected void update() {}
	
}
