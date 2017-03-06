package org.cyk.system.root.business.api;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;

public interface AbstractCollectionBusiness<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractEnumerationBusiness<COLLECTION> {
    
	COLLECTION instanciateOne(String code,String name,String[][] items);
	COLLECTION instanciateOne(String code,String name,String[] items);
	
	ITEM add(COLLECTION collection,ITEM item);
	ITEM remove(COLLECTION collection,ITEM item);
}
