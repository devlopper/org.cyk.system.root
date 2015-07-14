package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;

import org.cyk.system.root.model.event.EventType;
import org.cyk.system.root.persistence.api.event.EventTypeDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class EventTypeDaoImpl extends AbstractEnumerationDaoImpl<EventType> implements EventTypeDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

}
 