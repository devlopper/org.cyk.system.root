package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.ScheduleIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.time.ScheduleIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.time.ScheduleIdentifiableGlobalIdentifierDao;

public class ScheduleIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<ScheduleIdentifiableGlobalIdentifier, ScheduleIdentifiableGlobalIdentifierDao,ScheduleIdentifiableGlobalIdentifier.SearchCriteria> implements ScheduleIdentifiableGlobalIdentifierBusiness,Serializable {
	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public ScheduleIdentifiableGlobalIdentifierBusinessImpl(ScheduleIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
	
}
