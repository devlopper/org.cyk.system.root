package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.business.api.event.AbstractEventBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.AbstractEvent;
import org.cyk.system.root.persistence.api.event.AbstractEventDao;

public abstract class AbstractEventBusinessImpl<EVENT extends AbstractEvent,DAO extends AbstractEventDao<EVENT>> extends AbstractTypedBusinessService<EVENT, DAO> implements AbstractEventBusiness<EVENT>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	public AbstractEventBusinessImpl(DAO dao) {
		super(dao); 
	}

    @Override
    public Collection<EVENT> findWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return dao.readWhereFromDateBetweenByStartDateByEndDate(startDate, endDate);
    }

    @Override
    public Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return dao.countWhereFromDateBetweenByStartDateByEndDate(startDate, endDate);
    }

    @Override
    public Collection<EVENT> findWhereFromDateGreaterThanByDate(Date date) {
        return dao.readWhereFromDateGreaterThanByDate(date);
    }

    @Override
    public Long countWhereFromDateGreaterThanByDate(Date date) {
        return dao.countWhereFromDateGreaterThanByDate(date);
    }

    @Override
    public void programAlarm(Collection<EVENT> events) {
        // TODO Auto-generated method stub
    }
    
	
}
