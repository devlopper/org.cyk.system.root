package org.cyk.system.root.business.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.model.pattern.tree.DataTreeType;

public interface AbstractDataTreeTypeBusiness<ENUMERATION extends DataTreeType> extends AbstractDataTreeNodeBusiness<ENUMERATION> {

    void findHierarchy(ENUMERATION anEnumeration);
    
    Collection<ENUMERATION> findHierarchies();
}
