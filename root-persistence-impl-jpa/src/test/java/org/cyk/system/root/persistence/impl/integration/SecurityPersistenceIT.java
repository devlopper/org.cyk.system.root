package org.cyk.system.root.persistence.impl.integration;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import javax.inject.Inject;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Permission;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.persistence.api.security.CredentialsDao;
import org.cyk.system.root.persistence.api.security.PermissionDao;
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
	@Inject private PermissionDao permissionDao;
	
	private Long r1id,r2id;
		
	@Override
	protected void populate() {
		Permission p1,p2,p3;
		create(p1 = new Permission("p1"));
		create(p2 = new Permission("p2"));
		create(p3 = new Permission("p3"));
		
		Role adminRole,managerRole;
		adminRole = new Role("ADMIN", "Admin");
		adminRole.getPermissions().add(p1);
		adminRole.getPermissions().add(p2);
		adminRole.getPermissions().add(p3);
		create(adminRole);
		r1id = adminRole.getIdentifier();
		
		managerRole = new Role("MANAGER", "Manager");
		managerRole.getPermissions().add(p2);
		create(managerRole);
		r2id = managerRole.getIdentifier();
		
		createAccount("paul", "admin", "123", adminRole);
		createAccount("zadi", "manager", "123", managerRole);
		/*
		Person person = new Person("Paul", "Zadi");
		person.setContactCollection(null);
		create(person);
	    Credentials credentials = new Credentials("admin", "123");
	    UserAccount userAccount = new UserAccount(person, credentials, new Role[]{});
	    create(userAccount);
	    */
	}
	 
	@Override
	protected void queries() {
		Assert.assertNotNull(credentialsDao.readByUsername("admin"));
		Assert.assertNotNull(credentialsDao.readByUsernameByPassword("admin","123"));
		
		Assert.assertNull(credentialsDao.readByUsername("admin1"));
		Assert.assertNull(credentialsDao.readByUsernameByPassword("admin","1231"));
		
		Assert.assertNotNull(userAccountDao.readByCredentials(new Credentials("admin", "123")));
		Assert.assertNull(userAccountDao.readByCredentials(new Credentials("admin", "1234")));
		
		System.out.println(permissionDao.readByUserAccount(userAccountDao.readByCredentials(new Credentials("admin", "123"))));
		System.out.println(permissionDao.readByUserAccount(userAccountDao.readByCredentials(new Credentials("manager", "123"))));
		
		System.out.println(permissionDao.readByRoleId(r1id));
		System.out.println(permissionDao.readByRolesIds(new HashSet<>(Arrays.asList(r1id,r2id))));
		System.out.println(permissionDao.readByRolesIds(new HashSet<>(Arrays.asList(r2id))));
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
	
	
	private void createAccount(String personName,String username,String password,Role...roles){
		Person person = new Person(personName, null);
		person.setContactCollection(null);
		person.setRegistrationDate(new Date());
		create(person);
	    UserAccount userAccount = new UserAccount(person, new Credentials(username, password), roles);
	    create(userAccount);
	}
	
}
