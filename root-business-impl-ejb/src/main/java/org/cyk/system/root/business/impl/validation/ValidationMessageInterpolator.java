package org.cyk.system.root.business.impl.validation;

import java.util.Locale;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.utility.common.Constant;

public class ValidationMessageInterpolator implements MessageInterpolator {
	
    private static final String PREFIX_MESSAGE_JAVAX = "{"+Constant.PREFIX_PACKAGE_BEAN_VALIDATION;
    private static final String MESSAGE_CUSTOM_START = "{";
    private static final String MESSAGE_CUSTOM_END = "}";
    
    public static Locale LOCALE = Locale.FRENCH;
    
    private LanguageBusiness languageBusiness = LanguageBusinessImpl.getInstance();
    private MessageInterpolator defaultInterpolator;
    
    public ValidationMessageInterpolator() {
        defaultInterpolator = Validation.byDefaultProvider().configure().getDefaultMessageInterpolator();
    }
    
	public String interpolate(String message, Context context) {
		return interpolate(message, context, /*languageBusiness.findCurrentLocale()*/LOCALE);
	}

	public String interpolate(String message, Context context, Locale locale) {
	    if(StringUtils.startsWith(message, PREFIX_MESSAGE_JAVAX))
	        return defaultInterpolator.interpolate(message, context, locale);
	    if(message.startsWith(MESSAGE_CUSTOM_START) && message.endsWith(MESSAGE_CUSTOM_END))
            return languageBusiness.findText(locale,message.substring(1, message.length()-1));
        return message;
	}
}
