package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public abstract class AbstractCollectionItemDaoImpl<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationDaoImpl<ITEM> implements AbstractCollectionItemDao<ITEM,COLLECTION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String /*readByCollectionAscending,readByCollectionDescending,*/readByCollectionsAscending,readByCollectionsDescending;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		//registerNamedQuery(readByCollectionAscending, _select().where(AbstractCollectionItem.FIELD_COLLECTION).orderBy(getReadByCollectionOrderByFieldName(), Boolean.TRUE));
		//registerNamedQuery(readByCollectionDescending, _select().where(AbstractCollectionItem.FIELD_COLLECTION).orderBy(getReadByCollectionOrderByFieldName(), Boolean.FALSE));
		registerNamedQuery(readByCollectionsAscending, _select().whereIdentifierIn(AbstractCollectionItem.FIELD_COLLECTION).orderBy(getReadByCollectionOrderByFieldName(), Boolean.TRUE));
		registerNamedQuery(readByCollectionsDescending, _select().whereIdentifierIn(AbstractCollectionItem.FIELD_COLLECTION).orderBy(getReadByCollectionOrderByFieldName(), Boolean.FALSE));
	}
	
	@Override
	public Collection<ITEM> readByCollections(Collection<COLLECTION> collections, Boolean ascending) {
		String queryName;
		if(Boolean.TRUE.equals(ascending))
			queryName = readByCollectionsAscending;
		else
			queryName = readByCollectionsDescending;
		return namedQuery(queryName).parameterIdentifiers(collections).resultMany();
	}
	
	@Override
	public Collection<ITEM> readByCollections(Collection<COLLECTION> collections) {
		return readByCollections(collections, Boolean.TRUE);
	}
	
	@Override
	public Collection<ITEM> readByCollection(COLLECTION collection, Boolean ascending) {
		return readByCollections(Arrays.asList(collection),ascending);
	}
	
	@Override
	public Collection<ITEM> readByCollection(COLLECTION collection) {
		return readByCollections(Arrays.asList(collection));
	}
	
	protected String getReadByCollectionOrderByFieldName(){
		return AbstractIdentifiable.FIELD_IDENTIFIER;
	}
	
	/*
	public ITEM readByCollectionByRelativeCode(COLLECTION collection, String relativeCode) {
		return namedQuery(readByCollectionByRelativeCode).parameter(AbstractCollectionItem.FIELD_COLLECTION, collection)
				.parameter(GlobalIdentifier.FIELD_CODE, relativeCode)
				.resultOne();
	}*/
}
 