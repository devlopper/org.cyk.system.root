package org.cyk.system.root.model.event;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.search.AbstractFieldValueSearchCriteriaSet;
import org.cyk.system.root.model.time.PeriodSearchCriteria;

@Getter @Setter
public class EventSearchCriteria extends AbstractFieldValueSearchCriteriaSet implements Serializable {

	private static final long serialVersionUID = 3134811510557411588L;

	private PeriodSearchCriteria periodSearchCriteria;
	
	public EventSearchCriteria(){
		this(null,null);
	}
	
	public EventSearchCriteria(EventSearchCriteria criteria){
		this.periodSearchCriteria = new PeriodSearchCriteria(criteria.periodSearchCriteria);
	}
	
	public EventSearchCriteria(Date fromDate,Date toDate){
		periodSearchCriteria = new PeriodSearchCriteria(fromDate,toDate);
	}
	
}
