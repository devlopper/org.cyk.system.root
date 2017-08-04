package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;

import org.cyk.system.root.model.mathematics.Rank;

public interface Sortable {
	
	BigDecimal getValue();
	
	Rank getRank();
	
}
