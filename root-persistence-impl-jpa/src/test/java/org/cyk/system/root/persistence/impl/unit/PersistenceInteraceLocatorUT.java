package org.cyk.system.root.persistence.impl.unit;

import java.util.Collection;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.mockito.InjectMocks;

public class PersistenceInteraceLocatorUT extends AbstractUnitTest {

	private static final long serialVersionUID = 3681555921786058917L;
	
	@InjectMocks private PersistenceInterfaceLocator persistenceInterfaceLocator;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(persistenceInterfaceLocator);
	}
	
	@Override
	protected void _execute_() {
		super._execute_();
		assertThat("Locality Dao found", persistenceInterfaceLocator.locate(Locality.class)!=null);
		assertThat("Locality Dao injected", persistenceInterfaceLocator.injectLocated(Locality.class)!=null);
	}
	
	
	
}
