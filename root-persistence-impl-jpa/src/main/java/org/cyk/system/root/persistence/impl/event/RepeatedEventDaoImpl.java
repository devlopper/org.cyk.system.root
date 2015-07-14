package org.cyk.system.root.persistence.impl.event;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.cyk.system.root.model.event.RepeatedEvent;
import org.cyk.system.root.persistence.api.event.RepeatedEventDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;
import org.cyk.utility.common.computation.ArithmeticOperator;

public class RepeatedEventDaoImpl extends AbstractTypedDao<RepeatedEvent> implements RepeatedEventDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
    private String readByDayOfMonth,countByDayOfMonth,readByDayOfMonthByMonth,countByDayOfMonthByMonth,readByMonths,countByMonths;
    
    @Override
    protected void namedQueriesInitialisation() {
    	super.namedQueriesInitialisation();
    	registerNamedQuery(readByMonths, _select().where("date.month","monthIndexes",ArithmeticOperator.IN));
        registerNamedQuery(readByDayOfMonth, _select().where("date.day","dayOfMonthIndex"));
        registerNamedQuery(readByDayOfMonthByMonth, _select().where("date.day","dayOfMonthIndex").and("date.month", "monthIndex", ArithmeticOperator.EQ));   
    }
     
	@Override
	public Collection<RepeatedEvent> readByDayOfMonth(Integer dayOfMonth) {
		return namedQuery(readByDayOfMonth).parameter("dayOfMonthIndex", dayOfMonth).resultMany();
	}
	@Override
	public Long countByDayOfMonth(Integer dayOfMonth) {
		return countNamedQuery(countByDayOfMonth).parameter("dayOfMonthIndex", dayOfMonth).resultOne();
	}

	@Override
	public Collection<RepeatedEvent> readByDayOfMonthByMonth(Integer dayOfMonth,Integer month) {
		return namedQuery(readByDayOfMonthByMonth).parameter("dayOfMonthIndex", dayOfMonth).parameter("monthIndex", month).resultMany();
	}

	@Override
	public Long countByDayOfMonthByMonth(Integer dayOfMonth, Integer month) {
		return countNamedQuery(countByDayOfMonthByMonth).parameter("dayOfMonthIndex", dayOfMonth).parameter("monthIndex", month).resultOne();
	}

	@Override
	public Collection<RepeatedEvent> readByMonth(Integer month) {
		Set<Integer> set = new HashSet<>();
		set.add(month);
		return readByMonths(set);
	}

	@Override
	public Long countByMonth(Integer month) {
		Set<Integer> set = new HashSet<>();
		set.add(month);
		return countByMonths(set);
	}

	@Override
	public Collection<RepeatedEvent> readByMonths(Set<Integer> months) {
		return namedQuery(readByMonths).parameter("monthIndexes", months).resultMany();
	}

	@Override
	public Long countByMonths(Set<Integer> months) {
		return countNamedQuery(countByMonths).parameter("monthIndexes", months).resultOne();
	}

}
 