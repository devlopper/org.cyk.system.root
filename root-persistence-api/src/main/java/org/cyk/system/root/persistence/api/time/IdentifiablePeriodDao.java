package org.cyk.system.root.persistence.api.time;

import java.util.Collection;

import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;

public interface IdentifiablePeriodDao extends AbstractCollectionItemDao<IdentifiablePeriod,IdentifiablePeriodCollection> {
 
	Collection<IdentifiablePeriod> readByCollectionByClosed(IdentifiablePeriodCollection collection,Boolean closed);
	Long countByCollectionByClosed(IdentifiablePeriodCollection collection,Boolean closed);
}
