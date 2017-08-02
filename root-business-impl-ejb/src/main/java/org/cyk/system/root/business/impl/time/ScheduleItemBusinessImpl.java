package org.cyk.system.root.business.impl.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.business.api.time.ScheduleItemBusiness;
import org.cyk.system.root.business.impl.AbstractCollectionItemBusinessImpl;
import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.system.root.persistence.api.time.ScheduleItemDao;
import org.cyk.utility.common.helper.EventHelper;
import org.cyk.utility.common.helper.TimeHelper;

public class ScheduleItemBusinessImpl extends AbstractCollectionItemBusinessImpl<ScheduleItem, ScheduleItemDao,Schedule> implements ScheduleItemBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public ScheduleItemBusinessImpl(ScheduleItemDao dao) {
		super(dao); 
	}

	@Override
	public Collection<EventHelper.Event> findEvents(Date from, Date to) {
		Collection<ScheduleItem> scheduleItems = inject(ScheduleItemBusiness.class).findAll();
		Collection<EventHelper.Event> events = new ArrayList<>();
		for(ScheduleItem scheduleItem : scheduleItems){
			events.addAll(new EventHelper.Event.Builder.Interval.Adapter.Default()
				.setProperty(EventHelper.Event.Builder.PROPERTY_NAME, "Mon évènement")
				.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_INSTANT_INTERVAL, new TimeHelper.Instant.Interval(scheduleItem.getInstantInterval().getFrom().getTimeHelperInstant()
						, scheduleItem.getInstantInterval().getTo().getTimeHelperInstant(), scheduleItem.getInstantInterval().getDistanceInMillisecond()
						, scheduleItem.getInstantInterval().getPortionInMillisecond()))
				.execute());
		}
		return events;
	}
		
}
