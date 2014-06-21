package org.cyk.system.root.persistence.impl.event;

import static org.cyk.utility.common.computation.ArithmeticOperator.GT;
import static org.cyk.utility.common.computation.ArithmeticOperator.GTE;
import static org.cyk.utility.common.computation.ArithmeticOperator.LTE;
import static org.cyk.utility.common.computation.LogicalOperator.AND;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.event.AbstractEvent;
import org.cyk.system.root.persistence.api.event.AbstractEventDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractEventDaoImpl<EVENT extends AbstractEvent> extends AbstractTypedDao<EVENT> implements AbstractEventDao<EVENT>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	private String readWhereFromDateGreaterThanByDate,countWhereFromDateGreaterThanByDate,
	readWhereFromDateBetweenByStartDateByEndDate,countWhereFromDateBetweenByStartDateByEndDate;
	
	@Override
	protected void namedQueriesInitialisation() {
	    super.namedQueriesInitialisation();
	    registerNamedQuery(readWhereFromDateGreaterThanByDate, _select().where("period.fromDate", "fromDate",GT));
	    registerNamedQuery(readWhereFromDateBetweenByStartDateByEndDate, _select().where("period.fromDate", "startDate",GTE)
	            .where(AND,"period.fromDate", "endDate",LTE));
	}
	
    @Override
    public Collection<EVENT> readWhereFromDateGreaterThanByDate(Date date) {
        return namedQuery(readWhereFromDateGreaterThanByDate).parameter("fromDate", date)
                .resultMany();
    }

    @Override
    public Long countWhereFromDateGreaterThanByDate(Date date) {
        return countNamedQuery(countWhereFromDateGreaterThanByDate).parameter("fromDate", date)
                .resultOne();
    }

    @Override
    public Collection<EVENT> readWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return namedQuery(readWhereFromDateBetweenByStartDateByEndDate).parameter("startDate", startDate).parameter("endDate", endDate)
                .resultMany();
    }

    @Override
    public Long countWhereFromDateBetweenByStartDateByEndDate(Date startDate, Date endDate) {
        return countNamedQuery(countWhereFromDateBetweenByStartDateByEndDate).parameter("startDate", startDate).parameter("endDate", endDate)
                .resultOne();
    }

}
 