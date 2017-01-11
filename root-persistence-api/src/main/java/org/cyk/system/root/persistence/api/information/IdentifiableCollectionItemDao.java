package org.cyk.system.root.persistence.api.information;

import java.util.Collection;

import org.cyk.system.root.model.information.IdentifiableCollection;
import org.cyk.system.root.model.information.IdentifiableCollectionItem;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface IdentifiableCollectionItemDao extends JoinGlobalIdentifierDao<IdentifiableCollectionItem,IdentifiableCollectionItem.SearchCriteria> {

	Collection<IdentifiableCollectionItem> readByCollections(Collection<IdentifiableCollection> collections);
	Collection<IdentifiableCollectionItem> readByCollection(IdentifiableCollection collection);
	
	Long countByCollections(Collection<IdentifiableCollection> collections);
	Long countByCollection(IdentifiableCollection collection);
}
