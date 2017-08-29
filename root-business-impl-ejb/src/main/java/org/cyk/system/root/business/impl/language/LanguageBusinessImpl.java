package org.cyk.system.root.business.impl.language;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractEnumerationBusinessImpl;
import org.cyk.system.root.business.impl.party.ApplicationBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.CommonBusinessAction;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.persistence.api.file.report.ReportTemplateDao;
import org.cyk.system.root.persistence.api.language.LanguageDao;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.annotation.user.interfaces.Text.ValueType;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;

import lombok.Getter;
import lombok.Setter;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=-1)
public class LanguageBusinessImpl extends AbstractEnumerationBusinessImpl<Language, LanguageDao> implements LanguageBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	private static final String UNKNOWN_MARKER_START = "##";
	private static final String UNKNOWN_MARKER_END = "##";
	public static final String FIELD_MARKER_START = "field.";
	private static final String FIELD_OF_FORMAT = "%s.of";
	
	private static final String COMMON_BUSINESS_ACTION_FORMAT = "commonbusinessaction.%s.%s";
	
	private static final String DO_NOT_PROCESS_TAG = "cyk_donotprocess";
	private static final String DO_NOT_PROCESS_TAG_START = "<"+DO_NOT_PROCESS_TAG+">";
	private static final String DO_NOT_PROCESS_TAG_END = "</"+DO_NOT_PROCESS_TAG+">";
	
	private static final String SUBSTITUTE_TAG = "cyk_code";
	private static final String SUBSTITUTE_TAG_START = "<"+SUBSTITUTE_TAG+">";
	private static final String SUBSTITUTE_TAG_END = "</"+SUBSTITUTE_TAG+">";
	
	private static final String MANY = "many";
	private static final String MANY_MARKER = "."+MANY;
	
	private static final Set<String> FIELD_TYPE_MARKERS = new LinkedHashSet<>(Arrays.asList(".quantity",".unit.price",".price",".paid",".count",".index",".identifier"));
	private static final Map<String,ClassLoader> RESOURCE_BUNDLE_MAP = new LinkedHashMap<>();
	private static List<Entry<String, ClassLoader>> RESOURCE_BUNDLE_ENTRIES = new ArrayList<>();
	
	public static final Map<String,String> RESOURCE_BUNDLE_VALUE_CACHE = new HashMap<>();
	
	public static Locale LOCALE = Locale.FRENCH;
	
	private static LanguageBusiness INSTANCE;
	
	@Setter private Locale locale = LOCALE;
	@Getter @Setter private CaseType caseType = CaseType.FURL;
	@Getter @Setter private Boolean cachingEnabled = Boolean.TRUE;
	@Getter @Setter private CachingStrategy cachingStrategy = CachingStrategy.NONE;
	 
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
		registerResourceBundle("org.cyk.system.root.model.language.field",getClass().getClassLoader());
		
		registerResourceBundle("org.cyk.system.root.business.impl.language.ui",getClass().getClassLoader());
		registerResourceBundle("org.cyk.system.root.business.impl.language.misc",getClass().getClassLoader());
		registerResourceBundle("org.cyk.system.root.business.impl.language.exception",getClass().getClassLoader());
		registerResourceBundle("org.cyk.system.root.business.impl.language.validation",getClass().getClassLoader());
	}
	
	public static LanguageBusiness getInstance() {
        return INSTANCE;
    }

	@Override
	public String findText(String code,Object[] parameters,CaseType caseType) {
		return findText(locale,code,parameters,caseType);
	}
	
	@Override
	public String findText(String code,Object[] parameters) {
		return findText(code,parameters,getCaseType());
	}
	
	@Override
    public String findText(String code,CaseType caseType) {
	    return findText(locale,code,caseType);
	}
	
	@Override
    public String findText(String code) {
	    return findText(code,getCaseType());
	}

	public static String buildCacheIdentifier(Locale locale,String code,Object[] parameters,CaseType caseType){
		return locale+Constant.CHARACTER_UNDESCORE.toString()+code+(parameters==null?Constant.EMPTY_STRING:StringUtils.join(parameters))+Constant.CHARACTER_UNDESCORE.toString()+caseType;
	}
	
	public static void cache(Locale locale,String code,Object[] parameters,CaseType caseType,String value){
		RESOURCE_BUNDLE_VALUE_CACHE.put(buildCacheIdentifier(locale, code, parameters, caseType), value);
	}
	public static void cache(Class<? extends AbstractIdentifiable> identifiableClass,String one,String many){
		cache(LOCALE, buildEntityLabelIdentifier(identifiableClass), null, CaseType.FURL, one);
	}
	
	@Override
	public String findText(Locale locale,String code,Object[] parameters,CaseType caseType) {
		LogMessage.Builder logMessageBuilder = createLogMessageBuilder("find text");
		logMessageBuilder.addParameters("id",code,"locale",locale,"parameters",StringUtils.join(parameters,Constant.CHARACTER_COMA.toString()),"case",caseType);
		String value = null,cacheId=null,resourceBundleId=null;
		// 1 - Lookup in cache
		if(Boolean.TRUE.equals(cachingEnabled)){
			cacheId = buildCacheIdentifier(locale, code, parameters, caseType);
			logMessageBuilder.addParameters("Lookup in cache",Boolean.TRUE,"cache id",cacheId);
			/*
			CachingStrategy cachingStrategy = getCachingStrategy();
			if(!CachingStrategy.NONE.equals(cachingStrategy)){
				
				return null;
			}
			*/
			value = RESOURCE_BUNDLE_VALUE_CACHE.get(cacheId);
		}
		if(value==null){
			// 2 - Lookup in database
			logMessageBuilder.addParameters("Lookup in database",Boolean.TRUE);
			if(value==null){
				// 3 - Lookup in bundles
				logMessageBuilder.addParameters("Lookup in bundles",Boolean.TRUE);
				for(Entry<String, ClassLoader> entry : RESOURCE_BUNDLE_ENTRIES){
					try {
						ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleId = entry.getKey(), locale, entry.getValue());
						//logDebug("Bunble={}, Locale={}, Key={}",entry.getKey(),locale, code);
						/*if(!locale.equals(resourceBundle.getLocale()))
							throw new RuntimeException("Locale has changed! No same locale : "+locale+" <> "+resourceBundle.getLocale());*/
						value = parameters==null?resourceBundle.getString(code):MessageFormat.format(resourceBundle.getString(code),parameters);
						
						String substituteCode = null;
						while((substituteCode = StringUtils.substringBetween(value, SUBSTITUTE_TAG_START, SUBSTITUTE_TAG_END)) != null){
							value = StringUtils.replace(value, SUBSTITUTE_TAG_START+substituteCode+SUBSTITUTE_TAG_END, findText(substituteCode, CaseType.NONE));
						}
						
						if(StringUtils.startsWith(value,DO_NOT_PROCESS_TAG_START) && StringUtils.endsWith(value,DO_NOT_PROCESS_TAG_END)){
							value = StringUtils.substringBetween(value, DO_NOT_PROCESS_TAG_START, DO_NOT_PROCESS_TAG_END);
						}else{
							value = StringHelper.getInstance().applyCaseType(value, caseType);
						}
						
						if(Boolean.TRUE.equals(cachingEnabled)){
							RESOURCE_BUNDLE_VALUE_CACHE.put(cacheId, value);
						}
						break;
					} catch (Exception e) {
						//It is not in that bundle. Let try the next one
					}
				}
			}
		}
		if(value==null){
			// 4 - default
			logMessageBuilder.addParameters("Found",Boolean.FALSE);
			value = UNKNOWN_MARKER_START+code+UNKNOWN_MARKER_END;
		}else
			logMessageBuilder.addParameters("Found",Boolean.TRUE);
		if(resourceBundleId!=null)
			logMessageBuilder.addParameters("resource bundle",resourceBundleId);
		
		logMessageBuilder.addParameters("value",value);
		logTrace(logMessageBuilder);
		return value;
	}
	
	@Override
	public String findText(Locale locale,String code,Object[] parameters) {
		return findText(locale, code, parameters, getCaseType());
	}

	@Override
    public String findText(Locale locale,String code,CaseType caseType) {
	    return findText(locale,code,null,caseType);
	}
	
	@Override
	public String findText(Locale locale,String code) {
	    return findText(locale,code,getCaseType());
	}
	
	@Override
	public void registerResourceBundle(String id,ClassLoader aClassLoader) {
		RESOURCE_BUNDLE_MAP.put(id,aClassLoader);//First in First Top
		RESOURCE_BUNDLE_ENTRIES = new ArrayList<>(RESOURCE_BUNDLE_MAP.entrySet());
		//Should be Last in first Top
		Collections.reverse(RESOURCE_BUNDLE_ENTRIES);
		logTrace("Resource bundle {} registered", id);
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
        String code = "enum."+anEnum.getClass().getSimpleName().toLowerCase()+"."+anEnum.name();
        return findText(code, parameters);
    }

    @Override
    public String findText(Locale locale, Enum<?> anEnum) {
        return findText(locale, anEnum, null);
    }
    
    @Override
    public String findText(Locale locale, Locale aLocale) {
    	return findText(locale,"locale."+aLocale.toString());
    }
    
    /**/
    
    @Override
    public String buildIdentifierFromFieldName(String fieldName) {
    	return buildIdentifierFromFieldName(fieldName, FIELD_MARKER_START);
    }
    public static String buildIdentifierFromFieldName(String fieldName,String fieldMarker) {
    	StringBuilder identifier =new StringBuilder(fieldMarker);
    	identifier.append(CommonUtils.getInstance().addWordSeparatorToVariableName(fieldName, Constant.CHARACTER_DOT.toString()));
    	return identifier.toString();
    }
    
    @Override
	public FindTextResult findAnnotationText(Field field,Text text) {
		ValueType type ;
		String specifiedValue = null;
		if(text==null)
			type = ValueType.VALUE; 
		else{
			specifiedValue = text.value();
			type = ValueType.AUTO.equals(text.valueType())?ValueType.ID:text.valueType();
		}
		
		if(StringUtils.isNotBlank(specifiedValue)){
			logDebug("Value {} is of type {}", specifiedValue,type);	
			if(ValueType.VALUE.equals(type))
				return new FindTextResult(null,specifiedValue);
		}
		FindTextResult findTextResult = new FindTextResult();
		Collection<String> values = new ArrayList<>();
		if(ValueType.ID.equals(type))
			if(StringUtils.isNotBlank(specifiedValue)){
				findTextResult.setIdentifier(specifiedValue);
				values.add(findText(findTextResult.getIdentifier()));
			}else{
				try {
					buildFromCompleteFieldName(field, values);
				} catch (NoMatchFoundException e) {
					// 1 - build the id = field.xxx.xxx.xxx.xxx....
					String labelId = buildIdentifierFromFieldName(field.getName());
					findTextResult.setIdentifier(labelId);
					logDebug("Build id from field {} is {}",field.getName(),labelId);
					// 2 - Try to match the built id
					String value = findText(labelId);
					if(unknown(value)){
						// 1 - Attempt removing suffix
						for(String fieldMarker : FIELD_TYPE_MARKERS){
							if(fieldMarker(labelId, value, fieldMarker, values))
								break;
						}
						//2 - Attempt removing field.
						if(values.isEmpty()){
							String nvalue = findText(StringUtils.substringAfter(labelId,FIELD_MARKER_START));
							if(unknown(nvalue)){
								values.add(value);
							}else
								values.add(nvalue);
						}
						/*
						if(StringUtils.endsWith(labelId, FIELD_MARKER_QUANTITY)){
							values.add(findText("quantity"));
							String newLabelId = StringUtils.substringBefore(labelId, FIELD_MARKER_QUANTITY);
							value = findText(newLabelId);
							if(unknown(value)){
								newLabelId = StringUtils.substringAfter(newLabelId.toString(), FIELD_MARKER_START);
								values.add(findText(newLabelId));
							}else
								values.add(value);
						}else
							values.add(value);
							*/
					}else{
						values.add(value);
						
					}					
				}					
			}
		findTextResult.setValue(StringUtils.join(values," "));
		return findTextResult;
	}
    
    private String buildFromCompleteFieldName(Field field,Collection<String> values) throws NoMatchFoundException{
    	String id = field.getDeclaringClass().getName()+"."+field.getName();
    	logTrace("Path : Build id from field {} is {}",field.getName(),id);
    	return fetch(id, values);
    }
    /*
    private String buildFromFieldPrefix(Field field,Collection<String> values) throws NoMatchFoundException{
    	StringBuilder labelId =new StringBuilder(FIELD_MARKER_START);
		for(int i=0;i<field.getName().length();i++){
			if(Character.isUpperCase(field.getName().charAt(i)))
				labelId.append('.');
			labelId.append(Character.toLowerCase(field.getName().charAt(i)));
		}
		logTrace("Custom : Build id from field {} is {}",field.getName(),labelId);
		return fetch(labelId.toString(), values);
    }*/
    
    private String fetch(String id,Collection<String> values) throws NoMatchFoundException {
    	String result = findText(id);
    	if(unknown(result))
    		throw new NoMatchFoundException(id,result);
    	values.add(result);
    	return result;
    }
    
    private Boolean fieldMarker(String labelId,String value,String fieldMarker,Collection<String> values){
    	if(StringUtils.endsWith(labelId, fieldMarker) && !(StringUtils.substringBefore(labelId, fieldMarker)+Constant.CHARACTER_DOT).equals(FIELD_MARKER_START)){
    		String newLabelId = StringUtils.substringBefore(labelId, fieldMarker);
			value = findText(newLabelId);
			if(unknown(value)){
				newLabelId = StringUtils.substringAfter(newLabelId, FIELD_MARKER_START);
				value = findText(newLabelId);
			}else
				;
			values.add(findText(String.format(FIELD_OF_FORMAT, fieldMarker.substring(1)),new Object[]{value}));
			return Boolean.TRUE;
		}else
			return Boolean.FALSE;
    }
    
    private Boolean unknown(String value){
    	return StringUtils.startsWith(value,UNKNOWN_MARKER_START) && StringUtils.endsWith(value,UNKNOWN_MARKER_END);
    }
	
    @Override
	public FindTextResult findFieldLabelText(Field field) {
		if(field.getAnnotation(Input.class)!=null)
			return findAnnotationText(field,field.getAnnotation(Input.class).label());
		if(field.getAnnotation(IncludeInputs.class)!=null)
			return findAnnotationText(field,field.getAnnotation(IncludeInputs.class).label());
		FindTextResult findTextResult = new FindTextResult(field.getName(), findText(field.getName()));
		return findTextResult;
	}
    
    @Override
    public FindTextResult findFieldLabelText(final Class<?> aClass, final Field field) {
    	ListenerUtils.getInstance().execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.beforeFindFieldLabelText(aClass, field);
			}
		});
    	final FindTextResult findTextResult = findFieldLabelText(field);
    	ListenerUtils.getInstance().execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.afterFindFieldLabelText(aClass, field,findTextResult);
			}
		});
    	return findTextResult;
    }
    
    @Override
    public FindTextResult findFieldLabelText(final Object object, final Field field) {
    	ListenerUtils.getInstance().execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.beforeFindFieldLabelText(object, field);
			}
		});
    	final FindTextResult findTextResult = object == null ? findFieldLabelText(field) : findFieldLabelText(object.getClass(),field);
    	ListenerUtils.getInstance().execute(Listener.COLLECTION, new ListenerUtils.VoidMethod<Listener>() {
			@Override
			public void execute(Listener listener) {
				listener.afterFindFieldLabelText(object, field,findTextResult);
			}
		});
    	return findTextResult;
    }
    
    public static String buildEntityLabelIdentifier(Class<?> aClass){
    	return "model.entity."+StringUtils.uncapitalize(aClass.getSimpleName());
    }
    
    @Override
    public String findClassLabelText(FindClassLabelTextParameters parameters) {
    	//if(StringUtils.isBlank(parameters.getResult().getValue())){
    		if(AbstractModelElement.class.isAssignableFrom(parameters.getClazz()))
    			parameters.getResult().setValue(findText(buildEntityLabelIdentifier(parameters.getClazz())
    					+( (parameters.getOne()==null || parameters.getOne()) ? Constant.EMPTY_STRING : MANY_MARKER)));
    		else
    			parameters.getResult().setValue(findText(StringUtils.replace(parameters.getClazz().getName(), Constant.CHARACTER_DOLLAR.toString()
    				, Constant.CHARACTER_DOT.toString())));
    	//}
    	return parameters.getResult().getValue();
    }
    
    @Override
    public String findClassLabelText(Class<?> aClass) {
    	return findClassLabelText(new FindClassLabelTextParameters(aClass));
    }
    
    @Override
    public String findObjectLabelText(Object object) {
    	if(object==null)
    		return "##NULL FOUND##";
    	if(object instanceof AbstractIdentifiable)
			return ((AbstractIdentifiable)object).getUiString();
		return object.toString();
    }

	@Override
	public String findListOfText(Class<?> aClass) {
		return findText("listofsomething", new Object[]{findClassLabelText(new FindClassLabelTextParameters(aClass, Boolean.FALSE))});
	}
	
	@Override
	public FindTextResult findActionIdentifierText(String actionIdentifier,BusinessEntityInfos businessEntityInfos,Boolean verb){
		FindTextResult findTextResult = new FindTextResult();
		findTextResult.setIdentifier("action"+Constant.CHARACTER_DOT+businessEntityInfos.getVarName().toLowerCase()
				+Constant.CHARACTER_DOT+actionIdentifier+Constant.CHARACTER_DOT+(Boolean.TRUE.equals(verb)?"verb":"name"));
		findTextResult.setValue(findText(findTextResult.getIdentifier()));
		return findTextResult;
	}
	
	@Override
	public String findDeterminantText(Boolean male,Boolean one,Boolean global) {
		String smale = Boolean.TRUE.equals(male)?"male":"female";
		String sone = Boolean.TRUE.equals(one)?"one":MANY;
		String sglobal = Boolean.TRUE.equals(global)?"global":"notglobal";
		return findText("determinant."+smale+"."+sone+"."+sglobal);
	}
	
	@Override
	public FindTextResult findDoSomethingText(FindDoSomethingTextParameters parameters) {
		FindTextResult findTextResult = new FindTextResult();
		findTextResult.setIdentifier(StringUtils.join(parameters.getActionIdentifier())+Constant.CHARACTER_DOT+parameters.getSubjectClassLabelTextParameters().getClazz().getSimpleName().toLowerCase());
		BusinessEntityInfos businessEntityInfos = null;
		if(ApplicationBusinessImpl.BUSINESS_ENTITIES_INFOS!=null)
			for(BusinessEntityInfos b : ApplicationBusinessImpl.BUSINESS_ENTITIES_INFOS)
	    		if(b.getClazz().equals(parameters.getSubjectClassLabelTextParameters().getClazz())){
	    			businessEntityInfos = b;
	    			break;
	    		}
		
		GenderType genderType = GenderType.UNSET;
		
		if(GenderType.UNSET.equals(genderType)){
			if(businessEntityInfos==null){
				/*ModelBean modelBean = null;
				if(parameters.getSubjectClassLabelTextParameters().getClazz()!=null)
					modelBean = parameters.getSubjectClassLabelTextParameters().getClazz().getAnnotation(ModelBean.class);
				if(modelBean!=null)
					genderType = modelBean.genderType();
				*/
				genderType = parameters.getSubjectClassLabelTextParameters().getGenderType();
			}else
				genderType = businessEntityInfos.getGenderType();
		}
		Object actionIdentifier = null;
		String actionIdentifierAsString = null;
		if(parameters.getActionIdentifier() instanceof Crud)
			//actionIdentifierAsString = "crud."+((Crud)parameters.getActionIdentifier()).name().toLowerCase();
			actionIdentifier=CommonBusinessAction.valueOf(((Crud)parameters.getActionIdentifier()).name());
		else
			actionIdentifier = parameters.getActionIdentifier();
		
		if(actionIdentifier instanceof CommonBusinessAction)
			actionIdentifierAsString = String.format(COMMON_BUSINESS_ACTION_FORMAT, ((CommonBusinessAction)actionIdentifier).name().toLowerCase(),
					Boolean.TRUE.equals(parameters.getVerb()) ? "verb" : "name");
		else
			actionIdentifierAsString = actionIdentifier.toString();
		
		String subject = StringUtils.defaultIfBlank(parameters.getSubjectClassLabelTextParameters().getResult().getValue()
				, findClassLabelText(parameters.getSubjectClassLabelTextParameters())); 
		
		if(GenderType.UNSET.equals(genderType) || parameters.getOne()==null || parameters.getGlobal()==null){
			findTextResult.setValue(findText(StringUtils.isBlank(parameters.getForWhat())?DO_SOMETHING_FORMAT:DO_SOMETHING_PLUS_FOR_FORMAT
					, new Object[]{findText(actionIdentifierAsString),subject
							,findText("inorderto")+Constant.CHARACTER_SPACE+parameters.getForWhat()}));
		}else{
			String determinant = findDeterminantText(GenderType.MALE.equals(genderType), parameters.getOne(),parameters.getGlobal());
			if(parameters.getGlobal())
				if(parameters.getOne())
					if(Boolean.TRUE.equals(parameters.getVerb()))
						;
					else
						determinant = findText("of")+" "+determinant;
				else
					;
			else
				if(parameters.getOne())
					if(Boolean.TRUE.equals(parameters.getVerb()))
						;
					else
						determinant = findText("ofprefix")+determinant;
				else
					;
			//if(StringUtils.isBlank(parameters.getForWhat()))	
			//	return findText(DO_SOMETHING_PLUS_DET_FORMAT, new Object[]{findText(actionIdentifierAsString),determinant
			//		,findClassLabelText(new FindClassLabelTextParameters(parameters.getSubjectClass(),parameters.getOne()))});
			//else
				findTextResult.setValue(findText(StringUtils.isBlank(parameters.getForWhat())?DO_SOMETHING_PLUS_DET_FORMAT:DO_SOMETHING_PLUS_DET_PLUS_FOR_FORMAT
						, new Object[]{findText(actionIdentifierAsString),determinant,subject,findText("inorderto")+Constant.CHARACTER_SPACE+parameters.getForWhat()}));
		}
		return findTextResult;
	}
	
	@Override
	public FindTextResult findDoPrintReportText(FindDoSomethingTextParameters parameters,ReportTemplate reportTemplate) {
		if(parameters==null){
			parameters = new FindDoSomethingTextParameters();
			parameters.getSubjectClassLabelTextParameters().setClazz(File.class);//TODO null should be possible ?
			parameters.getSubjectClassLabelTextParameters().getResult().setValue(reportTemplate.getName());
			parameters.getSubjectClassLabelTextParameters().setGenderType(reportTemplate.getGlobalIdentifier().getMale() == null ? GenderType.UNSET : 
				Boolean.TRUE.equals(reportTemplate.getGlobalIdentifier().getMale()) ? GenderType.MALE : GenderType.FEMALE);
			parameters.setActionIdentifier(CommonBusinessAction.PRINT);
			parameters.setOne(Boolean.TRUE);
			parameters.setGlobal(Boolean.FALSE);
			parameters.setVerb(Boolean.TRUE);
		}
		if(parameters.getVerb()==null)
			parameters.setVerb(Boolean.TRUE);
		return findDoSomethingText(parameters);
	}
	
	@Override
	public FindTextResult findDoPrintReportText(ReportTemplate reportTemplate) {
		return findDoPrintReportText(null, reportTemplate);
	}
	
	@Override
	public FindTextResult findDoPrintReportText(FindDoSomethingTextParameters parameters, String reportTemplateCode) {
		return findDoPrintReportText(parameters, inject(ReportTemplateDao.class).read(reportTemplateCode));
	}
	
	@Override
	public FindTextResult findDoPrintReportText(String reportTemplateCode) {
		return findDoPrintReportText(null, reportTemplateCode);
	}
	
	@Override
	public String findResponseText(Boolean value) {
		return findText(value == null ? RootConstant.Code.LanguageEntry.NOT_SPECIFIED : Boolean.TRUE.equals(value) ? RootConstant.Code.LanguageEntry.YES 
				: RootConstant.Code.LanguageEntry.NO);
	}

	/**/
	
	
	
	/**/
	
	@Getter @Setter
	private class NoMatchFoundException extends Exception implements Serializable{
		private static final long serialVersionUID = -3217343065549408057L;
		private String id,result;
		public NoMatchFoundException(String id, String result) {
			super();
			this.id = id;
			this.result = result;
		}
		
	}
	
	/**/
	
	public static interface Listener extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener<Language> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		/**/
		
		void beforeFindFieldLabelText(Object object, Field field);
		FindTextResult afterFindFieldLabelText(Object object, Field field,FindTextResult findTextResult);
		
		void beforeFindFieldLabelText(Class<?> aClass, Field field);
		FindTextResult afterFindFieldLabelText(Class<?> aClass, Field field,FindTextResult findTextResult);
		
		public static class Adapter extends org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl.Listener.Adapter.Default<Language> implements Listener,Serializable{
			private static final long serialVersionUID = 1L;
			
			/**/
			@Override
			public void beforeFindFieldLabelText(Object object, Field field) {}
			@Override
			public FindTextResult afterFindFieldLabelText(Object object,Field field, FindTextResult findTextResult) {
				return null;
			}
			
			@Override
			public void beforeFindFieldLabelText(Class<?> aClass, Field field) {}
			@Override
			public FindTextResult afterFindFieldLabelText(Class<?> aClass,Field field, FindTextResult findTextResult) {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable{
				private static final long serialVersionUID = 1L;
				
				/**/
				
				@Override
				public void beforeFindFieldLabelText(Object object, Field field) {
					super.beforeFindFieldLabelText(object, field);
				}
				
				@Override
				public FindTextResult afterFindFieldLabelText(Object object,Field field, FindTextResult findTextResult) {
					return inject(LanguageBusiness.class).findFieldLabelText(field);
				}
				
				public static class EnterpriseResourcePlanning extends Listener.Adapter.Default implements Serializable{
					private static final long serialVersionUID = 1L;
					
					/**/
					
				}
			}
		}
	}
	
	public static class BuilderOneDimensionArray extends AbstractEnumerationBusinessImpl.BuilderOneDimensionArray<Language> implements Serializable {
		private static final long serialVersionUID = 1L;

		public BuilderOneDimensionArray() {
			super(Language.class);
		}
	}
}
