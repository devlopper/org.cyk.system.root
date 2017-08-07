package org.cyk.system.root.business.impl.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.time.ScheduleItemBusiness;
import org.cyk.utility.common.helper.EventHelper.Event;

public class EventHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	/**/
	
	public static class Get extends org.cyk.utility.common.helper.EventHelper.Event.Get.Datasource.Adapter.Default implements Serializable{
		private static final long serialVersionUID = 1L;
		
		@Override
		protected Collection<Event> __execute__(Date from, Date to) {
			Collection<org.cyk.utility.common.helper.EventHelper.Event> events = new ArrayList<>();
			events.addAll(inject(EventBusiness.class).findEvents(from, to));
			events.addAll(inject(ScheduleItemBusiness.class).findEvents(from, to));
			return events;
		}
		
	}
	
}
