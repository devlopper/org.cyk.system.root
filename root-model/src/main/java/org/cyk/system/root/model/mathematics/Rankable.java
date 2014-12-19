package org.cyk.system.root.model.mathematics;

import java.math.BigDecimal;

public interface Rankable {
	
	BigDecimal getValue();
	
	Rank getRank();
	
}
