package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;
import org.cyk.utility.common.helper.ClassHelper;

public abstract class AbstractCollectionItemBusinessImpl<ITEM extends AbstractCollectionItem<COLLECTION>,DAO extends AbstractCollectionItemDao<ITEM,COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationBusinessImpl<ITEM, DAO> implements AbstractCollectionItemBusiness<ITEM,COLLECTION>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	protected Class<COLLECTION> collectionClass;
	
	public AbstractCollectionItemBusinessImpl(DAO dao) {
		super(dao); 
	}   
	
	@SuppressWarnings("unchecked")
	public Class<COLLECTION> getCollectionClass(){
		if(collectionClass==null)
			collectionClass = (Class<COLLECTION>) ClassHelper.getInstance().getParameterAt(getClass(), 2,AbstractCollection.class);
		return collectionClass;
	}
	
	@Override
	protected void setAutoSettedProperties(ITEM item, Crud crud) {
		super.setAutoSettedProperties(item, crud);
		item.setCode(StringUtils.defaultIfBlank(item.getCode(), /*RandomStringUtils.randomAlphanumeric(5)*/ RootConstant.Code.generateFromString(item.getName())));
		if(item.getCollection()!=null && StringUtils.isNotBlank(item.getCollection().getCode()) && StringUtils.isNotBlank(item.getCollection().getItemCodeSeparator()) 
				&& !StringUtils.contains(item.getCode(), item.getCollection().getItemCodeSeparator()))
			item.setCode(RootConstant.Code.generate(item));
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
	
	@SuppressWarnings("unchecked")
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ITEM instanciateOne(COLLECTION collection,String code,String name,Boolean addable) {
		ITEM item = instanciateOne(code,name);
		item.setCascadeOperationToMaster(Boolean.TRUE);
		if(Boolean.TRUE.equals(addable))
			((AbstractCollectionBusiness<COLLECTION, ITEM>)inject(BusinessInterfaceLocator.class).injectTyped(getCollectionClass())).add(collection, item);
		return item;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ITEM instanciateOne(COLLECTION collection,Boolean addable) {
		return instanciateOne(collection,null,null,addable);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ITEM instanciateOne(COLLECTION collection,String code,String name) {
		return instanciateOne(collection,code,name,Boolean.TRUE);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ITEM instanciateOne(COLLECTION collection) {
		return instanciateOne(collection,Boolean.TRUE);
	}

	@Override
	public ITEM find(String collectionCode, String relativeCode) {
		COLLECTION collection = inject(BusinessInterfaceLocator.class).injectTyped(getCollectionClass()).find(collectionCode);
		return dao.read(RootConstant.Code.generate(collection, relativeCode));
	}
	
	@Override
	protected ITEM __instanciateOne__(String[] values, InstanciateOneListener<ITEM> listener) {
		ITEM item = super.__instanciateOne__(values, listener);
		set(listener.getSetListener().setIndex(10).setFieldType(getCollectionClass()), AbstractCollectionItem.FIELD_COLLECTION);
		return item;
	}
	
	/**/

	public static class BuilderOneDimensionArray<T extends AbstractCollectionItem<?>> extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<T> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray(Class<T> outputClass) {
			super(outputClass);
			addParameterArrayElementString(10,AbstractCollectionItem.FIELD_COLLECTION);
		}
		
		@Override
		protected T __execute__() {
			T item = super.__execute__();
			if(item.getCollection()!=null)
				item.setCode(RootConstant.Code.generate(item));
			return item;
		}
		
		/**/
		
		public static class KeyBuilder extends org.cyk.system.root.business.impl.helper.ArrayHelper.KeyBuilder implements Serializable {
			private static final long serialVersionUID = 1L;
	    	/*
			@Override
			protected org.cyk.utility.common.helper.ArrayHelper.Dimension.Key __execute__() {
				return new org.cyk.utility.common.helper.ArrayHelper.Dimension.Key(RootConstant.Code.generate(getInput()[0]));
			}*/

	    }
		
	}
	
}
