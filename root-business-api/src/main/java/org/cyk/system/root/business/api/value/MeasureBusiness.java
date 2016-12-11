package org.cyk.system.root.business.api.value;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.value.Measure;

public interface MeasureBusiness extends AbstractEnumerationBusiness<Measure> {
    
	/**
	 * compute the multiple by doing a multiplication between measure and value
	 */
	BigDecimal computeMultiple(Measure measure,BigDecimal value,Integer scale,RoundingMode roundingMode);
	BigDecimal computeMultiple(Measure measure,BigDecimal value,MathContext mathContext);
	BigDecimal computeMultiple(Measure measure,BigDecimal value);
	
	/**
	 * compute the quotient by doing a division between value and measure
	 */
	BigDecimal computeQuotient(Measure measure,BigDecimal value,Integer scale,RoundingMode roundingMode);
	BigDecimal computeQuotient(Measure measure,BigDecimal value,MathContext mathContext);
	BigDecimal computeQuotient(Measure measure,BigDecimal value);
    
}
