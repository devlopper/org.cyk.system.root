package org.cyk.system.root.persistence.api;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;

public interface AbstractCollectionDao<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractEnumerationDao<COLLECTION> {

    
}
