package org.cyk.system.root.business.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.AbstractEnumeration;

public interface AbstractDataTreeNodeBusiness<NODE extends AbstractEnumeration> extends AbstractEnumerationBusiness<NODE> {
    
    void findHierarchy(NODE anEnumeration);
    
    Collection<NODE> findHierarchies();
    Collection<NODE> findByParent(NODE parent);
    
    void move(NODE anEnumeration,NODE parent);
    /*
    NODE findParent(NODE child);
    Collection<NODE> findParentRecursively(NODE node);
    void setParents(NODE node);
    void setParents(Collection<NODE> nodes);
    */
    Boolean isAncestorOf(NODE ancestor,NODE child);
    Boolean isAtLeastOneAncestorOf(Collection<NODE> ancestors,NODE child);

    NODE instanciateOne(String parentCode,String code,String name);
    
    
    
}
