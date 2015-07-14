package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventSearchCriteria;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.event.EventDao;
import org.cyk.system.root.persistence.api.event.EventReminderDao;
import org.cyk.system.root.persistence.impl.QueryWrapper;

public class EventDaoImpl extends AbstractIdentifiablePeriodDaoImpl<Event> implements EventDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	private static final String READ_BY_PARTIES_FORMAT = "SELECT event FROM Event event WHERE %s AND "
			+ "(event.owner.identifier IN :identifiers OR EXISTS ( SELECT ep FROM EventParticipation ep WHERE ep.event = event AND ep.party.identifier IN :identifiers))"
			+ " ORDER BY event.period.fromDate DESC";
	
	private static final String READ_BY_CRITERIA_SELECT_FORMAT = "SELECT event FROM Event event ";
	private static final String READ_BY_CRITERIA_WHERE_FORMAT = "WHERE event.period.fromDate BETWEEN :fromDate AND :toDate ";
	
	private static final String READ_BY_CRITERIA_NOTORDERED_FORMAT = READ_BY_CRITERIA_SELECT_FORMAT+READ_BY_CRITERIA_WHERE_FORMAT;
	private static final String READ_BY_CRITERIA_ORDERED_FORMAT = READ_BY_CRITERIA_SELECT_FORMAT+READ_BY_CRITERIA_WHERE_FORMAT+ORDER_BY_FORMAT;
	
	@Inject private EventReminderDao eventReminderDao;
	
    private String readAllSortedByDate,readByCriteria,countByCriteria,readByCriteriaDateAscendingOrder,readByCriteriaDateDescendingOrder,
    	readWhereFromDateBetweenPeriodByParties,countWhereFromDateBetweenPeriodByParties
    	,readWhereToDateLessThanByDateByParties,countWhereToDateLessThanByDateByParties
    	,readWhereDateBetweenPeriodByParties,countWhereDateBetweenPeriodByParties
    	,readWhereFromDateGreaterThanByDateByParties,countWhereFromDateGreaterThanByDateByParties;
    
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readWhereFromDateBetweenPeriodByParties,String.format(READ_BY_PARTIES_FORMAT, "event.period.fromDate BETWEEN :startDate AND :endDate"));
        registerNamedQuery(readWhereToDateLessThanByDateByParties,String.format(READ_BY_PARTIES_FORMAT, "event.period.toDate < :thedate"));
        registerNamedQuery(readWhereDateBetweenPeriodByParties,String.format(READ_BY_PARTIES_FORMAT, ":thedate BETWEEN event.period.fromDate AND event.period.toDate"));
        registerNamedQuery(readWhereFromDateGreaterThanByDateByParties,String.format(READ_BY_PARTIES_FORMAT, "event.period.fromDate > :thedate"));
        
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
    public Collection<Event> readWhereFromDateBetweenPeriodByParties(Period period, Collection<Party> parties) {
    	return namedQuery(readWhereFromDateBetweenPeriodByParties).parameter("startDate", period.getFromDate()).parameter("endDate", period.getToDate())
    			.parameterIdentifiers(parties)
                .resultMany();
    }
    
    @Override
    public Long countWhereFromDateBetweenPeriodByParties(Period period,Collection<Party> parties) {
    	return countNamedQuery(countWhereFromDateBetweenPeriodByParties).parameter("startDate", period.getFromDate()).parameter("endDate", period.getToDate())
    			.parameterIdentifiers(parties)
                .resultOne();
    }
    
    /**/
    
    protected void applyCriteriaParameters(QueryWrapper<?> queryWrapper,EventSearchCriteria searchCriteria){
    	Period period = searchCriteria.getPeriodSearchCriteria().getPeriod();
		queryWrapper.parameter("fromDate",period.getFromDate());
		queryWrapper.parameter("toDate",period.getToDate());
	}
    
    /**/
    
    @Override
    public Event delete(Event event) {
    	eventReminderDao.deleteByEvent(event);
    	return super.delete(event);
    }

	@Override
	public Collection<Event> readWhereToDateLessThanByDateByParties(Date date, Collection<Party> parties) {
		return namedQuery(readWhereToDateLessThanByDateByParties).parameter("thedate", date).parameterIdentifiers(parties).resultMany();
	}

	@Override
	public Long countWhereToDateLessThanByDateByParties(Date date, Collection<Party> parties) {
		return countNamedQuery(countWhereToDateLessThanByDateByParties).parameter("thedate", date).parameterIdentifiers(parties).resultOne();
	}

	@Override
	public Collection<Event> readWhereDateBetweenPeriodByParties(Date date, Collection<Party> parties) {
		return namedQuery(readWhereDateBetweenPeriodByParties).parameter("thedate", date).parameterIdentifiers(parties).resultMany();
	}

	@Override
	public Long countWhereDateBetweenPeriodByParties(Date date, Collection<Party> parties) {
		return countNamedQuery(countWhereDateBetweenPeriodByParties).parameter("thedate", date).parameterIdentifiers(parties).resultOne();
	}

	@Override
	public Collection<Event> readWhereFromDateGreaterThanByDateByParties(Date date, Collection<Party> parties) {
		return namedQuery(readWhereFromDateGreaterThanByDateByParties).parameter("thedate", date).parameterIdentifiers(parties).resultMany();
	}

	@Override
	public Long countWhereFromDateGreaterThanByDateByParties(Date date, Collection<Party> parties) {
		return countNamedQuery(countWhereFromDateGreaterThanByDateByParties).parameter("thedate", date).parameterIdentifiers(parties).resultOne();
	}

}
 