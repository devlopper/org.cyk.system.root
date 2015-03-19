package org.cyk.system.root.persistence.impl.event;

import static org.cyk.utility.common.computation.ArithmeticOperator.GT;
import static org.cyk.utility.common.computation.ArithmeticOperator.GTE;
import static org.cyk.utility.common.computation.ArithmeticOperator.LTE;
import static org.cyk.utility.common.computation.LogicalOperator.AND;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventSearchCriteria;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.event.EventDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;

public class EventDaoImpl extends AbstractTypedDao<Event> implements EventDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private static final String READ_BY_CRITERIA_SELECT_FORMAT = "SELECT event FROM Event event ";
	private static final String READ_BY_CRITERIA_WHERE_FORMAT = "WHERE event.period.fromDate BETWEEN :fromDate AND :toDate ";
	
	private static final String READ_BY_CRITERIA_NOTORDERED_FORMAT = READ_BY_CRITERIA_SELECT_FORMAT+READ_BY_CRITERIA_WHERE_FORMAT;
	private static final String READ_BY_CRITERIA_ORDERED_FORMAT = READ_BY_CRITERIA_SELECT_FORMAT+READ_BY_CRITERIA_WHERE_FORMAT+ORDER_BY_FORMAT;
	
    private String readWhereFromDateGreaterThanByDate,countWhereFromDateGreaterThanByDate,readWhereFromDateBetweenByStartDateByEndDate,
    	countWhereFromDateBetweenByStartDateByEndDate,readAllSortedByDate,readByCriteria,countByCriteria,readByCriteriaDateAscendingOrder,readByCriteriaDateDescendingOrder,
    	readWhereAlarmFromDateBetween,countWhereAlarmFromDateBetween,readWhereDateBetweenAlarmPeriod,countWhereDateBetweenAlarmPeriod,
    	readWhereDateBetweenAlarmPeriodByAlarmEnabled,countWhereDateBetweenAlarmPeriodByAlarmEnabled;
    
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readWhereFromDateGreaterThanByDate, _select().where("period.fromDate", "fromDate",GT));
        registerNamedQuery(readWhereFromDateBetweenByStartDateByEndDate, _select().where("period.fromDate", "startDate",GTE)
                .where(AND,"period.fromDate", "endDate",LTE));
        registerNamedQuery(readWhereAlarmFromDateBetween, _select().where("alarm.period.fromDate", "startDate",GTE)
                .where(AND,"alarm.period.fromDate", "endDate",LTE));
        registerNamedQuery(readWhereDateBetweenAlarmPeriod, "SELECT event FROM Event event WHERE :thedate BETWEEN event.alarm.period.fromDate AND event.alarm.period.toDate");
        registerNamedQuery(readWhereDateBetweenAlarmPeriodByAlarmEnabled, "SELECT event FROM Event event WHERE :thedate BETWEEN event.alarm.period.fromDate AND "
        		+ "event.alarm.period.toDate AND event.alarm.enabled = :alarmEnabled");
        
        registerNamedQuery(readAllSortedByDate,READ_BY_CRITERIA_SELECT_FORMAT+" ORDER BY event.period.fromDate DESC");
    	registerNamedQuery(readByCriteria,READ_BY_CRITERIA_NOTORDERED_FORMAT);
        registerNamedQuery(readByCriteriaDateAscendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "event.period.fromDate ASC") );
        registerNamedQuery(readByCriteriaDateDescendingOrder,String.format(READ_BY_CRITERIA_ORDERED_FORMAT, "event.period.fromDate DESC") );
    }
    
    @Override
	public Collection<Event> readAll() {
		return namedQuery(readAllSortedByDate).resultMany();
	}
    
    @SuppressWarnings("unchecked")
	@Override
	public Collection<Event> readByCriteria(EventSearchCriteria searchCriteria) {
		String queryName = null;
		if(searchCriteria.getPeriodSearchCriteria().getFromDateSearchCriteria().getAscendingOrdered()!=null){
			queryName = Boolean.TRUE.equals(searchCriteria.getPeriodSearchCriteria().getFromDateSearchCriteria().getAscendingOrdered())?
					readByCriteriaDateAscendingOrder:readByCriteriaDateDescendingOrder;
		}else
			queryName = readByCriteriaDateAscendingOrder;
		QueryWrapper<?> queryWrapper = namedQuery(queryName);
		applyCriteriaParameters(queryWrapper, searchCriteria);
		return (Collection<Event>) queryWrapper.resultMany();
	}

	@Override
	public Long countByCriteria(EventSearchCriteria searchCriteria) {
		QueryWrapper<?> queryWrapper = countNamedQuery(countByCriteria);
		applyCriteriaParameters(queryWrapper, searchCriteria);
		return (Long) queryWrapper.resultOne();
	}
    
    @Override
    public Collection<Event> readWhereFromDateGreaterThanByDate(Date date) {
        return namedQuery(readWhereFromDateGreaterThanByDate).parameter("fromDate", date)
                .resultMany();
    }

    @Override
    public Long countWhereFromDateGreaterThanByDate(Date date) {
        return countNamedQuery(countWhereFromDateGreaterThanByDate).parameter("fromDate", date)
                .resultOne();
    }

    @Override
    public Collection<Event> readWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return namedQuery(readWhereFromDateBetweenByStartDateByEndDate).parameter("startDate", startDate).parameter("endDate", endDate)
                .resultMany();
    }

    @Override
    public Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return countNamedQuery(countWhereFromDateBetweenByStartDateByEndDate).parameter("startDate", startDate).parameter("endDate", endDate)
                .resultOne();
    }
    
    /**/
    
    protected void applyCriteriaParameters(QueryWrapper<?> queryWrapper,EventSearchCriteria searchCriteria){
    	Period period = searchCriteria.getPeriodSearchCriteria().getPeriod();
		queryWrapper.parameter("fromDate",period.getFromDate());
		queryWrapper.parameter("toDate",period.getToDate());
	}

	@Override
	public Collection<Event> readWhereAlarmFromDateBetween(Period period) {
		return namedQuery(readWhereAlarmFromDateBetween).parameter("startDate", period.getFromDate()).parameter("endDate", period.getToDate())
                .resultMany();
	}

	@Override
	public Long countWhereAlarmFromDateBetween(Period period) {
		return countNamedQuery(countWhereAlarmFromDateBetween).parameter("startDate", period.getFromDate()).parameter("endDate", period.getToDate())
				.resultOne();
	}
	
	@Override
	public Collection<Event> readWhereDateBetweenAlarmPeriod(Date date) {
		return namedQuery(readWhereDateBetweenAlarmPeriod).parameter("thedate", date)
                .resultMany();
	}
	
	@Override
	public Long countWhereDateBetweenAlarmPeriod(Date date) {
		return countNamedQuery(countWhereDateBetweenAlarmPeriod).parameter("thedate", date)
				.resultOne();
	}

	@Override
	public Collection<Event> readWhereDateBetweenAlarmPeriodByAlarmEnabled(Date date, Boolean alarmEnabled) {
		return namedQuery(readWhereDateBetweenAlarmPeriodByAlarmEnabled).parameter("thedate", date).parameter("alarmEnabled", alarmEnabled)
                .resultMany();
	}

	@Override
	public Long countWhereDateBetweenAlarmPeriodByAlarmEnabled(Date date,Boolean alarmEnabled) {
		return countNamedQuery(countWhereDateBetweenAlarmPeriodByAlarmEnabled).parameter("thedate", date).parameter("alarmEnabled", alarmEnabled)
				.resultOne();
	}

}
 