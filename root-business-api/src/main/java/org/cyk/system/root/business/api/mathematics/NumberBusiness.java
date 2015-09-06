package org.cyk.system.root.business.api.mathematics;

import java.math.BigDecimal;
import java.util.Locale;

public interface NumberBusiness {

	String format(Number number,Locale locale);
	String format(Number number);
	
	BigDecimal computePercentage(BigDecimal value,BigDecimal percent);
	BigDecimal incrementBy(BigDecimal value,BigDecimal increment);
	BigDecimal incrementByPercentage(BigDecimal value,BigDecimal percent);
	
	
	BigDecimal _100 = new BigDecimal("100");
}
