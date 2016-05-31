package org.cyk.system.root.model.search;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class IntegerSearchCriteria extends AbstractNumberSearchCriteria<Integer> implements Serializable {

	private static final long serialVersionUID = -1648133246443265214L;
	
	public IntegerSearchCriteria(Integer value) {
		super(value);
	}
	
	public IntegerSearchCriteria(IntegerSearchCriteria criteria) {
		super(criteria);
	}

}
