package org.cyk.system.root.business.api.language;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.language.Language;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.helper.StringHelper.CaseType;

public interface LanguageBusiness extends TypedBusiness<Language> {

    String findText(String code,Object[] parameters,CaseType caseType);
    String findText(String code,Object[] parameters);
    
    String findText(String code,CaseType caseType);
    String findText(String code);
    
	String findText(Locale locale,String code,Object[] parameters,CaseType caseType);
	String findText(Locale locale,String code,Object[] parameters);
	
	String findText(Locale locale,String code,CaseType caseType);
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
	
	void setCaseType(CaseType caseType);
	CaseType getCaseType();
	
	Locale findCurrentLocale();
	
	void registerResourceBundle(String id,ClassLoader aClassLoader);

	String findDeterminantText(Boolean male, Boolean one,Boolean global);

	String findDoSomethingText(FindDoSomethingTextParameters parameters);

	String findResponseText(Boolean value);
	
	void setCachingEnabled(Boolean aValue);
	Boolean getCachingEnabled();
	
	public enum CachingStrategy{NONE,AUTO}
	
	void setCachingStrategy(CachingStrategy aValue);
	CachingStrategy getCachingStrategy();
	
	/**/
	
	String DO_SOMETHING_PLUS_DET_FORMAT = "dosomethingformatplusdet";
	String DO_SOMETHING_FORMAT = "dosomethingformat";
	
	/**/
	@Getter @Setter
	public static class FindDoSomethingTextParameters implements Serializable{
		private static final long serialVersionUID = 6396335973589816204L;
		private Object actionIdentifier;
		private Class<? extends AbstractIdentifiable> subjectClass;
		private Boolean one,global,verb=Boolean.TRUE;
	}
}
