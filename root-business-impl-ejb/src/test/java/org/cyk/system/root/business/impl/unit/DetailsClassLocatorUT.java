package org.cyk.system.root.business.impl.unit;

import java.util.Collection;

import org.cyk.system.root.business.impl.DetailsClassLocator;
import org.cyk.system.root.business.impl.geography.LocalityDetails;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.mockito.InjectMocks;

public class DetailsClassLocatorUT extends AbstractUnitTest {

	private static final long serialVersionUID = 3681555921786058917L;
	
	@InjectMocks private DetailsClassLocator locator;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(locator);
	}
	
	@Override
	protected void _execute_() {
		super._execute_();
		assertEquals("Locality details not found", LocalityDetails.class, locator.locate(Locality.class));
	}
	
	
	
}
