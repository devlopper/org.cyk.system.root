package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.persistence.api.AbstractCollectionDao;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;
import org.cyk.utility.common.Constant;

public abstract class AbstractCollectionBusinessImpl<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>,DAO extends AbstractCollectionDao<COLLECTION, ITEM>,ITEM_DAO extends AbstractCollectionItemDao<ITEM,COLLECTION>,ITEM_BUSINESS extends AbstractCollectionItemBusiness<ITEM,COLLECTION>> extends AbstractEnumerationBusinessImpl<COLLECTION, DAO> implements AbstractCollectionBusiness<COLLECTION,ITEM>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractCollectionBusinessImpl(DAO dao) {
		super(dao); 
	}
	
	protected ITEM instanciateOneItem(String[] values,InstanciateOneListener listener){
		return getItemBusiness().instanciateOne(values,listener);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public COLLECTION instanciateOne(String code,String name,String itemCodeSeparator,String[][] items){
		COLLECTION collection = instanciateOne(code,name);
		collection.setItemCodeSeparator(itemCodeSeparator);
		for(String[] v : items){
			ITEM item = instanciateOneItem(v,null);
			item.setCollection(collection);
			collection.getCollection().add(item);
		}
		return collection;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public COLLECTION instanciateOne(String code,String name,String[][] items){
		return instanciateOne(code, name, Constant.CHARACTER_UNDESCORE.toString(), items);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public COLLECTION instanciateOne(String code,String name,String itemCodeSeparator,String[] items){
		COLLECTION collection = instanciateOne(code,name);
		collection.setItemCodeSeparator(itemCodeSeparator);
		for(String v : items){
			ITEM item = instanciateOneItem(new String[]{v},null);
			item.setCollection(collection);
			collection.getCollection().add(item);
		}
		return collection;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public COLLECTION instanciateOne(String code,String name,String[] items){
		return instanciateOne(code, name, Constant.CHARACTER_UNDESCORE.toString(), items);
	}
	
	@Override
	public COLLECTION create(COLLECTION collection) {
		super.create(collection);
		if(collection.getCollection()!=null){
			for(ITEM item : collection.getCollection()){
				item.setCollection(collection);
				if(StringUtils.isNotBlank(item.getCollection().getItemCodeSeparator()) && !StringUtils.contains(item.getCode(), item.getCollection().getItemCodeSeparator()))
					item.setCode(item.getCollection().getCode()+item.getCollection().getItemCodeSeparator()+item.getCode());
				item = createItem(item);
			}
		}
		return collection;
	}
	
	protected abstract ITEM_DAO getItemDao();
	protected abstract ITEM_BUSINESS getItemBusiness();
	
	protected ITEM createItem(ITEM item){
		return getItemDao().create(item);
	}
	
	protected void __load__(COLLECTION collection) {
		collection.setCollection(getItemDao().readByCollection(collection));
	}
}
