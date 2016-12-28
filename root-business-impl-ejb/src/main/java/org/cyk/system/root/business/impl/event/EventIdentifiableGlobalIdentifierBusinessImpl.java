package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.globalidentification.AbstractJoinGlobalIdentifierBusinessImpl;
import org.cyk.system.root.model.event.EventIdentifiableGlobalIdentifier;
import org.cyk.system.root.persistence.api.event.EventIdentifiableGlobalIdentifierDao;

public class EventIdentifiableGlobalIdentifierBusinessImpl extends AbstractJoinGlobalIdentifierBusinessImpl<EventIdentifiableGlobalIdentifier, EventIdentifiableGlobalIdentifierDao,EventIdentifiableGlobalIdentifier.SearchCriteria> implements EventIdentifiableGlobalIdentifierBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public EventIdentifiableGlobalIdentifierBusinessImpl(EventIdentifiableGlobalIdentifierDao dao) {
		super(dao); 
	}
	
}
