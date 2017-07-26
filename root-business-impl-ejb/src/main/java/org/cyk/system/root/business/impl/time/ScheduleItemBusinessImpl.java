package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.ScheduleItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.system.root.persistence.api.time.ScheduleItemDao;

public class ScheduleItemBusinessImpl extends AbstractCollectionItemBusinessImpl<ScheduleItem, ScheduleItemDao,Schedule> implements ScheduleItemBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ScheduleItemBusinessImpl(ScheduleItemDao dao) {
		super(dao); 
	}
		
}
