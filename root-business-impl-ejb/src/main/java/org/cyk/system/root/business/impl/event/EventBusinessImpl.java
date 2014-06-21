package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.persistence.api.event.EventDao;

@Stateless
public class EventBusinessImpl extends AbstractTypedBusinessService<Event, EventDao> implements EventBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

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
    public void create(Collection<Event> events) {
        for(Event event : events)
            //create(event);
            dao.create(event);
    }
	
}
