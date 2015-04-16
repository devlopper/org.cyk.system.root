package org.cyk.system.root.business.impl.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.cyk.system.root.business.api.event.AbstractIdentifiablePeriodBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.event.AbstractIdentifiablePeriod;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.event.AbstractIdentifiablePeriodDao;

public abstract class AbstractIdentifiablePeriodBusinessImpl<IDENTIFIABLE extends AbstractIdentifiablePeriod,DAO extends AbstractIdentifiablePeriodDao<IDENTIFIABLE>> extends AbstractTypedBusinessService<IDENTIFIABLE, DAO> implements AbstractIdentifiablePeriodBusiness<IDENTIFIABLE>,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	public AbstractIdentifiablePeriodBusinessImpl(DAO dao) {
		super(dao); 
	}  

    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Collection<IDENTIFIABLE> findWhereFromDateBetweenPeriod(Period period) {
        return dao.readWhereFromDateBetweenPeriod(period);
    }

    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Long countWhereFromDateBetweenPeriod(Period period) {
        return dao.countWhereFromDateBetweenPeriod(period);
    }
    
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Collection<IDENTIFIABLE> findWhereFromDateGreaterThanByDate(Date date) {
        return dao.readWhereFromDateGreaterThanByDate(date);
    }

    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Long countWhereFromDateGreaterThanByDate(Date date) {
        return dao.countWhereFromDateGreaterThanByDate(date);
    }

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findWhereToDateLessThanByDate(Date date) {
		return dao.readWhereToDateLessThanByDate(date);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countWhereToDateLessThanByDate(Date date) {
		return dao.countWhereToDateLessThanByDate(date);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findWhereDateBetweenPeriod(Date date) {
		return dao.readWhereDateBetweenPeriod(date);
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countWhereDateBetweenPeriod(Date date) {
		return dao.countWhereDateBetweenPeriod(date);
	}
    
	/**/
	
    @Override @TransactionAttribute(TransactionAttributeType.NEVER)
    public Long findDuration(Collection<IDENTIFIABLE> identifiables) {
    	Collection<Period> periods = new ArrayList<>();
    	for(IDENTIFIABLE identifiable : identifiables)
    		periods.add(identifiable.getPeriod());
    	return timeBusiness.findDuration(periods);
    }

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findPasts() {
		return findWhereToDateLessThanByDate(universalTimeCoordinated());
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countPasts() {
		return countWhereToDateLessThanByDate(universalTimeCoordinated());
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findCurrents() {
		return findWhereDateBetweenPeriod(universalTimeCoordinated());
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countCurrents() {
		return countWhereDateBetweenPeriod(universalTimeCoordinated());
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<IDENTIFIABLE> findOnComings() {
		return findWhereFromDateGreaterThanByDate(universalTimeCoordinated());
	}

	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countOnComings() {
		return countWhereFromDateGreaterThanByDate(universalTimeCoordinated());
	}
    

}
