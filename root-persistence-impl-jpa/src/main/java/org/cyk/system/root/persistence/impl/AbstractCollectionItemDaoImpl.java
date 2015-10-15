package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public abstract class AbstractCollectionItemDaoImpl<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationDaoImpl<ITEM> implements AbstractCollectionItemDao<ITEM,COLLECTION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByCollection;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByCollection, _select().where(AbstractCollectionItem.FIELD_COLLECTION));
	}
	
	@Override
	public Collection<ITEM> readByCollection(COLLECTION collection) {
		return namedQuery(readByCollection).parameter(AbstractCollectionItem.FIELD_COLLECTION, collection).resultMany();
	}
	
}
 