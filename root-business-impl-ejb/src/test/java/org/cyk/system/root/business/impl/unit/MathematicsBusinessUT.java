package org.cyk.system.root.business.impl.unit;

import java.util.Collection;

import org.cyk.system.root.business.impl.mathematics.MathematicsBusinessImpl;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.mockito.InjectMocks;

public class MathematicsBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private MathematicsBusinessImpl mathematicsBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(mathematicsBusiness);
	}
	
	

}
