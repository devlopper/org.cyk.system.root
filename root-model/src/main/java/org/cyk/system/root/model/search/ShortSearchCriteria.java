package org.cyk.system.root.model.search;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ShortSearchCriteria extends AbstractNumberSearchCriteria<Short> implements Serializable {

	private static final long serialVersionUID = -1648133246443265214L;
	
	public ShortSearchCriteria(Short value) {
		super(value);
	}
	
	public ShortSearchCriteria(ShortSearchCriteria criteria) {
		super(criteria);
	}

}
