package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public abstract class AbstractCollectionItemBusinessImpl<ITEM extends AbstractCollectionItem<COLLECTION>,DAO extends AbstractCollectionItemDao<ITEM,COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationBusinessImpl<ITEM, DAO> implements AbstractCollectionItemBusiness<ITEM,COLLECTION>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractCollectionItemBusinessImpl(DAO dao) {
		super(dao); 
	}   
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<ITEM> findByCollection(COLLECTION collection) {
		return dao.readByCollection(collection);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<ITEM> findByCollection(COLLECTION collection, Boolean ascending) {
		return dao.readByCollection(collection, ascending);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String findRelativeCode(ITEM item) {
		return RootBusinessLayer.getInstance().getRelativeCode(item.getCollection(), item.getCode());
	}

	@Override
	public ITEM instanciateOneRandomly(COLLECTION collection) {
		ITEM item = instanciateOneRandomly();
		item.setCollection(collection);
		collection.add(item);
		return item;
	}
	
	
}
