package org.cyk.system.root.business.api.validation;

import org.cyk.system.root.model.Identifiable;

public interface ValidationPolicy {
    
    void validateCreate(Identifiable<?> anIdentifiable);
    
    void validateRead(Identifiable<?> anIdentifiable);
    
    void validateUpdate(Identifiable<?> anIdentifiable);
    
    void validateDelete(Identifiable<?> anIdentifiable);
    
}
