package org.cyk.system.root.business.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.model.AbstractFormatter;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.ContentType;
import org.cyk.utility.common.Constant;

@Singleton
public class FormatterBusinessImpl extends AbstractBusinessServiceImpl implements FormatterBusiness,Serializable {

	private static final long serialVersionUID = -146387231230323629L;

	private static final Map<Class<?>, AbstractFormatter<?>> FORMATTER_MAP = new HashMap<>();
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public <T> String format(T object, ContentType contentType) {
		if(object==null)
			return Constant.EMPTY_STRING;
		AbstractFormatter<T> formatter;
		if((formatter = (AbstractFormatter<T>) FORMATTER_MAP.get(object.getClass()))==null){
			//logWarning("No formatter has been found for class {}. the toString() method will be used instead", object.getClass());
			
			/*return object instanceof AbstractIdentifiable
				?(object instanceof AbstractEnumeration
					?((AbstractEnumeration)object).getName()
					:((AbstractIdentifiable)object).getUiString())
				:object.toString();*/
			
			return object instanceof AbstractModelElement ?((AbstractModelElement)object).getUiString() : object.toString();
			
			//return object.toString();
		}
		return formatter.format(object,contentType);
	}

	@Override
	public String format(Object object) {
		return format(object,ContentType.TEXT);
	}

	@Override
	public <T> void registerFormatter(Class<T> aClass, AbstractFormatter<T> aFormatter) {
		FORMATTER_MAP.put(aClass, aFormatter);
		logInfo("formatter has been registered for class {}",aClass);
	}

}
