package org.cyk.system.root.business.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.AbstractEnumeration;

public interface AbstractDataTreeNodeBusiness<ENUMERATION extends AbstractEnumeration> extends AbstractEnumerationBusiness<ENUMERATION> {
    
    void findHierarchy(ENUMERATION anEnumeration);
    
    Collection<ENUMERATION> findHierarchies();
    Collection<ENUMERATION> findByParent(ENUMERATION parent);
    
    void move(ENUMERATION anEnumeration,ENUMERATION parent);
    
    ENUMERATION findParent(ENUMERATION child);
    
    Boolean isAncestorOf(ENUMERATION ancestor,ENUMERATION child);
    Boolean isAtLeastOneAncestorOf(Collection<ENUMERATION> ancestors,ENUMERATION child);

    ENUMERATION instanciateOne(String parentCode,String code,String name);
    
}
