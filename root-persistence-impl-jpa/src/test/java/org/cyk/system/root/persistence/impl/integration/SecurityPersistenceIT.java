package org.cyk.system.root.persistence.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.model.party.Person;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.security.CredentialsDao;
import org.cyk.system.root.persistence.api.security.UserAccountDao;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class SecurityPersistenceIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	} 
	 
	@Inject private UserAccountDao userAccountDao;
	@Inject private CredentialsDao credentialsDao;
	
		
	@Override
	protected void populate() {
		Person person = new Person("Paul", "Zadi");
		person.setContactCollection(null);
		create(person);
	    Credentials credentials = new Credentials("admin", "123");
	    UserAccount userAccount = new UserAccount(person, credentials, new Role[]{});
	    create(userAccount);
	}
	
	@Override
	protected void queries() {
		Assert.assertNotNull(credentialsDao.readByUsername("admin"));
		Assert.assertNotNull(credentialsDao.readByUsernameByPassword("admin","123"));
		
		Assert.assertNull(credentialsDao.readByUsername("admin1"));
		Assert.assertNull(credentialsDao.readByUsernameByPassword("admin","1231"));
		
		Assert.assertNotNull(userAccountDao.readByCredentials(new Credentials("admin", "123")));
		Assert.assertNull(userAccountDao.readByCredentials(new Credentials("admin", "1234")));
	}

	@Override
	protected void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void read() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
