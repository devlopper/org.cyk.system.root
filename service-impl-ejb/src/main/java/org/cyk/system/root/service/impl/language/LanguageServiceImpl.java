package org.cyk.system.root.service.impl.language;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Setter;

import org.cyk.system.root.dao.api.language.LanguageDao;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.service.api.language.LanguageService;
import org.cyk.system.root.service.impl.AbstractTypedService;

@Singleton
public class LanguageServiceImpl extends AbstractTypedService<Language, LanguageDao> implements LanguageService,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	private static Map<String,ClassLoader> RESOURCE_BUNDLE_MAP = new HashMap<>();
	
	@Setter private Locale locale = Locale.FRENCH;
	
	@Inject
	public LanguageServiceImpl(LanguageDao dao) {
		super(dao);
	} 
	
	
	@Override
	protected void initialisation() {
		super.initialisation();
		registerResourceBundle("org.cyk.system.root.model.language.i18n",getClass().getClassLoader());
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
		/*System.out.println("LanguageServiceImpl.registerResourceBundle() "+id);
		Enumeration<String> e = ResourceBundle.getBundle(id,locale).getKeys();
		System.out.println(e);
		while(e.hasMoreElements())
			System.out.println(e.nextElement());*/
		/*if(locale==null)
			throw new RuntimeException("No locale set");
		if(!locale.equals(resourceBundle.getLocale()))
			throw new RuntimeException("No same locale : "+locale+" <> "+resourceBundle.getLocale());*/
		RESOURCE_BUNDLE_MAP.put(id,aClassLoader);
	}
	
	
	protected void debug(){
		for(Entry<String, ClassLoader> entry : RESOURCE_BUNDLE_MAP.entrySet()){
			try {
				ResourceBundle resourceBundle = ResourceBundle.getBundle(entry.getKey(), locale, entry.getValue());
				Enumeration<String> e = resourceBundle.getKeys();
				System.out.println("Bundle : "+e);
				while(e.hasMoreElements())
					System.out.println(e.nextElement());
			} catch (Exception e) {}
		}
	}
	

}
