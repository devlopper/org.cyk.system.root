package org.cyk.system.root.business.impl.language;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Setter;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.persistence.api.language.LanguageDao;

@Singleton
public class LanguageBusinessImpl extends AbstractTypedBusinessService<Language, LanguageDao> implements LanguageBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	private static final Map<String,ClassLoader> RESOURCE_BUNDLE_MAP = new HashMap<>();
	
	@Setter private Locale locale = Locale.FRENCH;
	 
	@Inject
	public LanguageBusinessImpl(LanguageDao dao) {
		super(dao); 
	} 
	
	
	@Override
	protected void initialisation() {
		super.initialisation();
		registerResourceBundle("org.cyk.system.root.model.language.i18n",getClass().getClassLoader());
		registerResourceBundle("org.cyk.system.root.model.language.word",getClass().getClassLoader());
		registerResourceBundle("org.cyk.system.root.business.impl.language.ui",getClass().getClassLoader());
	}

	@Override
	public String findText(String code) {
		return findText(code, locale);
	}

	@Override
	public String findText(String code, Locale locale) {
		// 1 - Lookup in database
		
		// 2 - Lookup in bundles
		
		for(Entry<String, ClassLoader> entry : RESOURCE_BUNDLE_MAP.entrySet()){
			try {
				ResourceBundle resourceBundle = ResourceBundle.getBundle(entry.getKey(), locale, entry.getValue());
				/*if(!locale.equals(resourceBundle.getLocale()))
					throw new RuntimeException("Locale has changed! No same locale : "+locale+" <> "+resourceBundle.getLocale());*/
				return resourceBundle.getString(code);
			} catch (Exception e) {}
		}
		
		//3 - default
		return "##"+code+"##";
	}	
	
	@Override
	public void registerResourceBundle(String id,ClassLoader aClassLoader) {
		RESOURCE_BUNDLE_MAP.put(id,aClassLoader);
	}
	
}
