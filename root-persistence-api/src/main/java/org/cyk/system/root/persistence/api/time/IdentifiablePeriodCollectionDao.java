package org.cyk.system.root.persistence.api.time;

import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionType;
import org.cyk.system.root.persistence.api.AbstractCollectionDao;

public interface IdentifiablePeriodCollectionDao extends AbstractCollectionDao<IdentifiablePeriodCollection,IdentifiablePeriod> {

	Collection<IdentifiablePeriodCollection> readByTypeByJoin(IdentifiablePeriodCollectionType type,AbstractIdentifiable join);
	
}
