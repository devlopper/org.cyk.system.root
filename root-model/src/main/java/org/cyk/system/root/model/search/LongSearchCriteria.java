package org.cyk.system.root.model.search;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class LongSearchCriteria extends AbstractFieldValueSearchCriteria<Long> implements Serializable {

	private static final long serialVersionUID = -1648133246443265214L;
	
	{
		nullValue = 0l;
	}
	
	public LongSearchCriteria(Long value) {
		super(value);
	}
	
	public LongSearchCriteria(LongSearchCriteria criteria) {
		super(criteria);
	}
	
}
