package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.FilterHelper.Filter;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;

public abstract class AbstractCollectionItemDaoImpl<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractEnumerationDaoImpl<ITEM> implements AbstractCollectionItemDao<ITEM,COLLECTION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	@SuppressWarnings("rawtypes")
	protected Class<AbstractCollection> collectionClass;
	private String /*readByCollectionAscending,readByCollectionDescending,*/readByCollectionsAscending,readByCollectionsDescending;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void beforeInitialisation() {
		collectionClass = (Class<AbstractCollection>) __getCollectionClass__();
		//(Class<IDENTIFIABLE>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		super.beforeInitialisation();
	}
	
	protected Class<?> __getCollectionClass__(){
		return ClassHelper.getInstance().getParameterAt(getClass(), 1, AbstractCollection.class);
	}
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		//registerNamedQuery(readByCollectionAscending, _select().where(AbstractCollectionItem.FIELD_COLLECTION).orderBy(getReadByCollectionOrderByFieldName(), Boolean.TRUE));
		//registerNamedQuery(readByCollectionDescending, _select().where(AbstractCollectionItem.FIELD_COLLECTION).orderBy(getReadByCollectionOrderByFieldName(), Boolean.FALSE));
		registerNamedQuery(readByCollectionsAscending, _select().whereIdentifierIn(AbstractCollectionItem.FIELD_COLLECTION).orderBy(getReadByCollectionOrderByFieldName(), Boolean.TRUE));
		registerNamedQuery(readByCollectionsDescending, _select().whereIdentifierIn(AbstractCollectionItem.FIELD_COLLECTION).orderBy(getReadByCollectionOrderByFieldName(), Boolean.FALSE));
	}
	
	@Override
	public Collection<ITEM> readByCollections(Collection<COLLECTION> collections, Boolean ascending) {
		String queryName;
		if(Boolean.TRUE.equals(ascending))
			queryName = readByCollectionsAscending;
		else
			queryName = readByCollectionsDescending;
		return namedQuery(queryName).parameterIdentifiers(collections).resultMany();
	}
	
	@Override
	public Collection<ITEM> readByCollections(Collection<COLLECTION> collections) {
		return readByCollections(collections, Boolean.TRUE);
	}
	
	@Override
	public Collection<ITEM> readByCollection(COLLECTION collection, Boolean ascending) {
		return readByCollections(Arrays.asList(collection),ascending);
	}
	
	@Override
	public Collection<ITEM> readByCollection(COLLECTION collection) {
		return readByCollections(Arrays.asList(collection));
	}
	
	protected String getReadByCollectionOrderByFieldName(){
		return AbstractIdentifiable.FIELD_IDENTIFIER;
	}
	
	@Override
	protected void listenInstanciateJpqlBuilder(String name, JavaPersistenceQueryLanguage builder) {
		super.listenInstanciateJpqlBuilder(name, builder);
		if(readByFilter.equals(name)){
			builder.setFieldName(AbstractCollectionItem.FIELD_COLLECTION).where().and().in(AbstractIdentifiable.FIELD_IDENTIFIER);
		}else if(readWhereExistencePeriodFromDateIsLessThan.equals(name) || countWhereExistencePeriodFromDateIsLessThan.equals(name)){
			builder.where().and().eq(AbstractCollectionItem.FIELD_COLLECTION);
		}
	}
			
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> void processQueryWrapper(Class<T> aClass,QueryWrapper<T> queryWrapper, String queryName,Object[] arguments) {
		super.processQueryWrapper(aClass, queryWrapper, queryName,arguments);
		if(ArrayUtils.contains(new String[]{readByFilter,countByFilter}, queryName)){
			FilterHelper.Filter<T> filter = (Filter<T>) arguments[0];
			queryWrapper.parameterInIdentifiers(filter.filterMasters(collectionClass),AbstractCollectionItem.FIELD_COLLECTION,AbstractIdentifiable.FIELD_IDENTIFIER); 
		}else if(readWhereExistencePeriodFromDateIsLessThan.equals(queryName) || countWhereExistencePeriodFromDateIsLessThan.equals(queryName)){
			AbstractCollectionItem<?> item = (AbstractCollectionItem<?>) arguments[0];
			queryWrapper.parameter(AbstractCollectionItem.FIELD_COLLECTION, item.getCollection());
		} 
	}
	
	/*
	public ITEM readByCollectionByRelativeCode(COLLECTION collection, String relativeCode) {
		return namedQuery(readByCollectionByRelativeCode).parameter(AbstractCollectionItem.FIELD_COLLECTION, collection)
				.parameter(GlobalIdentifier.FIELD_CODE, relativeCode)
				.resultOne();
	}*/
}
 