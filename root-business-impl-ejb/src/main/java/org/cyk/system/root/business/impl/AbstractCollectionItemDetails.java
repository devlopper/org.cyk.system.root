package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public abstract class AbstractCollectionItemDetails<ITEM extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends AbstractEnumerationDetails<ITEM> implements Serializable {

	private static final long serialVersionUID = 7515356383413863619L;
	
	@Input @InputText protected String collection;
	
	public AbstractCollectionItemDetails(ITEM item) {
		super(item);
		collection = formatUsingBusiness(getCollection());
	}
	
	protected abstract COLLECTION getCollection();
	
	public static final String FIELD_COLLECTION = "collection";

	/**/
	
	public static abstract class AbstractDefault<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractCollectionItemDetails<ITEM,COLLECTION> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public AbstractDefault(ITEM item) {
			super(item);
		}
		
		@Override
		protected COLLECTION getCollection() {
			return master.getCollection();
		}
	}
}
