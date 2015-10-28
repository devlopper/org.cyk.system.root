package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventMissedReasonBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.persistence.api.event.EventMissedReasonDao;

public class EventMissedReasonBusinessImpl extends AbstractEnumerationBusinessImpl<EventMissedReason, EventMissedReasonDao> implements EventMissedReasonBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public EventMissedReasonBusinessImpl(EventMissedReasonDao dao) {
		super(dao); 
	}   
	
}
