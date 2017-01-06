package org.cyk.system.root.business.api.value;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness.Derive;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;

public interface ValueCollectionBusiness extends AbstractCollectionBusiness<ValueCollection,ValueCollectionItem> {
    
	void derive(Collection<ValueCollection> valueCollections,Derive listener);
	void derive(ValueCollection valueCollection,Derive listener);
	Collection<ValueCollection> deriveByCodes(Collection<String> codes,Derive listener);
	ValueCollection deriveByCode(String code,Derive listener);
	
}
	