package org.cyk.system.root.business.impl;

import java.io.Serializable;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.persistence.api.AbstractCollectionDao;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public abstract class AbstractCollectionBusinessImpl<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>,DAO extends AbstractCollectionDao<COLLECTION, ITEM>,ITEM_DAO extends AbstractCollectionItemDao<ITEM,COLLECTION>> extends AbstractEnumerationBusinessImpl<COLLECTION, DAO> implements AbstractCollectionBusiness<COLLECTION,ITEM>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractCollectionBusinessImpl(DAO dao) {
		super(dao); 
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
	
	protected abstract ITEM_DAO getItemDao();
	
	protected ITEM createItem(ITEM item){
		return getItemDao().create(item);
	}
	
	protected void __load__(COLLECTION collection) {
		collection.setCollection(getItemDao().readByCollection(collection));
	}
}
