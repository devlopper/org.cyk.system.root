package org.cyk.system.root.model.unit;


import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ModelUT extends AbstractUnitTest {
	private static final long serialVersionUID = 8008545189557409317L;

	@Test
	public void getMasterFieldNameOfInstanceOfJoinGlobalIdentifier(){
		assertEquals("movementCollection", AbstractJoinGlobalIdentifier.getMasterFieldName(MovementCollectionIdentifiableGlobalIdentifier.class));
	}
	
}
