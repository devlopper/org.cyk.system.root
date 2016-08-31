package org.cyk.system.root.business.impl.unit;

import java.util.Collection;

import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.mockito.InjectMocks;

public class BusinessInteraceLocatorUT extends AbstractUnitTest {

	private static final long serialVersionUID = 3681555921786058917L;
	
	@InjectMocks private BusinessInterfaceLocator businessInterfaceLocator;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(businessInterfaceLocator);
	}
	
	@Override
	protected void _execute_() {
		super._execute_();
		assertThat("Locality Business found", businessInterfaceLocator.locate(Locality.class)!=null);
	}
	
	
	
}
