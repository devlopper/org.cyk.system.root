package org.cyk.system.root.business.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;

public interface AbstractCollectionItemBusiness<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationBusiness<ITEM> {
    
	Collection<ITEM> findByCollection(COLLECTION collection);
    
}