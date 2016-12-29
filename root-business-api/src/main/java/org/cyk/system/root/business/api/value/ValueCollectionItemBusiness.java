package org.cyk.system.root.business.api.value;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness.DeriveArguments;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;

public interface ValueCollectionItemBusiness extends AbstractCollectionItemBusiness<ValueCollectionItem,ValueCollection> {

	void derive(Collection<ValueCollectionItem> valueCollectionItems,DeriveArguments arguments);
	void derive(ValueCollectionItem valueCollectionItem,DeriveArguments arguments);
	Collection<ValueCollectionItem> deriveByCodes(Collection<String> valueCollectionItemCodes,DeriveArguments arguments);
	ValueCollectionItem deriveByCode(String valueCollectionItemCode,DeriveArguments arguments);
	
}
