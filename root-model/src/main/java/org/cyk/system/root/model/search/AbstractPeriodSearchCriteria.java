package org.cyk.system.root.model.search;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPeriodSearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

	private static final long serialVersionUID = 6796076474234170332L;

	protected DateSearchCriteria fromDateSearchCriteria,toDateSearchCriteria;
	
	public AbstractPeriodSearchCriteria(){
		this(DateSearchCriteria.DATE_MOST_PAST,DateSearchCriteria.DATE_MOST_FUTURE);
	}
	
	public AbstractPeriodSearchCriteria(Date fromDate,Date toDate) {
		this.fromDateSearchCriteria = new DateSearchCriteria(fromDate);
		this.fromDateSearchCriteria.setNullValue(DateSearchCriteria.DATE_MOST_PAST);
		this.fromDateSearchCriteria.setAscendingOrdered(Boolean.TRUE);
		
		this.toDateSearchCriteria = new DateSearchCriteria(toDate);
		this.toDateSearchCriteria.setNullValue(DateSearchCriteria.DATE_MOST_FUTURE);
	}
	
	public AbstractPeriodSearchCriteria(AbstractPeriodSearchCriteria periodSearchCriteria){
		fromDateSearchCriteria = new DateSearchCriteria(periodSearchCriteria.fromDateSearchCriteria);
		toDateSearchCriteria = new DateSearchCriteria(periodSearchCriteria.toDateSearchCriteria);
	}
		
}