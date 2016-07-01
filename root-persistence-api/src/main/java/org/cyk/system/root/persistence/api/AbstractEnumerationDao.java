package org.cyk.system.root.persistence.api;

import java.util.Collection;

import org.cyk.system.root.model.AbstractEnumeration;

public interface AbstractEnumerationDao<ENUMERATION extends AbstractEnumeration> extends TypedDao<ENUMERATION> {

    ENUMERATION read(String code);
    Collection<ENUMERATION> read(Collection<String> codes);
}
