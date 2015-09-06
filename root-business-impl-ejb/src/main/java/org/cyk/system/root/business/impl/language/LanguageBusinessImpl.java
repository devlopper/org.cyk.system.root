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
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.business.impl.party.ApplicationBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.EnumHelper;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.persistence.api.language.LanguageDao;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.ModelBean.GenderType;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.annotation.user.interfaces.Text.ValueType;

import lombok.Getter;
import lombok.Setter;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=-1)
public class LanguageBusinessImpl extends AbstractTypedBusinessService<Language, LanguageDao> implements LanguageBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	private static final String UNKNOWN_MARKER_START = "##";
	private static final String UNKNOWN_MARKER_END = "##";
	private static final String FIELD_MARKER_START = "field.";
	private static final String FIELD_OF_FORMAT = "%s.of";
	
	private static final Set<String> FIELD_TYPE_MARKERS = new LinkedHashSet<>(Arrays.asList(".quantity",".unit.price",".price",".paid",".count"));
	private static final Map<String,ClassLoader> RESOURCE_BUNDLE_MAP = new LinkedHashMap<>();
	private static List<Entry<String, ClassLoader>> RESOURCE_BUNDLE_ENTRIES = new ArrayList<>();
	
	private static final Map<String,String> RESOURCE_BUNDLE_VALUE_CACHE = new HashMap<>();
	
	private static LanguageBusiness INSTANCE;
	
	@Setter private Locale locale = Locale.FRENCH;
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
	public String findText(String code,Object[] parameters) {
		return findText(locale,code,parameters);
	}
	
	@Override
    public String findText(String code) {
	    return findText(locale,code);
	}

	@Override
	public String findText(Locale locale,String code,Object[] parameters) {
		logTrace("Text lookup id={} , locale={}",code,locale);
		String value = null,cacheId=null;
		// 1 - Lookup in cache
		if(Boolean.TRUE.equals(cachingEnabled)){
			cacheId = locale+"_"+code+StringUtils.join(parameters);
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
    public String findText(Locale locale,String code) {
	    return findText(locale,code,null);
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
		return null;
	}
    
    @Override
    public String findClassLabelText(Class<?> aClass) {
    	if(AbstractModelElement.class.isAssignableFrom(aClass))
    		return findText("model.entity."+StringUtils.uncapitalize(aClass.getSimpleName()));
    	return findText(aClass.getName());
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
		return findText("listofsomething", new Object[]{findClassLabelText(aClass)});
	}
	
	@Override
	public String findDeterminantText(Boolean male,Boolean one,Boolean global) {
		String smale = Boolean.TRUE.equals(male)?"male":"female";
		String sone = Boolean.TRUE.equals(one)?"one":"many";
		String sglobal = Boolean.TRUE.equals(global)?"global":"notglobal";
		return findText("determinant."+smale+"."+sone+"."+sglobal);
	}
	
	@Override
	public String findDoActionText(String actionId,Class<? extends AbstractIdentifiable> aClass,Boolean one,Boolean global) {
		BusinessEntityInfos businessEntityInfos = null;
		for(BusinessEntityInfos b : ApplicationBusinessImpl.BUSINESS_ENTITIES_INFOS)
    		if(b.getClazz().equals(aClass)){
    			businessEntityInfos = b;
    			break;
    		}
		
		String determinant = findDeterminantText(GenderType.MALE.equals(businessEntityInfos.getGenderType()), one,global);
		return findText("doactionformat", new Object[]{findText(actionId),determinant,findClassLabelText(aClass)});
	}
	
	@Override
	public String findDoFunctionnalityText(Class<? extends AbstractIdentifiable> aClass,Boolean one,Boolean global) {
		return findDoActionText("dofunctionality", aClass, one, global);
	}

	@Override
	public String findDoFunctionnalityText(Class<? extends AbstractIdentifiable> aClass) {
		return findDoFunctionnalityText(aClass, Boolean.TRUE, Boolean.FALSE);
	}

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
