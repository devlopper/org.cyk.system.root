package org.cyk.system.root.business.api.time;

import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.utility.common.helper.EventHelper;

public interface ScheduleItemBusiness extends AbstractCollectionItemBusiness<ScheduleItem,Schedule> {
    
	Collection<EventHelper.Event> findEvents(Date from,Date to);
     
}
