package org.cyk.system.root.persistence.impl.event;

import static org.cyk.utility.common.computation.ArithmeticOperator.GT;
import static org.cyk.utility.common.computation.ArithmeticOperator.GTE;
import static org.cyk.utility.common.computation.ArithmeticOperator.LT;
import static org.cyk.utility.common.computation.ArithmeticOperator.LTE;
import static org.cyk.utility.common.computation.LogicalOperator.AND;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.cyk.system.root.model.time.AbstractIdentifiablePeriod;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.persistence.api.event.AbstractIdentifiablePeriodDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public abstract class AbstractIdentifiablePeriodDaoImpl<IDENTIFIABLE extends AbstractIdentifiablePeriod> extends AbstractTypedDao<IDENTIFIABLE> implements AbstractIdentifiablePeriodDao<IDENTIFIABLE>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
    protected String readWhereFromDateGreaterThanByDate,countWhereFromDateGreaterThanByDate,readWhereToDateLessThanByDate,countWhereToDateLessThanByDate,
    	readWhereFromDateBetweenPeriod,countWhereFromDateBetweenPeriod,readWhereDateBetweenPeriod,countWhereDateBetweenPeriod;
    
    @Override
    protected void namedQueriesInitialisation() {
        super.namedQueriesInitialisation();
        registerNamedQuery(readWhereFromDateGreaterThanByDate, _select().where("globalIdentifier.existencePeriod.fromDate", "fromDate",GT).orderBy("globalIdentifier.existencePeriod.fromDate",Boolean.FALSE));
        registerNamedQuery(readWhereToDateLessThanByDate, _select().where("globalIdentifier.existencePeriod.toDate", "toDate",LT).orderBy("globalIdentifier.existencePeriod.fromDate",Boolean.FALSE));
        registerNamedQuery(readWhereFromDateBetweenPeriod, _select().where("globalIdentifier.existencePeriod.fromDate", "startDate",GTE).where(AND,"globalIdentifier.existencePeriod.fromDate", "endDate",LTE)
        		.orderBy("globalIdentifier.existencePeriod.fromDate",Boolean.FALSE));
        registerNamedQuery(readWhereDateBetweenPeriod, _select().where("globalIdentifier.existencePeriod.fromDate", "thedate",LTE).where(AND, "globalIdentifier.existencePeriod.toDate","thedate",GTE)
        		.orderBy("globalIdentifier.existencePeriod.fromDate",Boolean.FALSE));
    }
    
    @Override
    public Collection<IDENTIFIABLE> readWhereFromDateGreaterThanByDate(Date date) {
        return namedQuery(readWhereFromDateGreaterThanByDate).parameter("fromDate", date)
                .resultMany();
    }

    @Override
    public Long countWhereFromDateGreaterThanByDate(Date date) {
        return countNamedQuery(countWhereFromDateGreaterThanByDate).parameter("fromDate", date)
                .resultOne();
    }
    
    @Override
    public Collection<IDENTIFIABLE> readWhereToDateLessThanByDate(Date date) {
        return namedQuery(readWhereToDateLessThanByDate).parameter("toDate", date)
                .resultMany();
    }

    @Override
    public Long countWhereToDateLessThanByDate(Date date) {
        return countNamedQuery(countWhereToDateLessThanByDate).parameter("toDate", date)
                .resultOne();
    }

    @Override
    public Collection<IDENTIFIABLE> readWhereFromDateBetweenPeriod(Period period) {
        return namedQuery(readWhereFromDateBetweenPeriod).parameter("startDate", period.getFromDate()).parameter("endDate", period.getToDate())
                .resultMany();
    }

    @Override
    public Long countWhereFromDateBetweenPeriod(Period period) {
        return countNamedQuery(countWhereFromDateBetweenPeriod).parameter("startDate", period.getFromDate()).parameter("endDate", period.getToDate())
                .resultOne();
    }
    
	@Override
	public Collection<IDENTIFIABLE> readWhereDateBetweenPeriod(Date date) {
		return namedQuery(readWhereDateBetweenPeriod).parameter("thedate", date)
                .resultMany();
	}
	
	@Override
	public Long countWhereDateBetweenPeriod(Date date) {
		return countNamedQuery(countWhereDateBetweenPeriod).parameter("thedate", date)
				.resultOne();
	}

}
 