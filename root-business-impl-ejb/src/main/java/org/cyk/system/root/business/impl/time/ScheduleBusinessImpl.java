package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.ScheduleBusiness;
import org.cyk.system.root.business.api.time.ScheduleItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionBusinessImpl;
import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.system.root.persistence.api.time.ScheduleDao;
import org.cyk.system.root.persistence.api.time.ScheduleItemDao;

public class ScheduleBusinessImpl extends AbstractCollectionBusinessImpl<Schedule,ScheduleItem, ScheduleDao,ScheduleItemDao,ScheduleItemBusiness> implements ScheduleBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public ScheduleBusinessImpl(ScheduleDao dao) {
		super(dao); 
	}
	
}
