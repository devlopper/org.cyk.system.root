package org.cyk.system.root.business.api.time;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractCollectionBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionType;

public interface IdentifiablePeriodCollectionBusiness extends AbstractCollectionBusiness<IdentifiablePeriodCollection,IdentifiablePeriod> {

	Collection<IdentifiablePeriodCollection> findByTypeByJoin(IdentifiablePeriodCollectionType type,AbstractIdentifiable join);
}
