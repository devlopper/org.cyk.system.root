package org.cyk.system.root.business.impl.unit;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import org.cyk.system.root.business.impl.network.UniformResourceLocatorBusinessImpl;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

public class UniformResourceLocatorBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private UniformResourceLocatorBusinessImpl uniformResourceLocatorBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(uniformResourceLocatorBusiness);
	}
	
	@Test
	public void crud() {
		
	}
	
	@Test
	public void find() {
		UniformResourceLocator uniformResourceLocator = new UniformResourceLocator("/a/b/xyz");
		uniformResourceLocator.getParameters().addOne(new UniformResourceLocatorParameter(null,"param1","value1"));
		uniformResourceLocator.getParameters().addOne(new UniformResourceLocatorParameter(null,"param3","value3"));
		uniformResourceLocatorBusiness.create(uniformResourceLocator);
		
		uniformResourceLocator = new UniformResourceLocator("/d/e");
		uniformResourceLocator.getParameters().addOne(new UniformResourceLocatorParameter(null,"param1","value1"));
		uniformResourceLocatorBusiness.create(uniformResourceLocator);
		
		
		assertIsAccessible("/a/b/xyz?param1=value1&param3=value3");
		assertIsAccessible("/a/b/xyz?param3=value3&param1=value1");
		assertIsAccessible("/d/e?param1=value1&param2=value2");
		assertIsAccessible("/a/b/xyz/subpath?param3=value3&param1=value1");
		
	}

	private void assertIsAccessible(String url){
		try {
			Assert.assertTrue(uniformResourceLocatorBusiness.isAccessible(new URL("http://localhost:8080"+url)));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
}
