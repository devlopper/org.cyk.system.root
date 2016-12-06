package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

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
	public COLLECTION instanciateOne(String code,String name,String[][] items){
		COLLECTION collection = instanciateOne(code,name);
		collection.setItemCodeSeparator(Constant.CHARACTER_UNDESCORE.toString());
		for(String[] v : items){
			ITEM item = instanciateOneItem(v,null);
			item.setCollection(collection);
			collection.add(item);
		}
		return collection;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public COLLECTION instanciateOne(String code,String name,String[] items){
		COLLECTION collection = instanciateOne(code,name);
		//if(collection.getItemCodeSeparator()==null)
			collection.setItemCodeSeparator(Constant.CHARACTER_UNDESCORE.toString());
		for(String v : items){
			ITEM item = instanciateOneItem(new String[]{v},null);
			item.setCollection(collection);
			collection.add(item);
		}
		return collection;
	}
		
	@Override
	public COLLECTION create(COLLECTION collection) {
		super.create(collection);
		if(collection.getCollection()!=null){
			for(ITEM item : collection.getCollection()){
				item.setCollection(collection);
				item = createItem(item);
			}
		}
		return collection;
	}
	
	@Override
	public COLLECTION update(COLLECTION collection) {
		if(collection.getCollection()!=null)
			for(ITEM item : collection.getCollection()){
				getItemBusiness().update(item);
			}
		if(collection.getCollectionToDelete()!=null)
			for(ITEM item : collection.getCollectionToDelete()){
				getItemBusiness().delete(item);
			}
		return super.update(collection);
	}
	
	@Override
	public COLLECTION delete(COLLECTION collection) {
		for(ITEM item : getItemDao().readByCollection(collection)){
			item.setCollection(null);
			getItemBusiness().delete(item);
		}
		return super.delete(collection);
	}
	
	protected abstract ITEM_DAO getItemDao();
	protected abstract ITEM_BUSINESS getItemBusiness();
	
	protected ITEM createItem(ITEM item){
		return getItemBusiness().create(item);
	}
	
	protected void __load__(COLLECTION collection) {
		collection.setCollection(getItemDao().readByCollection(collection));
	}
}
