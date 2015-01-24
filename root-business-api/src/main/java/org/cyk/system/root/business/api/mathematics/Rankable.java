package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;

import org.cyk.system.root.model.mathematics.Rank;

public interface Rankable {
	
	BigDecimal getValue();
	
	Rank getRank();
	
}
