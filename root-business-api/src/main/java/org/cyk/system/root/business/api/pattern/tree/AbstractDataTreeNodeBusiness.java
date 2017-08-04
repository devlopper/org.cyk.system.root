package org.cyk.system.root.business.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.business.api.AbstractEnumerationBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.utility.common.computation.DataReadConfiguration;

public interface AbstractDataTreeNodeBusiness<NODE extends AbstractEnumeration> extends AbstractEnumerationBusiness<NODE> {
    
	Collection<NODE> findRoots(DataReadConfiguration readConfiguration);
	Collection<NODE> findRoots();
    Long countRoots();
	
    void findHierarchy(NODE anEnumeration);
    
    Collection<NODE> findHierarchies();
    Collection<NODE> findByParent(NODE parent);
    Collection<NODE> findByParent(NODE parent,DataReadConfiguration readConfiguration);
    
    void move(NODE anEnumeration,NODE parent);
    void move(String code,String parentCode);
    /*
    NODE findParent(NODE child);
    Collection<NODE> findParentRecursively(NODE node);
    void setParents(NODE node);
    void setParents(Collection<NODE> nodes);
    */
    Boolean isAncestorOf(NODE ancestor,NODE child);
    Boolean isAtLeastOneAncestorOf(Collection<NODE> ancestors,NODE child);

    NODE instanciateOne(String parentCode,String code,String name);
    
    NODE instanciateOne(NODE parent);
    
    Collection<NODE> findDirectChildrenByParent(NODE parent);
    Collection<NODE> findDirectChildrenByParent(NODE parent,DataReadConfiguration readConfiguration);
	Long countDirectChildrenByParent(NODE parent);
	
}
