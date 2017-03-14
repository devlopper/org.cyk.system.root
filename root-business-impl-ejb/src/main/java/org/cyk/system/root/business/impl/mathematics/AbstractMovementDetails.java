package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionItemDetails;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractMovementDetails<ITEM extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends AbstractCollectionItemDetails<ITEM,COLLECTION> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText protected String action,value;
	
	public AbstractMovementDetails(ITEM item) {
		super(item);
		if(getMovement().getAction()!=null)
			action = getMovement().getAction().getName();
		
		value = formatNumber(getMovement().getValue());
	}
	
	protected abstract Movement getMovement();
	
	/**/
	
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_VALUE = "value";
	
	/**/
	
	public static abstract class Extends<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractMovementDetails<ITEM, COLLECTION> implements Serializable {
		private static final long serialVersionUID = 1L;

		public Extends(ITEM item) {
			super(item);
		}
		
	}
	
}