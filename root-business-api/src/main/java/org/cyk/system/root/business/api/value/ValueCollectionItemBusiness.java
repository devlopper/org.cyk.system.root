package org.cyk.system.root.business.api.value;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness.Derive;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;

public interface ValueCollectionItemBusiness extends AbstractCollectionItemBusiness<ValueCollectionItem,ValueCollection> {

	void derive(Collection<ValueCollectionItem> valueCollectionItems,Derive listener);
	void derive(ValueCollectionItem valueCollectionItem,Derive listener);
	Collection<ValueCollectionItem> deriveByCodes(Collection<String> valueCollectionItemCodes,Derive listener);
	ValueCollectionItem deriveByCode(String valueCollectionItemCode,Derive listener);
	
}
