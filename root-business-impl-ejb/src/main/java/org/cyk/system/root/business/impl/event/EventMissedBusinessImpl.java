package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventMissedBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventCollection;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.persistence.api.event.EventMissedDao;


public class EventMissedBusinessImpl extends AbstractTypedBusinessService<EventMissed, EventMissedDao> implements EventMissedBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public EventMissedBusinessImpl(EventMissedDao dao) {
		super(dao); 
	}

	@Override
	public Long findDuration(Collection<EventCollection> collections,Date fromDate, Date toDate, Boolean acceptable) {
		return dao.sumDuration(collections, fromDate, toDate, acceptable);
	}

	@Override
	public Long findDuration(Collection<Event> events, Boolean acceptable) {
		return dao.sumDuration(events, acceptable);
	}  

}
