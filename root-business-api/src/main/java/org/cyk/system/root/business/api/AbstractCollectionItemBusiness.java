package org.cyk.system.root.business.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;

public interface AbstractCollectionItemBusiness<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationBusiness<ITEM> {
    
	Collection<ITEM> findByCollection(COLLECTION collection);
	Long countByCollection(COLLECTION collection);
	
	Collection<ITEM> findByCollections(Collection<COLLECTION> collections);
	Collection<ITEM> findByCollection(COLLECTION collection,Boolean ascending);
	Collection<ITEM> findByCollections(Collection<COLLECTION> collections,Boolean ascending);
    
	//String findRelativeCode(ITEM item);
	
	ITEM instanciateOne(COLLECTION collection);
	ITEM instanciateOne(COLLECTION collection,Boolean addable);
	ITEM instanciateOne(COLLECTION collection,String code,String name,Boolean addable);
	ITEM instanciateOne(COLLECTION collection,String code,String name);
	ITEM instanciateOneRandomly(COLLECTION collection);
	
	ITEM find(String collectionCode,String relativeCode);
	
}
