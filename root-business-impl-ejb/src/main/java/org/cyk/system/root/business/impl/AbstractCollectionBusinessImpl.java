package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.persistence.api.AbstractCollectionDao;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractCollectionBusinessImpl<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>,DAO extends AbstractCollectionDao<COLLECTION, ITEM>,ITEM_DAO extends AbstractCollectionItemDao<ITEM,COLLECTION>,ITEM_BUSINESS extends AbstractCollectionItemBusiness<ITEM,COLLECTION>> extends AbstractEnumerationBusinessImpl<COLLECTION, DAO> implements AbstractCollectionBusiness<COLLECTION,ITEM>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	protected Class<ITEM> itemClass;
	
	@SuppressWarnings("unchecked")
	public AbstractCollectionBusinessImpl(DAO dao) {
		super(dao);
		itemClass = (Class<ITEM>) ClassHelper.getInstance().getParameterAt(getClass(), 1,AbstractCollectionItem.class);
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
		//LogMessage.Builder logMessageBuilder = new LogMessage.Builder(Boolean.TRUE.equals(add) ? "Add":"Remove", item.getClass().getSimpleName());
		if(Boolean.TRUE.equals(add)){
			Boolean found = Boolean.FALSE;
			if(collection.getItems().getElements()!=null)
				for(ITEM index : collection.getItems().getElements())
					if(index == item){
						found = Boolean.TRUE;
						break;
					}
			//logMessageBuilder.addParameters("found",found);
			if(Boolean.FALSE.equals(found)){
				collection.add(item);
				//logMessageBuilder.addParameters("item",item);
			}
		}else{
			if(collection.getItems().getElements()!=null)
				collection.getItems().getElements().remove(item);
		}
		//logTrace(logMessageBuilder);
		return item;
	}
	
	@Override
	protected void beforeCrud(COLLECTION collection,Crud crud) {
		super.beforeCrud(collection,crud);
		/*if(collection.getItems().isSynchonizationEnabled()){
			if(ArrayUtils.contains(new Crud[]{Crud.CREATE, Crud.UPDATE,Crud.DELETE},crud)){
				computeChanges(collection);
			}	
		}*/
		
	}
	
	@Override
	protected void afterCrud(COLLECTION collection,Crud crud) {
		super.afterCrud(collection,crud);
		if(Crud.isCreateOrUpdate(crud)){
			if(collection.getItems().isSynchonizationEnabled()){
				if(Crud.UPDATE.equals(crud)){
					Collection<ITEM> database = getItemDao().readByCollection(collection);
					delete(itemClass,database, collection.getItems().getElements());
				}
				Long orderNumber = 0l;
				for(ITEM item : collection.getItems().getElements()){
					item.setCollection(collection);
					if(Boolean.TRUE.equals(collection.getItems().getIsOrderNumberComputeEnabled()))
						item.setOrderNumber(orderNumber++);
				}
				if(Crud.CREATE.equals(crud))
					getItemBusiness().create(collection.getItems().getElements());
				else if(Crud.UPDATE.equals(crud))
					getItemBusiness().save(collection.getItems().getElements()); 
			}	
		}
	}

	@Override
	protected void beforeDelete(COLLECTION collection) {
		super.beforeDelete(collection);
		Collection<ITEM> identifiables = new ArrayList<>();
		if(CollectionHelper.getInstance().isEmpty(collection.getItemsDeletable()))
			identifiables.addAll(getItemDao().readByCollection(collection));
		else	
			identifiables.addAll(collection.getItemsDeletable().getElements());		
		getItemBusiness().delete(identifiables);
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
		collection.getItems().setElements(getItemDao().readByCollection(collection));
	}
	
	public Collection<ITEM> remove(COLLECTION collection,Class<? extends ITEM> aClass){
		@SuppressWarnings("unchecked")
		Collection<ITEM> items = (Collection<ITEM>) collection.getItems().filter(aClass);
		for(ITEM item : items){
			remove(collection, item);
		}
		return items;
	}
	
	@Override
	public void removeNullItems(COLLECTION collection){
		Collection<ITEM> items = new ArrayList<>();
		Collection<ITEM> candiateItems = collection.getItems().getElements();
		for(ITEM item : candiateItems){
			if(!Boolean.TRUE.equals(isNullItem(item)))
				items.add(item);
		}
		collection.getItems().setElements(items);
	}
	
	protected Boolean isNullItem(ITEM item){
		ThrowableHelper.getInstance().throwNotYetImplemented();
		return null;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void prepare(COLLECTION collection,Crud crud){
		prepare(collection, crud, new String[]{AbstractCollection.FIELD_ITEMS});
	}
	
	@Override
	protected String getFindByMasterMethodName(Object master){
		return "findByCollection";
	}
	
	
	
	/**/
	
	@Override
	protected void computeChanges(COLLECTION identifiable,LoggingHelper.Message.Builder logMessageBuilder) {
		super.computeChanges(identifiable, logMessageBuilder);
		logMessageBuilder.addNamedParameters("#items",CollectionHelper.getInstance().getSize(identifiable.getItems().getElements())
				,"items synchronized",identifiable.getItems().isSynchonizationEnabled());
		if(Boolean.TRUE.equals(identifiable.getItems().isSynchonizationEnabled())){
			new CollectionHelper.Iterator.Adapter.Default<ITEM>(identifiable.getItems().getElements()){
				private static final long serialVersionUID = 1L;
				protected void __executeForEach__(ITEM item) {
					getItemBusiness().computeChanges(item);
				}
			}.execute();	
		}
	}



	public static class BuilderOneDimensionArray<T extends AbstractCollection<?>> extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<T> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray(Class<T> outputClass) {
			super(outputClass);
		}	
	}
	
	@Getter @Setter
	public static class Details<T extends AbstractCollection<?>> extends AbstractEnumerationBusinessImpl.Details<T> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		
		@Input @InputText private String items;
		
		public Details(T identifiable) {
			super(identifiable);
			if(identifiable==null){
				
			}else{
				items = StringUtils.join(identifiable.getItems().getElements(),Constant.CHARACTER_COMA);
			}
			
		}
		
		/**/
		public static final String FIELD_ITEMS = "items";
		
	}
	
}
