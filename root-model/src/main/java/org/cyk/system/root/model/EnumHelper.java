package org.cyk.system.root.model;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.AbstractMethod;

@Setter
public class EnumHelper implements Serializable {
    
	private static final long serialVersionUID = 4330959923308332561L;

	private static EnumHelper INSTANCE = new EnumHelper();
    
    private AbstractMethod<String, Enum<?>> textMethod;
    private AbstractMethod<Locale, Enum<?>> localeGetterMethod;
    private Locale defaultLocale = Locale.FRENCH;
    
    public static EnumHelper getInstance() {
        return INSTANCE;
    }
    
    public String text(Locale locale,Enum<?> anEnum){
        if(textMethod==null){
            String rbName = anEnum.getClass().getName().toLowerCase();
            ResourceBundle resourceBundle;
            try {
                resourceBundle = ResourceBundle.getBundle(rbName, locale);
                return resourceBundle.getString("enum."+anEnum.getClass().getSimpleName().toLowerCase()+"."+anEnum.name());
            } catch (Exception e) {
                return anEnum.name();
            }
        }else{
            return textMethod.execute(anEnum);
        }
    }
    
    public String text(Enum<?> anEnum){
        return text(localeGetterMethod == null?defaultLocale==null?Locale.FRENCH:defaultLocale:localeGetterMethod.execute(anEnum), anEnum);
    }
    
    public <ENUM extends Enum<?>> ENUM getValueOf(Class<ENUM> enumClass,Locale locale,String text,Boolean caseSensitive){
        if(StringUtils.isEmpty(text))
            return null;
        for(ENUM anEnum : enumClass.getEnumConstants())
            if(Boolean.TRUE.equals(caseSensitive)){
                if(text.equals(text(locale,anEnum)))
                    return anEnum;    
            }else{
                if(text.equalsIgnoreCase(text(locale,anEnum)))
                    return anEnum;
            }
            
        return null;
    }
    
    public <ENUM extends Enum<?>> ENUM getValueOf(Class<ENUM> enumClass,Locale locale,String text){
        return getValueOf(enumClass, locale, text, Boolean.TRUE);
    }

}
