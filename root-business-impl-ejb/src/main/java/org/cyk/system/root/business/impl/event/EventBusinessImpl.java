package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.event.EventPartyBusiness;
import org.cyk.system.root.business.api.event.EventReminderBusiness;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.impl.time.AbstractIdentifiablePeriodBusinessImpl;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.Event.SearchCriteria;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.event.EventDao;
import org.cyk.system.root.persistence.api.event.EventPartyDao;
import org.cyk.system.root.persistence.api.event.EventReminderDao;

public class EventBusinessImpl extends AbstractIdentifiablePeriodBusinessImpl<Event, EventDao> implements EventBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public EventBusinessImpl(EventDao dao) {
		super(dao); 
	}  
    
	//@Secure
    @Override
    public Collection<Event> findWhereFromDateBetweenPeriodByParties(Period period, Collection<Party> parties) {
    	return dao.readWhereFromDateBetweenPeriodByParties(period, parties);
    }
    @Override
    public Long countWhereFromDateBetweenPeriodByParties(Period period,Collection<Party> parties) {
    	return dao.countWhereFromDateBetweenPeriodByParties(period, parties);
    }
    
    @Override
    protected void beforeCreate(Event event) {
    	super.beforeCreate(event);
    	createIfNotIdentified(event.getContactCollection());
    }
    
    @Override
    protected void afterCreate(Event event) {
    	super.afterCreate(event);
    	if(event.getEventParties().isSynchonizationEnabled())
    		inject(EventPartyBusiness.class).create(event.getEventParties().getCollection());
    	if(event.getEventReminders().isSynchonizationEnabled())
    		inject(EventReminderBusiness.class).create(event.getEventReminders().getCollection());
    }
    
    @Override
    protected void beforeDelete(Event event) {
    	super.beforeDelete(event);
    	if(event.getContactCollection()!=null){
    		ContactCollection contactCollection = event.getContactCollection();
    		event.setContactCollection(null);
    		inject(ContactCollectionBusiness.class).delete(contactCollection);
    	}
    	inject(EventPartyBusiness.class).delete(inject(EventPartyDao.class).readByEvent(event));
    	inject(EventReminderBusiness.class).delete(inject(EventReminderDao.class).readByEvent(event));
    }    
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<Event> findByCriteria(SearchCriteria criteria) {
		//Collection<Sale> sales = null;
		/*if(criteria.getFromDateSearchCriteria().getValue()==null || criteria.getToDateSearchCriteria().getValue()==null)
			sales = findAll();
		*/
		
		//criteriaDefaultValues(criteria);
		return dao.readByCriteria(criteria);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByCriteria(SearchCriteria criteria) {
		/*
		if(criteria.getFromDateSearchCriteria().getValue()==null || criteria.getToDateSearchCriteria().getValue()==null)
    		return countAll();
    		*/
		//criteriaDefaultValues(criteria);
		return dao.countByCriteria(criteria);
	}
	
	/*protected void __load__(Event event) {
		event.setEventPartys(eventPartyDao.readByEvents(Arrays.asList(event)));
	}*/

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
