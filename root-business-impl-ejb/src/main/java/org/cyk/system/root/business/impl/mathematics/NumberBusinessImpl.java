package org.cyk.system.root.business.impl.mathematics;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;

public class NumberBusinessImpl implements NumberBusiness,Serializable {

	private static final long serialVersionUID = -1477124794923780532L;
	
	//private NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
	//private DecimalFormat myFormatter = new DecimalFormat(pattern);

	private LanguageBusiness languageBusiness = LanguageBusinessImpl.getInstance();
	
	@Override
	public String format(Number number, Locale locale) {
		NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale);
		return numberFormatter.format(number);
	}
	
	@Override
	public String format(Number number) {
		return format(number, languageBusiness.findCurrentLocale());
	}

}
