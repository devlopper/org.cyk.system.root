package org.cyk.system.root.persistence.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class PersistenceInteraceLocatorIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	} 
	
	@Inject private PersistenceInterfaceLocator persistenceInterfaceLocator;
	
	@Override
	protected void populate() {}
	
	
	@Override
	protected void queries() {
		assertThat("Locality Dao found", persistenceInterfaceLocator.locate(Locality.class)!=null);
		assertThat("Locality Dao injected", persistenceInterfaceLocator.injectLocated(Locality.class)!=null);
	}

	@Override protected void create() {}
	@Override protected void delete() {}
	@Override protected void read() {}
	@Override protected void update() {}
	
	
	
	
}
