package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

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
import org.cyk.utility.common.LogMessage;

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
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder(Boolean.TRUE.equals(add) ? "Add":"Remove", item.getClass().getSimpleName());
		if(Boolean.TRUE.equals(add)){
			Boolean found = Boolean.FALSE;
			if(collection.getItems().getCollection()!=null)
				for(ITEM index : collection.getItems().getCollection())
					if(index == item){
						found = Boolean.TRUE;
						break;
					}
			logMessageBuilder.addParameters("found",found);
			if(Boolean.FALSE.equals(found)){
				collection.add(item);
				logMessageBuilder.addParameters("item",item);
			}
		}else{
			if(collection.getItems().getCollection()!=null)
				collection.getItems().getCollection().remove(item);
			collection.addToDelete(item);
		}
		logTrace(logMessageBuilder);
		return item;
	}

	@Override
	protected void afterCreate(COLLECTION collection) {
		if(collection.getItems().getSynchonizationEnabled()==null || collection.getItems().isSynchonizationEnabled()){
			for(ITEM item : collection.getItems().getCollection()){
				item.setCollection(collection);
			}
			getItemBusiness().create(collection.getItems().getCollection());
		}
		super.afterCreate(collection);
	}

	/*@Override
	protected void beforeUpdate(COLLECTION collection) {
		super.beforeUpdate(collection);
		if(collection.getItems().getSynchonizationEnabled()==null || collection.getItems().isSynchonizationEnabled()){
			if(collection.getItems().getCollection()!=null){
				getItemBusiness().update(collection.getItems().getCollection());
			}	
		}
		
		if(collection.getCollectionToDelete()!=null)
			for(ITEM item : collection.getCollectionToDelete()){
				getItemBusiness().delete(item);
			}
	}*/
	
	@Override
	protected void afterUpdate(COLLECTION collection) {
		super.afterUpdate(collection);
		if(collection.getItems().isSynchonizationEnabled()){
			Collection<ITEM> database = getItemDao().readByCollection(collection);
			delete(itemClass,database, collection.getItems().getCollection());
			getItemBusiness().update(collection.getItems().getCollection());
		}
	}

	@Override
	protected void beforeDelete(COLLECTION collection) {
		super.beforeDelete(collection);
		getItemBusiness().delete(getItemDao().readByCollection(collection));
		/*
		for(ITEM item : getItemDao().readByCollection(collection)){
			item.setCollection(null);
			getItemBusiness().delete(item);
		}
		*/
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
		collection.getItems().setCollection(getItemDao().readByCollection(collection));
	}
}
