package org.cyk.system.root.business.impl.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.value.ValueBusiness.Derive;
import org.cyk.system.root.business.api.value.ValueCollectionBusiness;
import org.cyk.system.root.business.api.value.ValueCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;
import org.cyk.system.root.persistence.api.value.ValueCollectionDao;
import org.cyk.system.root.persistence.api.value.ValueCollectionItemDao;

public class ValueCollectionBusinessImpl extends AbstractCollectionBusinessImpl<ValueCollection,ValueCollectionItem, ValueCollectionDao,ValueCollectionItemDao,ValueCollectionItemBusiness> implements ValueCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ValueCollectionBusinessImpl(ValueCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	public void derive(Collection<ValueCollection> valueCollections,Derive listener) {
		for(ValueCollection valueCollection : valueCollections)
			derive(valueCollection,listener);
	}

	@Override
	public void derive(ValueCollection valueCollection,Derive listener) {
		inject(ValueCollectionItemBusiness.class).derive(valueCollection.getItems().getCollection(),listener);
	}

	@Override
	public Collection<ValueCollection> deriveByCodes(Collection<String> valueCollectionCodes,Derive listener) {
		Collection<ValueCollection> valueCollections = new ArrayList<>();
		for(String valueCollectionCode : valueCollectionCodes)
			valueCollections.add(deriveByCode(valueCollectionCode,listener));
		return valueCollections;
	}

	@Override
	public ValueCollection deriveByCode(String valueCollectionCode,Derive listener) {
		ValueCollection valueCollection = dao.read(valueCollectionCode);
		valueCollection.getItems().setCollection(inject(ValueCollectionItemDao.class).readByCollection(valueCollection));
		derive(valueCollection,listener);
		return valueCollection;
	}
	
	public static class BuilderOneDimensionArray extends AbstractCollectionBusinessImpl.BuilderOneDimensionArray<ValueCollection> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(ValueCollection.class);
		}
		
	}
	
}
