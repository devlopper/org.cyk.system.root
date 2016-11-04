package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovementDetails extends AbstractMovementDetails<Movement,MovementCollection> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String action,value,supportingDocumentIdentifier;
	
	public MovementDetails(Movement movement) {
		super(movement);
		if(movement.getAction()!=null)
			action = movement.getAction().getName();
		
		value = formatNumber(movement.getValue());
		supportingDocumentIdentifier = StringUtils.defaultString(movement.getSupportingDocumentIdentifier());
	}
	
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_SUPPORTING_DOCUMENT_IDENTIFIER = "supportingDocumentIdentifier";

	@Override
	protected Movement getMovement() {
		return master;
	}
	
	@Override
	protected MovementCollection getCollection() {
		return getMovement().getCollection();
	}
}