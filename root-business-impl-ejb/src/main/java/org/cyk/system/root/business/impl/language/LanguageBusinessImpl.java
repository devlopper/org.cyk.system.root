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

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.party.ApplicationBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.language.LanguageEntry;
import org.cyk.system.root.persistence.api.language.LanguageDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.ModelBean;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.annotation.user.interfaces.Text.ValueType;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=-1)
public class LanguageBusinessImpl extends AbstractTypedBusinessService<Language, LanguageDao> implements LanguageBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	private static final String UNKNOWN_MARKER_START = "##";
	private static final String UNKNOWN_MARKER_END = "##";
	private static final String FIELD_MARKER_START = "field.";
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
	    return findText(locale,code);
	}
	
	@Override
    public String findText(String code) {
	    return findText(code,getCaseType());
	}

	public static String buildCacheIdentifier(Locale locale,String code,Object[] parameters,CaseType caseType){
		return locale+Constant.CHARACTER_UNDESCORE.toString()+code+StringUtils.join(parameters)+Constant.CHARACTER_UNDESCORE.toString()+caseType;
	}
	
	public static void cache(Locale locale,String code,Object[] parameters,CaseType caseType,String value){
		RESOURCE_BUNDLE_VALUE_CACHE.put(buildCacheIdentifier(locale, code, parameters, caseType), value);
	}
	public static void cache(Class<? extends AbstractIdentifiable> identifiableClass,String one,String many){
		cache(LOCALE, buildEntityLabelIdentifier(identifiableClass), null, CaseType.FURL, one);
	}
	
	@Override
	public String findText(Locale locale,String code,Object[] parameters,CaseType caseType) {
		logTrace("Text lookup id={} , locale={}",code,locale);
		String value = null,cacheId=null;
		// 1 - Lookup in cache
		if(Boolean.TRUE.equals(cachingEnabled)){
			cacheId = buildCacheIdentifier(locale, code, parameters, caseType);
			logTrace("Lookup in cache firstly");
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
			
			if(value==null){
				// 3 - Lookup in bundles
				logTrace("Lookup in bundles");
				for(Entry<String, ClassLoader> entry : RESOURCE_BUNDLE_ENTRIES){
					try {
						ResourceBundle resourceBundle = ResourceBundle.getBundle(entry.getKey(), locale, entry.getValue());
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
							logDebug("value of {} is {}",code,value);
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
			logDebug("No match found for {}", code);
			return UNKNOWN_MARKER_START+code+UNKNOWN_MARKER_END;
		}else
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
	public String findAnnotationText(Field field,Text text) {
		ValueType type ;
		String specifiedValue = null;
		if(text==null)
			type = ValueType.VALUE; 
		else{
			specifiedValue = text.value();
			type = ValueType.AUTO.equals(text.type())?ValueType.ID:text.type();
		}
		
		if(StringUtils.isNotBlank(specifiedValue)){
			logDebug("Value {} is of type {}", specifiedValue,type);	
			if(ValueType.VALUE.equals(type))
				return specifiedValue;
		}
		
		Collection<String> values = new ArrayList<>();
		if(ValueType.ID.equals(type))
			if(StringUtils.isNotBlank(specifiedValue))
				values.add(findText(specifiedValue));
			else{
				try {
					buildFromCompleteFieldName(field, values);
				} catch (NoMatchFoundException e) {
					// 1 - build the id = field.xxx.xxx.xxx.xxx....
					StringBuilder labelId =new StringBuilder(FIELD_MARKER_START);
					for(int i=0;i<field.getName().length();i++){
						if(Character.isUpperCase(field.getName().charAt(i)))
							labelId.append('.');
						labelId.append(Character.toLowerCase(field.getName().charAt(i)));
					}
					logDebug("Build id from field {} is {}",field.getName(),labelId);
					// 2 - Try to match the built id
					String value = findText(labelId.toString());
					if(unknown(value)){
						// 1 - Attempt removing suffix
						for(String fieldMarker : FIELD_TYPE_MARKERS){
							if(fieldMarker(labelId.toString(), value, fieldMarker, values))
								break;
						}
						//2 - Attempt removing field.
						if(values.isEmpty()){
							String nvalue = findText(StringUtils.substringAfter(labelId.toString(),FIELD_MARKER_START));
							if(unknown(nvalue)){
								values.add(value);
							}else
								values.add(nvalue);
						}
						/*
						if(StringUtils.endsWith(labelId, FIELD_MARKER_QUANTITY)){
							values.add(findText("quantity"));
							String newLabelId = StringUtils.substringBefore(labelId.toString(), FIELD_MARKER_QUANTITY);
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
		return StringUtils.join(values," ");
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
    	if(StringUtils.endsWith(labelId, fieldMarker)){
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
	public String findFieldLabelText(Field field) {
		if(field.getAnnotation(Input.class)!=null)
			return findAnnotationText(field,field.getAnnotation(Input.class).label());
		if(field.getAnnotation(IncludeInputs.class)!=null)
			return findAnnotationText(field,field.getAnnotation(IncludeInputs.class).label());
		return findText(field.getName());
	}
    
    public static String buildEntityLabelIdentifier(Class<?> aClass){
    	return "model.entity."+StringUtils.uncapitalize(aClass.getSimpleName());
    }
    
    @Override
    public String findClassLabelText(FindClassLabelTextParameters parameters) {
    	if(AbstractModelElement.class.isAssignableFrom(parameters.getClazz()))
    		return findText(buildEntityLabelIdentifier(parameters.getClazz())
    		+( (parameters.getOne()==null || parameters.getOne()) ? Constant.EMPTY_STRING : MANY_MARKER));
    	return findText(StringUtils.replace(parameters.getClazz().getName(), Constant.CHARACTER_DOLLAR.toString(), Constant.CHARACTER_DOT.toString()));
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
	public String findActionIdentifierText(String actionIdentifier,BusinessEntityInfos businessEntityInfos,Boolean verb){
		return findText("action"+Constant.CHARACTER_DOT+businessEntityInfos.getVarName().toLowerCase()
				+Constant.CHARACTER_DOT+actionIdentifier+Constant.CHARACTER_DOT+(Boolean.TRUE.equals(verb)?"verb":"name"));
	}
	
	@Override
	public String findDeterminantText(Boolean male,Boolean one,Boolean global) {
		String smale = Boolean.TRUE.equals(male)?"male":"female";
		String sone = Boolean.TRUE.equals(one)?"one":MANY;
		String sglobal = Boolean.TRUE.equals(global)?"global":"notglobal";
		return findText("determinant."+smale+"."+sone+"."+sglobal);
	}
	
	@Override
	public String findDoSomethingText(FindDoSomethingTextParameters parameters) {
		BusinessEntityInfos businessEntityInfos = null;
		if(ApplicationBusinessImpl.BUSINESS_ENTITIES_INFOS!=null)
			for(BusinessEntityInfos b : ApplicationBusinessImpl.BUSINESS_ENTITIES_INFOS)
	    		if(b.getClazz().equals(parameters.getSubjectClass())){
	    			businessEntityInfos = b;
	    			break;
	    		}
		
		GenderType genderType = GenderType.UNSET;
		if(businessEntityInfos==null){
			ModelBean modelBean = null;
			if(parameters.getSubjectClass()!=null)
				modelBean = parameters.getSubjectClass().getAnnotation(ModelBean.class);
			if(modelBean!=null)
				genderType = modelBean.genderType();
		}else
			genderType = businessEntityInfos.getGenderType();
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
		
		if(GenderType.UNSET.equals(genderType) || parameters.getOne()==null || parameters.getGlobal()==null)
			return findText(StringUtils.isBlank(parameters.getForWhat())?DO_SOMETHING_FORMAT:DO_SOMETHING_PLUS_FOR_FORMAT
					, new Object[]{findText(actionIdentifierAsString),findClassLabelText(new FindClassLabelTextParameters(parameters.getSubjectClass()))
							,findText("inorderto")+Constant.CHARACTER_SPACE+parameters.getForWhat()});
		
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
			return findText(StringUtils.isBlank(parameters.getForWhat())?DO_SOMETHING_PLUS_DET_FORMAT:DO_SOMETHING_PLUS_DET_PLUS_FOR_FORMAT
					, new Object[]{findText(actionIdentifierAsString),determinant,findClassLabelText(new FindClassLabelTextParameters(parameters.getSubjectClass()
							,parameters.getOne())),findText("inorderto")+Constant.CHARACTER_SPACE+parameters.getForWhat()});
	}
	
	@Override
	public String findResponseText(Boolean value) {
		if(value==null)
			return Constant.EMPTY_STRING;
		return findText(Boolean.TRUE.equals(value)?LanguageEntry.YES:LanguageEntry.NO);
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
			//logDebug("No match found for {}",id);
		}
		
	}
	
}
