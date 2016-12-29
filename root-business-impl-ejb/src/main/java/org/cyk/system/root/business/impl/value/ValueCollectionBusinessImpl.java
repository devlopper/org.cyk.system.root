package org.cyk.system.root.business.impl.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.business.api.value.ValueCollectionBusiness;
import org.cyk.system.root.business.api.value.ValueCollectionItemBusiness;
import org.cyk.system.root.business.api.value.ValueBusiness.DeriveArguments;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;
import org.cyk.system.root.persistence.api.value.ValueCollectionDao;
import org.cyk.system.root.persistence.api.value.ValueCollectionItemDao;

public class ValueCollectionBusinessImpl extends AbstractCollectionBusinessImpl<ValueCollection,ValueCollectionItem, ValueCollectionDao,ValueCollectionItemDao,ValueCollectionItemBusiness> implements ValueCollectionBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private ValueCollectionItemDao valueCollectionItemDao;
	
	@Inject
	public ValueCollectionBusinessImpl(ValueCollectionDao dao) {
		super(dao); 
	}
	
	@Override
	protected ValueCollectionItemDao getItemDao() {
		return valueCollectionItemDao;
	}
	@Override
	protected ValueCollectionItemBusiness getItemBusiness() {
		return inject(ValueCollectionItemBusiness.class);
	}

	@Override
	public void derive(Collection<ValueCollection> valueCollections,DeriveArguments arguments) {
		for(ValueCollection valueCollection : valueCollections)
			derive(valueCollection,arguments);
	}

	@Override
	public void derive(ValueCollection valueCollection,DeriveArguments arguments) {
		inject(ValueCollectionItemBusiness.class).derive(valueCollection.getCollection(),arguments);
	}

	@Override
	public Collection<ValueCollection> deriveByCodes(Collection<String> valueCollectionCodes,DeriveArguments arguments) {
		Collection<ValueCollection> valueCollections = new ArrayList<>();
		for(String valueCollectionCode : valueCollectionCodes)
			valueCollections.add(deriveByCode(valueCollectionCode,arguments));
		return valueCollections;
	}

	@Override
	public ValueCollection deriveByCode(String valueCollectionCode,DeriveArguments arguments) {
		ValueCollection valueCollection = dao.read(valueCollectionCode);
		valueCollection.setCollection(inject(ValueCollectionItemDao.class).readByCollection(valueCollection));
		derive(valueCollection,arguments);
		return valueCollection;
	}
	
}
