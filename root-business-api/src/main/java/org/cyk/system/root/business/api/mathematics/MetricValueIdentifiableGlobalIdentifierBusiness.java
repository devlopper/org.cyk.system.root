package org.cyk.system.root.business.api.mathematics;

import java.util.Collection;

import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;

public interface MetricValueIdentifiableGlobalIdentifierBusiness extends JoinGlobalIdentifierBusiness<MetricValueIdentifiableGlobalIdentifier,MetricValueIdentifiableGlobalIdentifier.SearchCriteria> {

	void create(Collection<MetricCollection> metricCollections,Collection<? extends AbstractIdentifiable> identifiables);
	
}
