package org.cyk.system.root.business.api.validation;

import java.lang.reflect.Field;

import org.cyk.system.root.model.Identifiable;

public interface ValidationPolicy {
    
    void validateCreate(Identifiable<?> anIdentifiable);
    
    void validateRead(Identifiable<?> anIdentifiable);
    
    void validateUpdate(Identifiable<?> anIdentifiable);
    
    void validateDelete(Identifiable<?> anIdentifiable);
    
    void validateField(Field field,Object value,Object...crossValues);
}
