package org.cyk.system.root.persistence.impl.information;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.model.information.IdentifiableCollection;
import org.cyk.system.root.model.information.IdentifiableCollectionItem;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionItemDao;
import org.cyk.system.root.persistence.impl.globalidentification.AbstractJoinGlobalIdentifierDaoImpl;

public class IdentifiableCollectionItemDaoImpl extends AbstractJoinGlobalIdentifierDaoImpl<IdentifiableCollectionItem,IdentifiableCollectionItem.SearchCriteria> implements IdentifiableCollectionItemDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private String readByCollections,countByCollections;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByCollections, _select().whereIdentifierIn(IdentifiableCollectionItem.FIELD_COLLECTION));
	}
	
	@Override
	public Collection<IdentifiableCollectionItem> readByCollections(Collection<IdentifiableCollection> collections) {
		return namedQuery(readByCollections).parameterIdentifiers(collections).resultMany();
	}
	
	@Override
	public Collection<IdentifiableCollectionItem> readByCollection(IdentifiableCollection collection) {
		return readByCollections(Arrays.asList(collection));
	}

	@Override
	public Long countByCollections(Collection<IdentifiableCollection> collections) {
		return countNamedQuery(countByCollections).parameterIdentifiers(collections).resultOne();
	}
	
	@Override
	public Long countByCollection(IdentifiableCollection collection) {
		return countByCollections(Arrays.asList(collection));
	}
	
}
 