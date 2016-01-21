package org.cyk.system.root.model.search;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class BigDecimalSearchCriteria extends AbstractNumberSearchCriteria<BigDecimal> implements Serializable {

	private static final long serialVersionUID = -1648133246443265214L;
	
	public BigDecimalSearchCriteria(BigDecimal value) {
		super(value);
	}
	
	public BigDecimalSearchCriteria(BigDecimalSearchCriteria criteria) {
		super(criteria);
	}

}
