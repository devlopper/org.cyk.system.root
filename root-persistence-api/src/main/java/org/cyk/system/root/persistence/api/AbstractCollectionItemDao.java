package org.cyk.system.root.persistence.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;

public interface AbstractCollectionItemDao<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationDao<ITEM> {

	Collection<ITEM> readByCollection(COLLECTION collection);
	Collection<ITEM> readByCollections(Collection<COLLECTION> collections);
	Collection<ITEM> readByCollection(COLLECTION collection,Boolean ascending);
	Collection<ITEM> readByCollections(Collection<COLLECTION> collections,Boolean ascending);
	
	//ITEM readByCollectionByRelativeCode(COLLECTION collection,String relativeCode);
}
