package org.cyk.system.root.persistence.impl.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventCollection;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.model.event.EventSearchCriteria;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.event.EventDao;
import org.cyk.system.root.persistence.api.event.EventMissedDao;
import org.cyk.utility.common.computation.Function;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class EventPersistenceIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5955832118708678179L;

	@Deployment
	public static Archive<?> createDeployment() {
	    return createRootDeployment();
	} 
	
	@Inject private EventDao eventDao;
	@Inject private EventMissedDao eventMissedDao,eventMissedDao1,eventMissedDao2,eventMissedDao3;
	private Event event,e1,e2,e3,e4;
	private EventCollection eventCollection1,eventCollection2;
	private Date now = new Date(),oneHourLater,oneHourPast;
	private EventMissedReason eventMissedReason1,eventMissedReason2;
		
	@Override
	protected void populate() {
		
	    oneHourLater = DateUtils.addHours(now, 1);
	    oneHourPast = DateUtils.addHours(now, -1);
		event(null,oneHourLater, DateUtils.addMinutes(oneHourLater, 5));
		event(null,DateUtils.addMinutes(oneHourLater, 10), DateUtils.addMinutes(oneHourLater, 13));
		event(null,now, DateUtils.addMinutes(now, 3));
	
		event(null,oneHourPast, DateUtils.addMinutes(oneHourPast, 7));
		
		eventMissedReason1 = new EventMissedReason("MALADIE", "Maladie", null);
		create(eventMissedReason1);
		eventMissedReason1.setAcceptable(Boolean.TRUE);
		eventMissedReason2 = new EventMissedReason("RETARD", "Retard", null);
		eventMissedReason2.setAcceptable(Boolean.FALSE);
		create(eventMissedReason2);
		
		eventCollection1 = new EventCollection();
		create(eventCollection1);
		e1 = event(eventCollection1, now, oneHourLater);
		e2 = event(eventCollection1, oneHourLater, DateUtils.addHours(now, 3));
		
		eventMissed(e1, DateUtils.MILLIS_PER_MINUTE*15, eventMissedReason2);
		eventMissed(e1, DateUtils.MILLIS_PER_MINUTE*60, eventMissedReason1);
		eventMissed(e2, DateUtils.MILLIS_PER_MINUTE*60, eventMissedReason2);
		
		eventCollection2 = new EventCollection();
		create(eventCollection2);
		e3 = event(eventCollection2, now, oneHourLater);
		e4 = event(eventCollection2, oneHourLater, DateUtils.addHours(now, 3));
		
		eventMissed(e3, DateUtils.MILLIS_PER_MINUTE*25, eventMissedReason2);
		eventMissed(e4, DateUtils.MILLIS_PER_MINUTE*120, eventMissedReason1);
		eventMissed(e4, DateUtils.MILLIS_PER_MINUTE*60, eventMissedReason2);
	}
	
	private Event event(EventCollection collection,Date fromDate,Date toDate){
	    Event event = new Event();
	    event.setCollection(collection);
	    event.setContactCollection(null);
	    event.setPeriod(new Period(fromDate, toDate));
	    event.getAlarm().getPeriod().setFromDate(DateUtils.addMinutes(fromDate, -5));
	    create(event);
	    return event;
	}
	
	private EventMissed eventMissed(Event event,Long durationInMillisecond,EventMissedReason reason){
		EventMissed eventMissed = new EventMissed();
		eventMissed.setParticipation(new EventParticipation(null,event));
		create(eventMissed.getParticipation());
		eventMissed.setDuration(durationInMillisecond);
		eventMissed.setReason(reason);
		create(eventMissed);
		return eventMissed;
	}
					
	// CRUD 
	
	@Override
	protected void create() {
	    eventDao.create(event = event(null,now, DateUtils.addMinutes(now, 3)));
	    Assert.assertNotNull(event.getIdentifier());
	}

	@Override
	protected void read() {
	    Assert.assertNotNull(eventDao.read(event.getIdentifier()));
	}

	@Override
	protected void update() {
	    Date newDate = new Date();
	    event.getPeriod().setFromDate(newDate);
	    eventDao.update(event);
	    Assert.assertEquals(newDate, eventDao.read(event.getIdentifier()).getPeriod().getFromDate());
	}

	@Override
	protected void delete() {
	    eventDao.delete(event);
	    Assert.assertNull(eventDao.read(event.getIdentifier()));
	}
	
	@Override
	protected void queries() {
	    
		Assert.assertEquals(8,eventDao.select().all().size());
		Assert.assertEquals(8,eventDao.select(Function.COUNT).oneLong().intValue());
		
		Assert.assertEquals(8,eventDao.readAll().size());
		Assert.assertEquals(8,eventDao.countAll().intValue());
		
		Assert.assertEquals(4,eventDao.readWhereFromDateGreaterThanByDate(now).size());
		Assert.assertEquals(4,eventDao.countWhereFromDateGreaterThanByDate(now).intValue());
		
		Assert.assertEquals(8, eventDao.countWhereFromDateBetweenByStartDateByEndDate(DateUtils.addHours(now, -2), DateUtils.addHours(now, 2)).intValue());
		Assert.assertEquals(7, eventDao.countWhereFromDateBetweenByStartDateByEndDate(DateUtils.addHours(now, 0), DateUtils.addHours(now, 2)).intValue());
		Assert.assertEquals(1, eventDao.countWhereFromDateBetweenByStartDateByEndDate(DateUtils.addMinutes(oneHourLater, 6), DateUtils.addHours(now, 2)).intValue());
		
		Assert.assertEquals(8, eventDao.countByCriteria(new EventSearchCriteria(DateUtils.addHours(now, -2), DateUtils.addHours(now, 2))).intValue());
		Assert.assertEquals(7, eventDao.countByCriteria(new EventSearchCriteria(DateUtils.addHours(now, 0), DateUtils.addHours(now, 2))).intValue());
		Assert.assertEquals(1, eventDao.countByCriteria(new EventSearchCriteria(DateUtils.addMinutes(oneHourLater, 6), DateUtils.addHours(now, 2))).intValue());
		
		Assert.assertEquals(8, eventDao.countWhereAlarmFromDateBetween(new Period(DateUtils.addHours(now, -2), DateUtils.addHours(now, 2))).intValue());
		Assert.assertEquals(4, eventDao.countWhereAlarmFromDateBetween(new Period(DateUtils.addHours(now, -2), now)).intValue());
		
		Assert.assertEquals(4, eventDao.countWhereAlarmFromDateBetween(new Period(DateUtils.addHours(now, -2), now)).intValue());
		
		//Assert.assertEquals(4, eventDao.countWhereDateBetweenAlarmPeriod(now).intValue());
		
		Date toDate = DateUtils.addHours(now, 4);
		
		Assert.assertEquals(135l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(eventCollection1),now,toDate, null).longValue());
		Assert.assertEquals(205l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(eventCollection2),now,toDate, null).longValue());
		Assert.assertEquals(340l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(eventCollection1,eventCollection2),now,toDate, null).longValue());
		
		Assert.assertEquals(75l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(eventCollection1),now,toDate, Boolean.FALSE).longValue());
		Assert.assertEquals(85l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(eventCollection2),now,toDate, Boolean.FALSE).longValue());
		Assert.assertEquals(160l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(eventCollection1,eventCollection2),now,toDate, Boolean.FALSE).longValue());
		
		Assert.assertEquals(60l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(eventCollection1),now,toDate, Boolean.TRUE).longValue());
		Assert.assertEquals(120l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(eventCollection2),now,toDate, Boolean.TRUE).longValue());
		Assert.assertEquals(180l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(eventCollection1,eventCollection2),now,toDate, Boolean.TRUE).longValue());
		
		toDate = DateUtils.addMinutes(now, 30);
		Assert.assertEquals(60l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(eventCollection1),now,toDate, Boolean.TRUE).longValue());
		Assert.assertEquals(0l,eventMissedDao.sumDuration(Arrays.asList(eventCollection2),now,toDate, Boolean.TRUE).longValue());
		Assert.assertEquals(60l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(eventCollection1,eventCollection2),now,toDate, Boolean.TRUE).longValue());
		
		now = DateUtils.addHours(now, -14);
		toDate = DateUtils.addHours(now, -10);
		Assert.assertEquals(0l,eventMissedDao1.sumDuration(Arrays.asList(eventCollection1),now,toDate, Boolean.TRUE).longValue());
		Assert.assertEquals(0l,eventMissedDao2.sumDuration(Arrays.asList(eventCollection2),now,toDate, Boolean.TRUE).longValue());
		Assert.assertEquals(0l,eventMissedDao3.sumDuration(Arrays.asList(eventCollection1,eventCollection2),now,toDate, Boolean.TRUE).longValue());
		
		Assert.assertEquals(75l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1), null).longValue());
		Assert.assertEquals(135l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1,e2), null).longValue());
		Assert.assertEquals(160l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1,e2,e3), null).longValue());
		Assert.assertEquals(340l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1,e2,e3,e4), null).longValue());
		
		Assert.assertEquals(15l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1), Boolean.FALSE).longValue());
		Assert.assertEquals(75l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1,e2), Boolean.FALSE).longValue());
		Assert.assertEquals(100l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1,e2,e3), Boolean.FALSE).longValue());
		Assert.assertEquals(160l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1,e2,e3,e4), Boolean.FALSE).longValue());
		
		Assert.assertEquals(60l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1), Boolean.TRUE).longValue());
		Assert.assertEquals(60l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1,e2), Boolean.TRUE).longValue());
		Assert.assertEquals(60l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1,e2,e3), Boolean.TRUE).longValue());
		Assert.assertEquals(180l * DateUtils.MILLIS_PER_MINUTE, eventMissedDao.sumDuration(Arrays.asList(e1,e2,e3,e4), Boolean.TRUE).longValue());
		
		Assert.assertEquals(0l, eventMissedDao.sumDuration(new ArrayList<Event>(), Boolean.TRUE).longValue());
	}
	
}
