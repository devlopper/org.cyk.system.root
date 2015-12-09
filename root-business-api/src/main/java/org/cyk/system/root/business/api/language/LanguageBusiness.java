package org.cyk.system.root.business.api.language;

import java.lang.reflect.Field;
import java.util.Locale;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.language.Language;
import org.cyk.utility.common.annotation.user.interfaces.Text;

public interface LanguageBusiness extends TypedBusiness<Language> {

    String findText(String code,Object[] parameters);
    
    String findText(String code);
    
	String findText(Locale locale,String code,Object[] parameters);
	
	String findText(Locale locale,String code);
	
	String findText(Enum<?> anEnum,Object[] parameters);
    
    String findText(Enum<?> anEnum);
    
    String findText(Locale locale,Enum<?> anEnum,Object[] parameters);
    
    String findText(Locale locale,Enum<?> anEnum);
	
    String findText(Locale locale,Locale aLocale);
	
    String findAnnotationText(Field field,Text text);
    
    String findFieldLabelText(Field field);
    
    String findClassLabelText(Class<?> aClass);
    
    String findObjectLabelText(Object object);
    
    String findListOfText(Class<?> aClass);
    
	void setLocale(Locale locale);
	
	Locale findCurrentLocale();
	
	void registerResourceBundle(String id,ClassLoader aClassLoader);

	String findDeterminantText(Boolean male, Boolean one,Boolean global);

	String findDoActionText(String actionId,Class<? extends AbstractIdentifiable> aClass, Boolean one,Boolean global);

	String findDoFunctionnalityText(Class<? extends AbstractIdentifiable> aClass,Boolean one, Boolean global);
	
	String findDoFunctionnalityText(Class<? extends AbstractIdentifiable> aClass);
	
	String findResponseText(Boolean value);
	
	void setCachingEnabled(Boolean aValue);
	Boolean getCachingEnabled();
	
	public enum CachingStrategy{NONE,AUTO}
	
	void setCachingStrategy(CachingStrategy aValue);
	CachingStrategy getCachingStrategy();
	
	/**/
	
	
	
}
