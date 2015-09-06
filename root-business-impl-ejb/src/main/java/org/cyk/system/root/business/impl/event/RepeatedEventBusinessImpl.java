package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.event.RepeatedEventBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventReminder;
import org.cyk.system.root.model.event.RepeatedEvent;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.event.EventReminderDao;
import org.cyk.system.root.persistence.api.event.RepeatedEventDao;
import org.joda.time.DateTime;

public class RepeatedEventBusinessImpl extends AbstractTypedBusinessService<RepeatedEvent, RepeatedEventDao> implements RepeatedEventBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private EventBusiness eventBusiness;
	@Inject private EventReminderDao eventReminderDao;
	
	@Inject
	public RepeatedEventBusinessImpl(RepeatedEventDao dao) {
		super(dao); 
	}

	@Override
    public RepeatedEvent createAnniversary(Integer dayOfMonth,Integer month,String object) {
    	Event event = new Event();
    	EventReminder eventReminder = new EventReminder();
    	event.setPeriod(new Period());
    	eventReminder.setPeriod(new Period());
		
    	commonEvent(event,Arrays.asList(eventReminder),dayOfMonth,month, object);
		event.setType(RootBusinessLayer.getInstance().getAnniversaryEventType());
		event.setContactCollection(null); 
		event.setOwner(null);//Nobody is owner
		eventBusiness.create(event,Arrays.asList(eventReminder));
    	
    	RepeatedEvent repeatedEvent = new RepeatedEvent();
    	repeatedEvent.setEvent(event);
    	commonRepeatedEvent(repeatedEvent, dayOfMonth, month);
    	dao.create(repeatedEvent);
		return repeatedEvent;
    }
	
	private void commonEvent(Event event,Collection<EventReminder> eventReminders,Integer dayOfMonth,Integer month,String object){
		event.setObject(anniversaryName(object));
		event.getPeriod().setFromDate(new DateTime(1900, month, dayOfMonth, 0, 0, 0, 0).toDate());
		event.getPeriod().setToDate(event.getPeriod().getFromDate());
		for(EventReminder eventReminder : eventReminders){
			eventReminder.getPeriod().setFromDate(new DateTime(event.getPeriod().getFromDate()).minusDays(1).toDate());
			eventReminder.getPeriod().setToDate(event.getPeriod().getFromDate());
		}
	}
	
	private void commonRepeatedEvent(RepeatedEvent repeatedEvent,Integer dayOfMonth,Integer month){
		repeatedEvent.getDate().setDay(dayOfMonth);
    	repeatedEvent.getDate().setMonth(month);
	} 

	@Override
	public RepeatedEvent createAnniversary(Date date, String name) {
		return createAnniversary(timeBusiness.findDayOfMonth(date), timeBusiness.findMonth(date), name);
	}

	@Override
	public RepeatedEvent updateAnniversary(RepeatedEvent anniversary,Integer dayOfMonth,Integer month,String name) {
		Collection<EventReminder> eventReminders = eventReminderDao.readByEvent(anniversary.getEvent());
		commonEvent(anniversary.getEvent(),eventReminders,dayOfMonth,month, name);
		commonRepeatedEvent(anniversary, dayOfMonth, month);
		eventBusiness.update(anniversary.getEvent());
		for(EventReminder eventReminder : eventReminders)
			eventReminderDao.update(eventReminder);
		dao.update(anniversary);
		return anniversary;
	}
	
	@Override
	public RepeatedEvent updateAnniversary(RepeatedEvent anniversary,Date date, String name) {
		return updateAnniversary(anniversary, timeBusiness.findDayOfMonth(date), timeBusiness.findMonth(date), name);
	}

	private String anniversaryName(String name){
		return RootBusinessLayer.getInstance().getAnniversaryEventType().getName()+" : "+name;
	}

	@Override
	public Collection<RepeatedEvent> findByPeriod(Period period) {
		return dao.readByMonths(timeBusiness.findMonthIndexes(period));
	}

	@Override
	public Long countByPeriod(Period period) {
		return dao.countByMonths(timeBusiness.findMonthIndexes(period));
	}

	@Override
	public Collection<RepeatedEvent> findByMonth(Integer month) {
		return dao.readByMonth(month);
	}

	@Override
	public Long countByMonth(Integer month) {
		return dao.countByMonth(month);
	}

	@Override
	public Collection<RepeatedEvent> findByDayOfMonth(Integer dayOfMonth) {
		return dao.readByDayOfMonth(dayOfMonth);
	}

	@Override
	public Long countByDayOfMonth(Integer dayOfMonth) {
		return dao.countByDayOfMonth(dayOfMonth);
	}

	@Override
	public Collection<RepeatedEvent> findByDayOfMonthByMonth(Integer dayOfMonth,Integer month) {
		return dao.readByDayOfMonthByMonth(dayOfMonth, month);
	}

	@Override
	public Long countByDayOfMonthByMonth(Integer dayOfMonth, Integer month) {
		return dao.countByDayOfMonthByMonth(dayOfMonth, month);
	}

}
