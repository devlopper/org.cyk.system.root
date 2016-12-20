package org.cyk.system.root.business.api.language;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Locale;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.language.Language;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.helper.StringHelper.CaseType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface LanguageBusiness extends AbstractEnumerationBusiness<Language> {

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
	
    String buildIdentifierFromFieldName(String fieldName);
    
    FindTextResult findAnnotationText(Field field,Text text);
    
    FindTextResult findFieldLabelText(Class<?> aClass,Field field);
    FindTextResult findFieldLabelText(Object object,Field field);
    FindTextResult findFieldLabelText(Field field);
    
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

	FindTextResult findDoSomethingText(FindDoSomethingTextParameters parameters);
	FindTextResult findDoPrintReportText(FindDoSomethingTextParameters parameters,ReportTemplate reportTemplate);
	FindTextResult findDoPrintReportText(ReportTemplate reportTemplate);
	FindTextResult findDoPrintReportText(FindDoSomethingTextParameters parameters,String reportTemplateCode);
	FindTextResult findDoPrintReportText(String reportTemplateCode);

	String findResponseText(Boolean value);
	
	FindTextResult findActionIdentifierText(String actionIdentifier,BusinessEntityInfos businessEntityInfos,Boolean verb);
	
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
	@Getter @Setter @NoArgsConstructor
	public static class FindClassLabelTextParameters implements Serializable{
		private static final long serialVersionUID = 6396335973589816204L;
		private Class<?> clazz;
		private Boolean one=Boolean.TRUE;
		private GenderType genderType;
		private FindTextResult result = new FindTextResult();
		public FindClassLabelTextParameters(Class<?> clazz, Boolean one) {
			super();
			this.clazz = clazz;
			this.one = one;
		}
		public FindClassLabelTextParameters(Class<?> clazz) {
			this(clazz,Boolean.TRUE);
		}
		
		public GenderType getGenderType(){
			if(genderType==null){
				if(this.clazz!=null){
					ModelBean modelBean = this.clazz.getAnnotation(ModelBean.class);
					if(modelBean!=null){
						genderType = modelBean.genderType();
					}
				}
				if(genderType == null){
					genderType = GenderType.UNSET;
				}
			}
			return genderType;
		}
	}
	
	@Getter @Setter 
	public static class FindDoSomethingTextParameters implements Serializable{
		private static final long serialVersionUID = 6396335973589816204L;
		private Object actionIdentifier;
		private FindClassLabelTextParameters subjectClassLabelTextParameters = new FindClassLabelTextParameters();
		private Boolean global,verb=Boolean.TRUE;
		private String forWhat;
		private FindTextResult result = new FindTextResult();
		/**/
		
		public static FindDoSomethingTextParameters create(Object actionIdentifier,Class<? extends AbstractIdentifiable> identifiableClass,Boolean verb){
			FindDoSomethingTextParameters labelParameters = new FindDoSomethingTextParameters();
			labelParameters = new FindDoSomethingTextParameters();
			labelParameters.setActionIdentifier(actionIdentifier);
			labelParameters.subjectClassLabelTextParameters.clazz = identifiableClass;
			labelParameters.setVerb(verb);
			return labelParameters;
		}
		
		public Boolean getOne() {
			return subjectClassLabelTextParameters.getOne();
		}

		public void setOne(Boolean one) {
			subjectClassLabelTextParameters.setOne(one);
		}
		
		public static FindDoSomethingTextParameters create(Object actionIdentifier,Class<? extends AbstractIdentifiable> identifiableClass){
			return create(actionIdentifier, identifiableClass, Boolean.TRUE);
		}

		
	}

	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class FindTextResult{
    	private String identifier;
    	private String value;
    }
}
