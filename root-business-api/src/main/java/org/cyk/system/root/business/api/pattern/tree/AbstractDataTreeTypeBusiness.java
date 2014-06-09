package org.cyk.system.root.business.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;

public interface AbstractDataTreeTypeBusiness<ENUMERATION extends AbstractDataTreeType> extends AbstractDataTreeNodeBusiness<ENUMERATION> {

    void findHierarchy(ENUMERATION anEnumeration);
    
    Collection<ENUMERATION> findHierarchies();
}
