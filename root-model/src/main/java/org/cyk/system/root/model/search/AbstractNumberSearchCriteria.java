package org.cyk.system.root.model.search;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractNumberSearchCriteria<NUMBER extends Number> extends AbstractFieldValueSearchCriteria<NUMBER> implements Serializable {

	private static final long serialVersionUID = -1648133246443265214L;

	protected NUMBER lowest;
	protected NUMBER highest;
	
	public AbstractNumberSearchCriteria(NUMBER value) {
		super(value);
	}
	
	public AbstractNumberSearchCriteria(AbstractNumberSearchCriteria<NUMBER> criteria) {
		super(criteria);
		lowest = criteria.lowest;
		highest = criteria.highest;
	}

}
