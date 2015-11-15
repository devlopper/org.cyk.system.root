package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public abstract class AbstractCollectionItemBusinessImpl<ITEM extends AbstractCollectionItem<COLLECTION>,DAO extends AbstractCollectionItemDao<ITEM,COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationBusinessImpl<ITEM, DAO> implements AbstractCollectionItemBusiness<ITEM,COLLECTION>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractCollectionItemBusinessImpl(DAO dao) {
		super(dao); 
	}   
	
	@Override
	public Collection<ITEM> findByCollection(COLLECTION collection) {
		return dao.readByCollection(collection);
	}
	
	@Override
	public Collection<ITEM> findByCollection(COLLECTION collection, Boolean ascending) {
		return dao.readByCollection(collection, ascending);
	}
	
}
