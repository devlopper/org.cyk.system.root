package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Singleton;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;

@Singleton
public class NumberBusinessImpl implements NumberBusiness,Serializable {

	private static final long serialVersionUID = -1477124794923780532L;
	
	//private NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
	//private DecimalFormat myFormatter = new DecimalFormat(pattern);

	private LanguageBusiness languageBusiness = LanguageBusinessImpl.getInstance();
	
	@Override
	public String format(Number number, Locale locale) {
		if(number==null)
			return null;
		NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale);
		return numberFormatter.format(number);
	}
	
	@Override
	public String format(Number number) {
		return format(number, languageBusiness.findCurrentLocale());
	}
	
	@Override
	public BigDecimal computePercentage(BigDecimal value, BigDecimal percent) {
		return value.multiply(percent).divide(_100);
	}
	
	@Override
	public BigDecimal incrementBy(BigDecimal value, BigDecimal increment) {
		return value.add(increment);
	}

	@Override
	public BigDecimal incrementByPercentage(BigDecimal value, BigDecimal percent) {
		return incrementBy(value, computePercentage(value, percent));
	}

}
