package org.cyk.system.root.business.impl.language;

import java.io.Serializable;
import java.text.MessageFormat;
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
import org.cyk.system.root.model.EnumHelper;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.persistence.api.language.LanguageDao;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=-1)
public class LanguageBusinessImpl extends AbstractTypedBusinessService<Language, LanguageDao> implements LanguageBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;

	private static final Map<String,ClassLoader> RESOURCE_BUNDLE_MAP = new HashMap<>();
	
	private static LanguageBusiness INSTANCE;
	
	@Setter private Locale locale = Locale.FRENCH;
	 
	@Inject
	public LanguageBusinessImpl(LanguageDao dao) {
		super(dao); 
		INSTANCE = this;
	} 
	
	@Override
	protected void initialisation() {
		super.initialisation();
		registerResourceBundle("org.cyk.system.root.model.language.i18n",getClass().getClassLoader());
		registerResourceBundle("org.cyk.system.root.model.language.word",getClass().getClassLoader());
		registerResourceBundle("org.cyk.system.root.model.language.entity",getClass().getClassLoader());
		
		registerResourceBundle("org.cyk.system.root.business.impl.language.ui",getClass().getClassLoader());
		registerResourceBundle("org.cyk.system.root.business.impl.language.exception",getClass().getClassLoader());
		registerResourceBundle("org.cyk.system.root.business.impl.language.validation",getClass().getClassLoader());
		
		
	}
	
	public static LanguageBusiness getInstance() {
        return INSTANCE;
    }

	@Override
	public String findText(String code,Object[] parameters) {
		return findText(locale,code,parameters);
	}
	
	@Override
    public String findText(String code) {
	    return findText(locale,code);
	}

	@Override
	public String findText(Locale locale,String code,Object[] parameters) {
		// 1 - Lookup in database
		
		// 2 - Lookup in bundles
		
		for(Entry<String, ClassLoader> entry : RESOURCE_BUNDLE_MAP.entrySet()){
			try {
				ResourceBundle resourceBundle = ResourceBundle.getBundle(entry.getKey(), locale, entry.getValue());
				/*if(!locale.equals(resourceBundle.getLocale()))
					throw new RuntimeException("Locale has changed! No same locale : "+locale+" <> "+resourceBundle.getLocale());*/
				return parameters==null?resourceBundle.getString(code):MessageFormat.format(resourceBundle.getString(code),parameters);
			} catch (Exception e) {}
		}
		
		//3 - default
		return "##"+code+"##";
	}	
	
	@Override
    public String findText(Locale locale,String code) {
	    return findText(locale,code,null);
	}
	
	@Override
	public void registerResourceBundle(String id,ClassLoader aClassLoader) {
		RESOURCE_BUNDLE_MAP.put(id,aClassLoader);
	}
	
	@Override
	public Locale findCurrentLocale() {
	    return locale;
	}

    @Override
    public String findText(Enum<?> anEnum, Object[] parameters) {
        return findText(findCurrentLocale(), anEnum, parameters);
    }

    @Override
    public String findText(Enum<?> anEnum) {
        return findText(findCurrentLocale(), anEnum, null);
    }

    @Override
    public String findText(Locale locale, Enum<?> anEnum, Object[] parameters) {
        return EnumHelper.getInstance().text(locale, anEnum);
    }

    @Override
    public String findText(Locale locale, Enum<?> anEnum) {
        return findText(locale, anEnum, null);
    }
    
    @Override
    public String findText(Locale locale, Locale aLocale) {
    	return findText(locale,"locale."+aLocale.toString());
    }
}
