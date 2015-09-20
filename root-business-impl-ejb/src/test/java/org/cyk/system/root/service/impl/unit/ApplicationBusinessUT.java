package org.cyk.system.root.service.impl.unit;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import org.cyk.system.root.business.impl.party.ApplicationBusinessImpl;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

public class ApplicationBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private ApplicationBusinessImpl applicationBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(applicationBusiness);
	}
	
	@Test
	public void find() {
		UniformResourceLocator uniformResourceLocator = new UniformResourceLocator("/a/b/xyz");
		uniformResourceLocator.getParameters().add(new UniformResourceLocatorParameter(null,"param1","value1"));
		uniformResourceLocator.getParameters().add(new UniformResourceLocatorParameter(null,"param3","value3"));
		applicationBusiness.getWhiteListedUrls().add(uniformResourceLocator);
		
		uniformResourceLocator = new UniformResourceLocator("/d/e");
		uniformResourceLocator.getParameters().add(new UniformResourceLocatorParameter(null,"param1","value1"));
		applicationBusiness.getWhiteListedUrls().add(uniformResourceLocator);
		
		try {
			Assert.assertNotNull(applicationBusiness.find(applicationBusiness.getWhiteListedUrls(), new URL("http://localhost:8080/a/b/xyz?param1=value1&param3=value3")));
			Assert.assertNotNull(applicationBusiness.find(applicationBusiness.getWhiteListedUrls(), new URL("http://localhost:8080/a/b/xyz?param3=value3&param1=value1")));
			Assert.assertNotNull(applicationBusiness.find(applicationBusiness.getWhiteListedUrls(), new URL("http://localhost:8080/d/e?param1=value1&param2=value2")));
			Assert.assertNotNull(applicationBusiness.find(applicationBusiness.getWhiteListedUrls(), new URL("http://localhost:8080/a/b/xyz/subpath?param3=value3&param1=value1")));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	
	
}
