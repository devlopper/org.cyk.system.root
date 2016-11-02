package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public abstract class AbstractCollectionItemBusinessImpl<ITEM extends AbstractCollectionItem<COLLECTION>,DAO extends AbstractCollectionItemDao<ITEM,COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationBusinessImpl<ITEM, DAO> implements AbstractCollectionItemBusiness<ITEM,COLLECTION>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractCollectionItemBusinessImpl(DAO dao) {
		super(dao); 
	}   
	
	@Override
	protected Object[] getPropertyValueTokens(ITEM item, String name) {
		if(ArrayUtils.contains(new String[]{GlobalIdentifier.FIELD_CODE}, name))
			return new Object[]{item.getCollection(),StringUtils.defaultIfBlank(item.getCode(), RandomStringUtils.randomAlphanumeric(5))};
		return super.getPropertyValueTokens(item, name);
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

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ITEM instanciateOneRandomly(COLLECTION collection) {
		ITEM item = instanciateOneRandomly();
		item.setCollection(collection);
		collection.add(item);
		return item;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ITEM instanciateOne(COLLECTION collection) {
		ITEM item = instanciateOne();
		item.setCollection(collection);
		collection.add(item);
		return item;
	}
}
