package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.model.event.EventReminder;
import org.cyk.system.root.model.event.EventSearchCriteria;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.event.EventDao;
import org.cyk.system.root.persistence.api.event.EventParticipationDao;
import org.cyk.system.root.persistence.api.event.EventReminderDao;

@Stateless
public class EventBusinessImpl extends AbstractIdentifiablePeriodBusinessImpl<Event, EventDao> implements EventBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject private ContactCollectionBusiness contactCollectionBusiness;
	@Inject private EventParticipationDao eventParticipationDao;
	@Inject private EventReminderDao eventReminderDao;
	//@Inject private RepeatedEventBusiness repeatedEventBusiness;
	
	@Inject
	public EventBusinessImpl(EventDao dao) {
		super(dao); 
	}  
    
    @Override
    public Collection<Event> findWhereFromDateBetweenPeriodByParties(Period period, Collection<Party> parties) {
    	return dao.readWhereFromDateBetweenPeriodByParties(period, parties);
    }
    @Override
    public Long countWhereFromDateBetweenPeriodByParties(Period period,Collection<Party> parties) {
    	return dao.countWhereFromDateBetweenPeriodByParties(period, parties);
    }
    
    @Override
    public Event create(Event event) {
    	if(event.getContactCollection()!=null)
    		contactCollectionBusiness.create(event.getContactCollection());
        super.create(event);
        for(EventParticipation eventParticipation : event.getEventParticipations()){
        	eventParticipation.setEvent(event);
        	eventParticipationDao.create(eventParticipation);
        }
        return event;
    }
    
    @Override
    public Event delete(Event event) {
    	if(event.getContactCollection()!=null){
    		ContactCollection contactCollection = event.getContactCollection();
    		event.setContactCollection(null);
    		event = dao.update(event);
    		contactCollectionBusiness.delete(contactCollection);
    	}
    	for(EventParticipation eventParticipation : eventParticipationDao.readByEvent(event))
        	eventParticipationDao.delete(eventParticipation);
    	for(EventReminder eventReminder : eventReminderDao.readByEvent(event))
    		eventReminderDao.delete(eventReminder);
    	return super.delete(event);
    }

    @Override
    public void create(Event event, Collection<EventReminder> eventReminders) {
    	create(event);
    	for(EventReminder eventReminder : eventReminders){
    		eventReminder.setEvent(event);
    		//TODO check disjoin
    		eventReminderDao.create(eventReminder);
    	}
    }
    /*
    @Override
    public Event update(Event event) {
    	
    	return super.update(event);
    }
    */
	
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
	
	protected void __load__(Event event) {
		event.setEventParticipations(eventParticipationDao.readByEvents(Arrays.asList(event)));
	}

	@Override
	public Collection<Event> findPasts(Collection<Party> parties) {
		return dao.readWhereToDateLessThanByDateByParties(universalTimeCoordinated(),parties);
	}

	@Override
	public Long countPasts(Collection<Party> parties) {
		return dao.countWhereToDateLessThanByDateByParties(universalTimeCoordinated(),parties);
	}

	@Override
	public Collection<Event> findCurrents(Collection<Party> parties) {
		return dao.readWhereDateBetweenPeriodByParties(universalTimeCoordinated(),parties);
	}

	@Override
	public Long countCurrents(Collection<Party> parties) {
		return dao.countWhereDateBetweenPeriodByParties(universalTimeCoordinated(),parties);
	}

	@Override
	public Collection<Event> findOnComings(Collection<Party> parties) {
		return dao.readWhereFromDateGreaterThanByDateByParties(universalTimeCoordinated(),parties);
	}

	@Override
	public Long countOnComings(Collection<Party> parties) {
		return dao.countWhereFromDateGreaterThanByDateByParties(universalTimeCoordinated(),parties);
	}

	/*
	@Override
	public Collection<Event> findPersonBirthDateAnniversariesByPeriod(Period period) {
		return dao.readPersonBirthDateByMonthIndexes(timeBusiness.findMonthIndexes(period));
	}

	@Override
	public Long countPersonBirthDateAnniversariesByPeriod(Period period) {
		// TODO Auto-generated method stub
		return null;
	}
	*/

}
