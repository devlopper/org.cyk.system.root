package org.cyk.system.root.business.api;

import org.cyk.system.root.model.AbstractEnumeration;

public interface AbstractEnumerationBusiness<ENUMERATION extends AbstractEnumeration> extends TypedBusiness<ENUMERATION> {

    ENUMERATION find(String code);
    
}
