package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.event.EventDao;

@Stateless
public class EventBusinessImpl extends AbstractTypedBusinessService<Event, EventDao> implements EventBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private ContactCollectionBusiness contactCollectionBusiness;

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
    public void programAlarm(Collection<Event> events) {
        // TODO Auto-generated method stub
    }
    
    @Override
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
        return event;
    }
    
    @Override
    public void create(Collection<Event> events) {
        for(Event event : events)
            //create(event);
            dao.create(event);
    }
	
    @Override
    public Event load(Long identifier) {
        Event event = super.load(identifier);
        contactCollectionBusiness.load(event.getContactCollection());
        return event;
    }
}
