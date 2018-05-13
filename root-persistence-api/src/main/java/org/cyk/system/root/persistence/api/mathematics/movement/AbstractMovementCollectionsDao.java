package org.cyk.system.root.persistence.api.mathematics.movement;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.persistence.api.AbstractCollectionDao;

public interface AbstractMovementCollectionsDao<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractCollectionDao<COLLECTION,ITEM> {

}
