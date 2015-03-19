package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.model.event.EventSearchCriteria;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.event.EventDao;
import org.cyk.system.root.persistence.api.event.EventParticipationDao;

@Stateless
public class EventBusinessImpl extends AbstractTypedBusinessService<Event, EventDao> implements EventBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private ContactCollectionBusiness contactCollectionBusiness;
	@Inject private EventParticipationDao eventParticipationDao;

	@Inject
	public EventBusinessImpl(EventDao dao) {
		super(dao); 
	}  

    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Collection<Event> findWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return dao.readWhereFromDateBetweenByStartDateByEndDate(startDate, endDate);
    }

    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return dao.countWhereFromDateBetweenByStartDateByEndDate(startDate, endDate);
    }

    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Collection<Event> findWhereFromDateGreaterThanByDate(Date date) {
        return dao.readWhereFromDateGreaterThanByDate(date);
    }

    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Long countWhereFromDateGreaterThanByDate(Date date) {
        return dao.countWhereFromDateGreaterThanByDate(date);
    }

    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Long findDuration(Collection<Event> events) {
    	Collection<Period> periods = new ArrayList<>();
    	for(Event event : events)
    		periods.add(event.getPeriod());
    	return timeBusiness.findDuration(periods);
    }
    
    @Override
    public Event create(Event event) {
    	contactCollectionBusiness.create(event.getContactCollection());
        super.create(event);
        for(EventParticipation eventParticipation : event.getEventParticipations())
        	eventParticipationDao.create(eventParticipation);
        return event;
    }
    
    @Override
    public void create(Collection<Event> events) {
        for(Event event : events)
            //create(event);
            dao.create(event);
    }
	
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Event load(Long identifier) {
        Event event = super.load(identifier);
        contactCollectionBusiness.load(event.getContactCollection());
        return event;
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<Event> findByCriteria(EventSearchCriteria criteria) {
		//Collection<Sale> sales = null;
		/*if(criteria.getFromDateSearchCriteria().getValue()==null || criteria.getToDateSearchCriteria().getValue()==null)
			sales = findAll();
		*/
		
		//criteriaDefaultValues(criteria);
		return dao.readByCriteria(criteria);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(EventSearchCriteria criteria) {
		/*
		if(criteria.getFromDateSearchCriteria().getValue()==null || criteria.getToDateSearchCriteria().getValue()==null)
    		return countAll();
    		*/
		//criteriaDefaultValues(criteria);
		return dao.countByCriteria(criteria);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<Event> findToAlarm() {
		Date now = universalTimeCoordinated();
		Collection<Event> events = dao.readWhereDateBetweenAlarmPeriod(now);
		return events;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<Event> findWhereAlarmFromDateBetween(Period period) {
		return dao.readWhereAlarmFromDateBetween(period);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countWhereAlarmFromDateBetween(Period period) {
		return dao.countWhereAlarmFromDateBetween(period);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<Event> findWhereDateBetweenAlarmPeriod(Date date) {
		return dao.readWhereDateBetweenAlarmPeriod(date);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countWhereDateBetweenAlarmPeriod(Date date){
		return dao.countWhereDateBetweenAlarmPeriod(date);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public void load(Event event) {
		event.setEventParticipations(eventParticipationDao.readByEvents(Arrays.asList(event)));
	}
}
