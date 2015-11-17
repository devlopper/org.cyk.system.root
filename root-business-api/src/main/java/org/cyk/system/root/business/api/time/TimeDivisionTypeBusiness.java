package org.cyk.system.root.business.api.time;

import java.math.BigDecimal;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.time.TimeDivisionType;

public interface TimeDivisionTypeBusiness extends AbstractEnumerationBusiness<TimeDivisionType> {
    
	BigDecimal convertToDivisionDuration(TimeDivisionType timeDivisionType,Long millisecond);
	Long convertToMillisecond(TimeDivisionType timeDivisionType,BigDecimal duration);
    
}
