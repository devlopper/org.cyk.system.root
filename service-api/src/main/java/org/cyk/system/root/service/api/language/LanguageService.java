package org.cyk.system.root.service.api.language;

import java.util.Locale;

import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.service.api.IModelService;

public interface LanguageService extends IModelService<Language> {

	String i18n(String code,Locale locale);
	
}
