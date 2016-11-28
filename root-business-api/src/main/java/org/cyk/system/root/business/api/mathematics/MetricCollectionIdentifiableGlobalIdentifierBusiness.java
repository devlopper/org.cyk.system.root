package org.cyk.system.root.business.api.mathematics;

import java.util.Collection;

import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;

public interface MetricCollectionIdentifiableGlobalIdentifierBusiness extends JoinGlobalIdentifierBusiness<MetricCollectionIdentifiableGlobalIdentifier,MetricCollectionIdentifiableGlobalIdentifier.SearchCriteria> {
	
	void create(Collection<MetricCollection> metricCollections,Collection<? extends AbstractIdentifiable> identifiables);
	void create(Collection<MetricCollection> metricCollections,AbstractIdentifiable identifiable);
	void create(MetricCollection metricCollection,Collection<? extends AbstractIdentifiable> identifiables);
	void create(MetricCollection metricCollection,AbstractIdentifiable identifiable);
	
}
