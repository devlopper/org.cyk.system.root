package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventTypeBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.model.event.EventType;
import org.cyk.system.root.persistence.api.event.EventTypeDao;

public class EventTypeBusinessImpl extends AbstractEnumerationBusinessImpl<EventType, EventTypeDao> implements EventTypeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public EventTypeBusinessImpl(EventTypeDao dao) {
		super(dao); 
	}   
	
}
