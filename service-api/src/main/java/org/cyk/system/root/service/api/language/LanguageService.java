package org.cyk.system.root.service.api.language;

import java.util.Locale;

import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.service.api.IModelService;

public interface LanguageService extends IModelService<Language> {

	String findText(String code);
	
	String findText(String code,Locale locale);
	
	void setLocale(Locale locale);
	
	void registerResourceBundle(String id,ClassLoader aClassLoader);
	
}
