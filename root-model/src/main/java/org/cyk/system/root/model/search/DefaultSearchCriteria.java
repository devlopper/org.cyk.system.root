package org.cyk.system.root.model.search;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.search.AbstractPeriodSearchCriteria;

@Getter @Setter @NoArgsConstructor
public class DefaultSearchCriteria extends AbstractPeriodSearchCriteria implements Serializable {

	private static final long serialVersionUID = 6796076474234170332L;

	private StringSearchCriteria identifierStringSearchCriteria = new StringSearchCriteria();
	
	public DefaultSearchCriteria(Date fromDate,Date toDate) {
		super(fromDate,toDate);
	}
	
	public DefaultSearchCriteria(DefaultSearchCriteria criteria) {
		super(criteria);
		identifierStringSearchCriteria = new StringSearchCriteria(criteria.identifierStringSearchCriteria);
	}
}
