package org.cyk.system.root.service.impl.integration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.party.ApplicationBusinessImpl;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.system.root.model.security.UserAccountSearchCriteria;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class SecurityBusinessIT extends AbstractBusinessIT {

	private static final long serialVersionUID = 8691254326402622637L;
	
	@Inject private UserAccountBusiness userAccountBusiness;
	
	@Deployment 
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	}
		 	
    @Override
    protected void create() {}

    @Override
    protected void read() {}
    
    @Override
    protected void update() {}
    
    @Override
    protected void delete() {}
		
    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
    	ApplicationBusinessImpl.Listener.COLLECTION.add(new ApplicationBusinessImpl.Listener.Adapter.Default(){
			private static final long serialVersionUID = 6148913289155659043L;
			@Override
    		public void installationStarted(Installation installation) {
    			installation.getApplication().setUniformResourceLocatorFilteringEnabled(Boolean.TRUE);
    			installation.getApplication().setWebContext("context");
    			super.installationStarted(installation);
    		}
    	});
    	installApplication();
    	
    	//userAccountBusiness.create(new UserAccount(RootBusinessLayer.getInstance().getPersonBusiness().findOneRandomly()
    	//		, new Credentials("123456789", "789456123"), new Date(), RootBusinessLayer.getInstance().getRoleManager()));
    	
    	userAccountBusiness.connect(new Credentials("manager", "123"));
    	
    	UserAccountSearchCriteria criteria = new UserAccountSearchCriteria(null);
    	//System.out.println(RootBusinessLayer.getInstance().getAdministratorRole());
    	criteria.getRoleExcluded().add(inject(RoleBusiness.class).find(Role.ADMINISTRATOR));
    	
    	/*System.out.println(userAccountBusiness.findAll());
    	System.out.println(userAccountBusiness.findAllExcludeRoles(Arrays.asList(RootBusinessLayer.getInstance().getAdministratorRole())));
    	System.out.println(userAccountBusiness.findByCriteria(criteria));*/
    	Assert.assertEquals(1l, userAccountBusiness.countByCriteria(criteria).longValue());
    	
    	UniformResourceLocator.Builder uniformResourceLocatorBuilder = new UniformResourceLocator.Builder();
    	uniformResourceLocatorBuilder.instanciate();
    	uniformResourceLocatorBuilder.addParameters("mc",Person.class);
    	
    	//System.out.println(uniformResourceLocatorBuilder.build());
    	
    	urls();
    }

    private void urls(){
    	UniformResourceLocator u1,u2,u3/*,u4,u5,u6,u7,u8,u9*/;
    	create(u1 = new UniformResourceLocator("/context/"));
    	create(u2 = new UniformResourceLocator("/context/a"));
    	create(u3 = new UniformResourceLocator("/context/b"));
    	create(/*u4 = */new UniformResourceLocator("/context/a/1"));
    	create(/*u5 = */new UniformResourceLocator("/context/a/2"));
    	
    	//System.out.println(RootBusinessLayer.getInstance().getUniformResourceLocatorBusiness().findAll());
    	
    	create(new RoleUniformResourceLocator(inject(RoleBusiness.class).find(Role.USER),u1));
    	create(new RoleUniformResourceLocator(inject(RoleBusiness.class).find(Role.USER),u2));
    	
    	create(new RoleUniformResourceLocator(inject(RoleBusiness.class).find(Role.ADMINISTRATOR),u1));
    	create(new RoleUniformResourceLocator(inject(RoleBusiness.class).find(Role.ADMINISTRATOR),u3));
    	
    	isUrlAccessible("/path",Boolean.FALSE,Boolean.FALSE, inject(RoleBusiness.class).find(Role.USER));
    	isUrlAccessible("/a",Boolean.TRUE,Boolean.TRUE, inject(RoleBusiness.class).find(Role.USER));
    	isUrlAccessible("/a/1",Boolean.TRUE,Boolean.FALSE, inject(RoleBusiness.class).find(Role.USER));
    	
    	//System.out.println(RootBusinessLayer.getInstance().getRoleUniformResourceLocatorBusiness().findAll());
    }
    
    private void isUrlAccessible(String path,Boolean byApplication,Boolean byRole,Role...roles){
    	URL _url = null;
    	try {
    		_url = new URL("http://www.mydomain.com"+path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    	assertThat("Path "+path+" is accessible", inject(UniformResourceLocatorBusiness.class).isAccessible(_url).equals(byApplication));
    	if(roles!=null && roles.length>0)
    		assertThat("Path "+path+" is accessible by "+StringUtils.join(roles,",")
    			, inject(RoleUniformResourceLocatorBusiness.class).isAccessible(_url,Arrays.asList(roles)).equals(byRole));
    }
    
    


}
