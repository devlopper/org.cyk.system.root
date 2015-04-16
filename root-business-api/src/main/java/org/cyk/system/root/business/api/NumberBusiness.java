package org.cyk.system.root.business.api;

import java.util.Locale;

public interface NumberBusiness {

	String format(Number number,Locale locale);
	String format(Number number);
	
}
