package org.cyk.system.root.business.api;

import org.cyk.system.root.model.AbstractFormatter;
import org.cyk.system.root.model.ContentType;

/**
 * Format an object to another type. Default is string
 * @author Christian Yao Komenan
 *
 */
public interface FormatterBusiness {

    <T> String format(T object,ContentType contentType);
    
    <T> String format(T object);
    
    <T> void registerFormatter(Class<T> aClass,AbstractFormatter<T> aFormatter);
    
    /**/
    
}
