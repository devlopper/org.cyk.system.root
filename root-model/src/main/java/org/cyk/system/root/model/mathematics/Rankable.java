package org.cyk.system.root.model.mathematics;

import java.math.BigDecimal;

public interface Rankable extends Comparable<Rankable>{
	
	public BigDecimal getValue();
	
}
