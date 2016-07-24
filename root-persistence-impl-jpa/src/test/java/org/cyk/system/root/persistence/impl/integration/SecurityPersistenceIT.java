package org.cyk.system.root.persistence.impl.integration;

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Permission;
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
	
	private Role adminRole,managerRole;
		
	@Override
	protected void populate() {
		Permission p1,p2,p3;
		create(p1 = new Permission("p1"));
		create(p2 = new Permission("p2"));
		create(p3 = new Permission("p3"));
		
		adminRole = new Role("ADMIN", "Admin");
		adminRole.getPermissions().add(p1);
		adminRole.getPermissions().add(p2);
		adminRole.getPermissions().add(p3);
		create(adminRole);
		
		managerRole = new Role("MANAGER", "Manager");
		managerRole.getPermissions().add(p2);
		create(managerRole);
		
		createAccount("paul", "admin", "123","mymail@mail.com", adminRole);
		createAccount("zadi", "manager", "123","m1@k.net", managerRole);
		
	}
	 
	@Override
	protected void queries() {
		Assert.assertNotNull(credentialsDao.readByUsername("admin"));
		Assert.assertNotNull(credentialsDao.readByUsernameByPassword("admin","123"));
		
		Assert.assertNull(credentialsDao.readByUsername("admin1"));
		Assert.assertNull(credentialsDao.readByUsernameByPassword("admin","1231"));
		
		Assert.assertNotNull(userAccountDao.readByCredentials(new Credentials("admin", "123")));
		Assert.assertNull(userAccountDao.readByCredentials(new Credentials("admin", "1234")));
		
		Assert.assertEquals(2l,userAccountDao.readAll().size());
		Assert.assertEquals(2l,userAccountDao.countAll().longValue());
		
		Assert.assertEquals(1l,userAccountDao.readAllExcludeRoles(Arrays.asList(adminRole)).size());
		Assert.assertEquals(1l,userAccountDao.countAllExcludeRoles(Arrays.asList(adminRole)).longValue());
		
		/*
		System.out.println(userAccountDao.readByUsername("admin"));
		
		System.out.println(permissionDao.readByUserAccount(userAccountDao.readByCredentials(new Credentials("admin", "123"))));
		System.out.println(permissionDao.readByUserAccount(userAccountDao.readByCredentials(new Credentials("manager", "123"))));
		
		System.out.println(permissionDao.readByRoleId(r1id));
		System.out.println(permissionDao.readByRolesIds(new HashSet<>(Arrays.asList(r1id,r2id))));
		System.out.println(permissionDao.readByRolesIds(new HashSet<>(Arrays.asList(r2id))));
		*/
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
	
	
	private void createAccount(String personName,String username,String password,String email,Role...roles){
		ElectronicMail electronicMail = new ElectronicMail(email);
		Person person = new Person(personName, null);
		electronicMail.setCollection(person.getContactCollection());
		
		create(person.getContactCollection());
		create(electronicMail);
		create(person);
	    
		UserAccount userAccount = new UserAccount(person, new Credentials(username, password),new Date(), roles);
	    create(userAccount);
	}
	
}
