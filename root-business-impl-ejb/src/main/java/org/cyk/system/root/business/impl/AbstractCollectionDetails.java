package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public abstract class AbstractCollectionDetails<COLLECTION extends AbstractCollection<?>> extends AbstractEnumerationDetails<COLLECTION> implements Serializable {

	private static final long serialVersionUID = 7515356383413863619L;

	@Input @InputText protected String itemCodeSeparator;
	
	public AbstractCollectionDetails(COLLECTION collection) {
		super(collection);
		itemCodeSeparator = collection.getItemCodeSeparator();
	}

	public static final String FIELD_ITEM_CODE_SEPARATOR = "itemCodeSeparator";
}
