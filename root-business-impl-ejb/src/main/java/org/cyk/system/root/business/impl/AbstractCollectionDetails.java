package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public abstract class AbstractCollectionDetails<COLLECTION extends AbstractIdentifiable> extends AbstractEnumerationDetails<COLLECTION> implements Serializable {

	private static final long serialVersionUID = 7515356383413863619L;

	@Input @InputText protected String itemCodeSeparator;
	
	public AbstractCollectionDetails(COLLECTION collection) {
		super(collection);
		setMaster(collection);
		
	}
	
	public abstract AbstractCollection<?> getCollection();

	public void setMaster(COLLECTION collection) {
		super.setMaster(collection);
		if(collection==null){
			
		}else{
			itemCodeSeparator = getCollection().getItemCodeSeparator();	
		}	
	}
	
	/**/
	
	public static final String FIELD_ITEM_CODE_SEPARATOR = "itemCodeSeparator";
	
	/**/
	
	public static class Extends<COLLECTION extends AbstractCollection<?>> extends AbstractCollectionDetails<COLLECTION> implements Serializable  {
		private static final long serialVersionUID = 1L;

		public Extends(COLLECTION collection) {
			super(collection);
		}
		
		@Override
		public AbstractCollection<?> getCollection() {
			return master;
		}
	}
}
