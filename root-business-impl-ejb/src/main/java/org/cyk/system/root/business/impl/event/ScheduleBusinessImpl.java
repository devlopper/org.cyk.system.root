package org.cyk.system.root.business.impl.event;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.ScheduleBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.Schedule;
import org.cyk.system.root.persistence.api.event.ScheduleDao;

public class ScheduleBusinessImpl extends AbstractTypedBusinessService<Schedule, ScheduleDao> implements ScheduleBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public ScheduleBusinessImpl(ScheduleDao dao) {
		super(dao); 
	} 
	
}
