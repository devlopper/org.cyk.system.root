package org.cyk.system.root.persistence.api.time;

import org.cyk.system.root.model.time.ScheduleIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.globalidentification.JoinGlobalIdentifierDao;

public interface ScheduleIdentifiableGlobalIdentifierDao extends JoinGlobalIdentifierDao<ScheduleIdentifiableGlobalIdentifier,ScheduleIdentifiableGlobalIdentifier.SearchCriteria> {
	
}
