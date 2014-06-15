package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.persistence.api.event.EventDao;

public class EventBusinessImpl extends AbstractTypedBusinessService<Event, EventDao> implements EventBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public EventBusinessImpl(EventDao dao) {
		super(dao); 
	} 
	
}
