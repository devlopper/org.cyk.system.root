package org.cyk.system.root.persistence.api.mathematics;

import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface MetricValueIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<MetricValueIdentifiableGlobalIdentifier,MetricValueIdentifiableGlobalIdentifier.SearchCriteria> {
	
}
