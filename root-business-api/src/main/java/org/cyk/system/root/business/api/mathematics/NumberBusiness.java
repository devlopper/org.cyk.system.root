package org.cyk.system.root.business.api.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

public interface NumberBusiness {

	String format(Number number,Locale locale);
	String format(Number number);
	
	String formatToBase(Number number,FormatToBaseArguments arguments);
	
	@Getter @Setter
	public static class FormatToBaseArguments implements Serializable{
		private static final long serialVersionUID = 5580484664839713712L;
		private Byte base;
	}
	
	BigDecimal computePercentage(BigDecimal value,BigDecimal percent);
	BigDecimal incrementBy(BigDecimal value,BigDecimal increment);
	BigDecimal incrementByPercentage(BigDecimal value,BigDecimal percent);
	
	BigDecimal parseBigDecimal(String value);
	
	BigDecimal _100 = new BigDecimal("100");
}
