package org.cyk.system.root.business.api.value;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness.DeriveArguments;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;

public interface ValueCollectionBusiness extends AbstractCollectionBusiness<ValueCollection,ValueCollectionItem> {
    
	void derive(Collection<ValueCollection> valueCollections,DeriveArguments arguments);
	void derive(ValueCollection valueCollection,DeriveArguments arguments);
	Collection<ValueCollection> deriveByCodes(Collection<String> valueCollectionCodes,DeriveArguments arguments);
	ValueCollection deriveByCode(String valueCollectionCode,DeriveArguments arguments);
}
	