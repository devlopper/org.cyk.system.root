package org.cyk.system.root.model.time;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.search.DateSearchCriteria;
import org.cyk.utility.common.Constant;

@Getter @Setter
public class PeriodSearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

	private static final long serialVersionUID = 6796076474234170332L;

	private DateSearchCriteria fromDateSearchCriteria,toDateSearchCriteria;
	
	public PeriodSearchCriteria(){
		this(null,null);
	}
	
	public PeriodSearchCriteria(Date fromDate,Date toDate) {
		this.fromDateSearchCriteria = new DateSearchCriteria(fromDate);
		this.toDateSearchCriteria = new DateSearchCriteria(toDate);
	}
	
	public PeriodSearchCriteria(PeriodSearchCriteria criteria) {
		this.fromDateSearchCriteria = new DateSearchCriteria(criteria.fromDateSearchCriteria);
		this.toDateSearchCriteria = new DateSearchCriteria(criteria.toDateSearchCriteria);
	}
	
	public Period getPeriod(){
		if(fromDateSearchCriteria.getPreparedValue()==null)
			if(toDateSearchCriteria.getPreparedValue()==null)
				return new Period(Constant.DATE_LOWEST_VALUE, Constant.DATE_HIGHEST_VALUE);
			else
				return new Period(Constant.DATE_LOWEST_VALUE, toDateSearchCriteria.getPreparedValue());
		else
			if(toDateSearchCriteria.getPreparedValue()==null)
				return new Period(fromDateSearchCriteria.getPreparedValue(), Constant.DATE_HIGHEST_VALUE);
			else
				return new Period(fromDateSearchCriteria.getPreparedValue(), toDateSearchCriteria.getPreparedValue());
	}
	
}
