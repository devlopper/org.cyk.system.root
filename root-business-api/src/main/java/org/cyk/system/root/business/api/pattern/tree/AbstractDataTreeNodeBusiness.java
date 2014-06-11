package org.cyk.system.root.business.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.AbstractEnumeration;

public interface AbstractDataTreeNodeBusiness<ENUMERATION extends AbstractEnumeration> extends AbstractEnumerationBusiness<ENUMERATION> {
    
    void findHierarchy(ENUMERATION anEnumeration);
    
    Collection<ENUMERATION> findHierarchies();

}
