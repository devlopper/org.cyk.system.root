package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public abstract class AbstractCollectionItemDaoImpl<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationDaoImpl<ITEM> implements AbstractCollectionItemDao<ITEM,COLLECTION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readByCollectionAscending,readByCollectionDescending;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByCollectionAscending, _select().where(AbstractCollectionItem.FIELD_COLLECTION).orderBy(getReadByCollectionOrderByFieldName(), Boolean.TRUE));
		registerNamedQuery(readByCollectionDescending, _select().where(AbstractCollectionItem.FIELD_COLLECTION).orderBy(getReadByCollectionOrderByFieldName(), Boolean.FALSE));
	}
	
	@Override
	public Collection<ITEM> readByCollection(COLLECTION collection) {
		return readByCollection(collection, Boolean.TRUE);
	}
	
	@Override
	public Collection<ITEM> readByCollection(COLLECTION collection, Boolean ascending) {
		String queryName;
		if(Boolean.TRUE.equals(ascending))
			queryName = readByCollectionAscending;
		else
			queryName = readByCollectionDescending;
		return namedQuery(queryName).parameter(AbstractCollectionItem.FIELD_COLLECTION, collection).resultMany();
	}
	
	protected String getReadByCollectionOrderByFieldName(){
		return AbstractIdentifiable.FIELD_IDENTIFIER;
	}
	
}
 