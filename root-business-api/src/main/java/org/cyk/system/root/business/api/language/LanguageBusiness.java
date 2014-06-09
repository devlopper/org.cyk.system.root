package org.cyk.system.root.business.api.language;

import java.util.Locale;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.language.Language;

public interface LanguageBusiness extends TypedBusiness<Language> {

    String findText(String code,Object[] parameters);
    
    String findText(String code);
    
	String findText(Locale locale,String code,Object[] parameters);
	
	String findText(Locale locale,String code);
	
	void setLocale(Locale locale);
	
	void registerResourceBundle(String id,ClassLoader aClassLoader);
	
}
