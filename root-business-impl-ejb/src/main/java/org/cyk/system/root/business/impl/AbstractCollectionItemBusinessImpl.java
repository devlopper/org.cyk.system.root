package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public abstract class AbstractCollectionItemBusinessImpl<ITEM extends AbstractCollectionItem<COLLECTION>,DAO extends AbstractCollectionItemDao<ITEM,COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationBusinessImpl<ITEM, DAO> implements AbstractCollectionItemBusiness<ITEM,COLLECTION>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractCollectionItemBusinessImpl(DAO dao) {
		super(dao); 
	}   
	
	@Override
	protected void setAutoSettedProperties(ITEM item) {
		super.setAutoSettedProperties(item);
		item.setCode(StringUtils.defaultIfBlank(item.getCode(), /*RandomStringUtils.randomAlphanumeric(5)*/ RootConstant.Code.generateFromString(item.getName())));
		if(item.getCollection()!=null && StringUtils.isNotBlank(item.getCollection().getCode()) && StringUtils.isNotBlank(item.getCollection().getItemCodeSeparator()) 
				&& !StringUtils.contains(item.getCode(), item.getCollection().getItemCodeSeparator()))
			item.setCode(item.getCollection().getCode()+item.getCollection().getItemCodeSeparator()+item.getCode());
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<ITEM> findByCollection(COLLECTION collection) {
		return dao.readByCollection(collection);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<ITEM> findByCollection(COLLECTION collection, Boolean ascending) {
		return dao.readByCollection(collection, ascending);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<ITEM> findByCollections(Collection<COLLECTION> collections) {
		return dao.readByCollections(collections);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<ITEM> findByCollections(Collection<COLLECTION> collections, Boolean ascending) {
		return dao.readByCollections(collections, ascending);
	}
	/*
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String findRelativeCode(ITEM item) {
		return RootBusinessLayer.getInstance().getRelativeCode(item.getCollection(), item.getCode());
	}*/

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

	@Override
	public ITEM find(String collectionCode, String relativeCode) {
		COLLECTION collection = inject(BusinessInterfaceLocator.class).injectTyped(getCollectionClass()).find(collectionCode);
		return dao.read(RootConstant.Code.generate(collection, relativeCode));
	}
	
	@SuppressWarnings("unchecked")
	protected Class<COLLECTION> getCollectionClass(){
		return (Class<COLLECTION>) commonUtils.getClassParameterAt(getClass(), 2);
	}
	
	@Override
	protected ITEM __instanciateOne__(String[] values, InstanciateOneListener<ITEM> listener) {
		ITEM item = super.__instanciateOne__(values, listener);
		set(listener.getSetListener().setIndex(10).setFieldType(getCollectionClass())
				, AbstractCollectionItem.FIELD_COLLECTION);
		return item;
	}
	
	/**/

	
	
}
