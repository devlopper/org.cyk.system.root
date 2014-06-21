package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;

import org.cyk.system.root.model.event.Schedule;
import org.cyk.system.root.persistence.api.event.ScheduleDao;

public class ScheduleDaoImpl extends AbstractEventDaoImpl<Schedule> implements ScheduleDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

}
 