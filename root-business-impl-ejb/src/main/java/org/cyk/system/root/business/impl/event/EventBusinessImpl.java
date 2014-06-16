package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.ScheduleEvent;
import org.cyk.system.root.persistence.api.event.EventDao;

public class EventBusinessImpl extends AbstractTypedBusinessService<Event, EventDao> implements EventBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	@Inject
	public EventBusinessImpl(EventDao dao) {
		super(dao); 
	}

    @Override
    public Collection<Event> findWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return dao.readWhereFromDateBetweenByStartDateByEndDate(startDate, endDate);
    }

    @Override
    public Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return dao.countWhereFromDateBetweenByStartDateByEndDate(startDate, endDate);
    }

    @Override
    public Collection<Event> findWhereFromDateGreaterThanByDate(Date date) {
        return dao.readWhereFromDateGreaterThanByDate(date);
    }

    @Override
    public Long countWhereFromDateGreaterThanByDate(Date date) {
        return dao.countWhereFromDateGreaterThanByDate(date);
    }

    @Override
    public void programAlarm(Collection<Event> events) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Collection<ScheduleEvent> process(Event event) {
        if(event.getSchedule().getDay()==null)
            return Arrays.asList(new ScheduleEvent(event, event.getSchedule().getPeriod()));
        Collection<ScheduleEvent> collection = new ArrayList<>();
        
        return collection;
    }

    
	
}
