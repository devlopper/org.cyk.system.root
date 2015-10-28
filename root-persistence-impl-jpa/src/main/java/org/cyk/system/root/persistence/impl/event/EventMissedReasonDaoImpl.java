package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;

import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.persistence.api.event.EventMissedReasonDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public class EventMissedReasonDaoImpl extends AbstractEnumerationDaoImpl<EventMissedReason> implements EventMissedReasonDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

}
 