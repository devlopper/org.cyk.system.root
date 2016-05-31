package org.cyk.system.root.model.search;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class BooleanSearchCriteria extends AbstractFieldValueSearchCriteria<Boolean> implements Serializable {

	private static final long serialVersionUID = -1648133246443265214L;
	
	{
		nullValue = Boolean.FALSE;
	}
	
	public BooleanSearchCriteria(Boolean value) {
		super(value);
	}
	
	public BooleanSearchCriteria(BooleanSearchCriteria criteria) {
		super(criteria);
	}
	
}
