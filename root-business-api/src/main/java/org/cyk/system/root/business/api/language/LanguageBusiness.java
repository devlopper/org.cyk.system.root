package org.cyk.system.root.business.api.language;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
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
    
    String findClassLabelText(FindClassLabelTextParameters parameters);
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
	
	String findActionIdentifierText(String actionIdentifier,BusinessEntityInfos businessEntityInfos,Boolean verb);
	
	void setCachingEnabled(Boolean aValue);
	Boolean getCachingEnabled();
	
	public enum CachingStrategy{NONE,AUTO}
	
	void setCachingStrategy(CachingStrategy aValue);
	CachingStrategy getCachingStrategy();
	
	/**/
	
	String DO_SOMETHING_PLUS_DET_FORMAT = "dosomethingformatplusdet";
	String DO_SOMETHING_PLUS_DET_PLUS_FOR_FORMAT = "dosomethingformatplusdetplusfor";
	String DO_SOMETHING_FORMAT = "dosomethingformat";
	String DO_SOMETHING_PLUS_FOR_FORMAT = "dosomethingformatplusfor";
	
	/**/
	@Getter @Setter
	public static class FindClassLabelTextParameters implements Serializable{
		private static final long serialVersionUID = 6396335973589816204L;
		private Class<?> clazz;
		private Boolean one=Boolean.TRUE;
		public FindClassLabelTextParameters(Class<?> clazz, Boolean one) {
			super();
			this.clazz = clazz;
			this.one = one;
		}
		public FindClassLabelTextParameters(Class<?> clazz) {
			this(clazz,Boolean.TRUE);
		}
	}
	
	@Getter @Setter
	public static class FindDoSomethingTextParameters implements Serializable{
		private static final long serialVersionUID = 6396335973589816204L;
		private Object actionIdentifier;
		private Class<? extends AbstractIdentifiable> subjectClass;
		private Boolean one,global,verb=Boolean.TRUE;
		private String forWhat;
		
		/**/
		
		public static FindDoSomethingTextParameters create(Object actionIdentifier,Class<? extends AbstractIdentifiable> identifiableClass,Boolean verb){
			FindDoSomethingTextParameters labelParameters = new FindDoSomethingTextParameters();
			labelParameters = new FindDoSomethingTextParameters();
			labelParameters.setActionIdentifier(actionIdentifier);
			labelParameters.setSubjectClass(identifiableClass);
			labelParameters.setVerb(verb);
			return labelParameters;
		}
		
		public static FindDoSomethingTextParameters create(Object actionIdentifier,Class<? extends AbstractIdentifiable> identifiableClass){
			return create(actionIdentifier, identifiableClass, Boolean.TRUE);
		}
	}
}
