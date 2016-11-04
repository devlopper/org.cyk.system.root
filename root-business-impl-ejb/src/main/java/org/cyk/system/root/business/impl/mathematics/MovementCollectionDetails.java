package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionDetails;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class MovementCollectionDetails extends AbstractCollectionDetails<MovementCollection> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String value,interval,incrementAction,decrementAction,supportDocumentIdentifier;
	
	public MovementCollectionDetails(MovementCollection movementCollection) {
		super(movementCollection);
		value = formatNumber(movementCollection.getValue());
		interval = formatUsingBusiness(movementCollection.getInterval());
		incrementAction = formatUsingBusiness(movementCollection.getIncrementAction());
		decrementAction = formatUsingBusiness(movementCollection.getDecrementAction());
		supportDocumentIdentifier = formatResponse(movementCollection.getSupportDocumentIdentifier());
	}
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_INTERVAL = "interval";
	public static final String FIELD_INCREMENT_ACTION = "incrementAction";
	public static final String FIELD_DECREMENT_ACTION = "decrementAction";
	public static final String FIELD_SUPPORT_DOCUMENT_IDENTIFIER = "supportDocumentIdentifier";
}