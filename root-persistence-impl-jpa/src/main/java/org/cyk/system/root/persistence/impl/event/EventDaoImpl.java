package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.persistence.api.event.EventDao;

public class EventDaoImpl extends AbstractEventDaoImpl<Event> implements EventDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

}
 