package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public abstract class AbstractCollectionItemDetails<COLLECTION_ITEM extends AbstractCollectionItem<?>> extends AbstractEnumerationDetails<COLLECTION_ITEM> implements Serializable {

	private static final long serialVersionUID = 7515356383413863619L;
	
	@Input @InputText protected String collection;
	
	public AbstractCollectionItemDetails(COLLECTION_ITEM item) {
		super(item);
		collection = formatUsingBusiness(item.getCollection());
	}
	
	public static final String FIELD_COLLECTION = "collection";

}
