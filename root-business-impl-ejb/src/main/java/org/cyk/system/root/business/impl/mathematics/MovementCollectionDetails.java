package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class MovementCollectionDetails extends AbstractEnumerationDetails<MovementCollection> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String value,increment,decrement;
	
	public MovementCollectionDetails(MovementCollection movementCollection) {
		super(movementCollection);
		value = formatNumber(movementCollection.getValue());
		increment = formatUsingBusiness(movementCollection.getIncrementAction());
		decrement = formatUsingBusiness(movementCollection.getDecrementAction());
	}
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_INCREMENT = "increment";
	public static final String FIELD_DECREMENT = "decrement";
}