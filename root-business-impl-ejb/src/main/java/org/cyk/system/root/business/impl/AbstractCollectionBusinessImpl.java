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
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.Constant;

public abstract class AbstractCollectionBusinessImpl<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>,DAO extends AbstractCollectionDao<COLLECTION, ITEM>,ITEM_DAO extends AbstractCollectionItemDao<ITEM,COLLECTION>,ITEM_BUSINESS extends AbstractCollectionItemBusiness<ITEM,COLLECTION>> extends AbstractEnumerationBusinessImpl<COLLECTION, DAO> implements AbstractCollectionBusiness<COLLECTION,ITEM>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	protected Class<ITEM> itemClass;
	
	@SuppressWarnings("unchecked")
	public AbstractCollectionBusinessImpl(DAO dao) {
		super(dao);
		itemClass = (Class<ITEM>) commonUtils.getClassParameterAt(getClass(), 1);
	}
	
	protected ITEM instanciateOneItem(String[] values){
		return getItemBusiness().instanciateOne(values);
	}

	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public COLLECTION instanciateOne(String code,String name,String[][] items){
		COLLECTION collection = instanciateOne(code,name);
		collection.setItemCodeSeparator(Constant.CHARACTER_UNDESCORE.toString());
		for(String[] v : items){
			ITEM item = instanciateOneItem(v);
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
			ITEM item = instanciateOneItem(new String[]{null,v});
			item.setCollection(collection);
			collection.add(item);
		}
		return collection;
	}
		
	@Override
	public ITEM add(COLLECTION collection, ITEM item) {
		return addOrRemove(collection, item, Boolean.TRUE);
	}

	@Override
	public ITEM remove(COLLECTION collection, ITEM item) {
		return addOrRemove(collection, item, Boolean.FALSE);
	}
	
	protected ITEM addOrRemove(COLLECTION collection, ITEM item,Boolean add) {
		if(Boolean.TRUE.equals(add)){
			Boolean found = Boolean.FALSE;
			if(collection.getCollection()!=null)
				for(ITEM index : collection.getCollection())
					if(index == item){
						found = Boolean.TRUE;
						break;
					}
			if(Boolean.FALSE.equals(found)){
				collection.add(item);	
			}
		}else{
			if(collection.getCollection()!=null)
				collection.getCollection().remove(item);
			collection.addToDelete(item);
		}
		return item;
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
	
	@SuppressWarnings("unchecked")
	protected ITEM_DAO getItemDao(){
		return (ITEM_DAO) inject(PersistenceInterfaceLocator.class).injectTyped(itemClass);
	}
	
	@SuppressWarnings("unchecked")
	protected ITEM_BUSINESS getItemBusiness(){
		return (ITEM_BUSINESS) inject(BusinessInterfaceLocator.class).injectTyped(itemClass);
	}
	
	protected ITEM createItem(ITEM item){
		return getItemBusiness().create(item);
	}
	
	protected void __load__(COLLECTION collection) {
		collection.setCollection(getItemDao().readByCollection(collection));
	}
}
