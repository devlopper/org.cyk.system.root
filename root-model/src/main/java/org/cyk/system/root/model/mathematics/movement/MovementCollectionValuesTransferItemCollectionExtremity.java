package org.cyk.system.root.model.mathematics.movement;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.cyk.system.root.model.AbstractModelElement;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class MovementCollectionValuesTransferItemCollectionExtremity extends AbstractModelElement implements Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean movementCollectionIsBuffer;
	
	/**/
	
	public static final String FIELD_MOVEMENT_COLLECTION_IS_BUFFER = "movementCollectionIsBuffer";
	
	public static final String COLUMN_MOVEMENT_COLLECTION_IS_BUFFER = FIELD_MOVEMENT_COLLECTION_IS_BUFFER;
}
