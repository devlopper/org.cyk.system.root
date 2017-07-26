package org.cyk.system.root.business.impl.time;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractCollectionDetails;
import org.cyk.system.root.model.time.Schedule;

public class ScheduleDetails extends AbstractCollectionDetails.Extends<Schedule> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	
	public ScheduleDetails(Schedule schedule) {
		super(schedule);
		
	}
	
	
}