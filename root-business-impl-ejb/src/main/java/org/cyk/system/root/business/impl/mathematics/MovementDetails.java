package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.AbstractEnumerationDetails;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class MovementDetails extends AbstractEnumerationDetails<Movement> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String action,value,date,supportingDocumentIdentifier;
	
	public MovementDetails(Movement movement) {
		super(movement);
		action = movement.getAction().getName();
		date = formatDateTime(movement.getDate());
		value = formatNumber(movement.getValue());
		supportingDocumentIdentifier = StringUtils.defaultString(movement.getSupportingDocumentIdentifier());
	}
	
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_DATE = "date";
}