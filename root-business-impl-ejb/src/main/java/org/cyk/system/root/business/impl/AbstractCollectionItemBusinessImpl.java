package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
	protected void setAutoSettedProperties(ITEM item) {
		super.setAutoSettedProperties(item);
		item.setCode(StringUtils.defaultIfBlank(item.getCode(), RandomStringUtils.randomAlphanumeric(5)));
		if(StringUtils.isNotBlank(item.getCollection().getCode()) && StringUtils.isNotBlank(item.getCollection().getItemCodeSeparator()) 
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
	
	/**/
	
	public static String getRelativeCode(AbstractCollection<?> collection,String code){
		return StringUtils.isBlank(collection.getItemCodeSeparator()) ? code : StringUtils.split(code,collection.getItemCodeSeparator())[1];
	}
	
	public static String buildCode(AbstractCollection<?> collection,String code){
		if(StringUtils.isBlank(collection.getItemCodeSeparator()))
			return code;
		else
			return collection.getCode()+collection.getItemCodeSeparator()+ code;
	}
}
