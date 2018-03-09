package org.cyk.system.root.business.api.time;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;

public interface IdentifiablePeriodBusiness extends AbstractCollectionItemBusiness<IdentifiablePeriod,IdentifiablePeriodCollection> {
	
	IdentifiablePeriod findFirstNotClosedOrInstanciateOneByIdentifiablePeriodCollection(IdentifiablePeriodCollection collection);
	
}
