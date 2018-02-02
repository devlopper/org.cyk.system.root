package org.cyk.system.root.persistence.impl;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.persistence.api.AbstractCollectionDao;

public abstract class AbstractCollectionDaoImpl<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractEnumerationDaoImpl<COLLECTION> implements AbstractCollectionDao<COLLECTION,ITEM>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
}
 