package org.cyk.system.root.persistence.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.persistence.api.AbstractEnumerationDao;
import org.cyk.system.root.persistence.api.TypedDao;

public interface AbstractDataTreeNodeDao<ENUMERATION extends AbstractDataTreeNode> extends TypedDao<ENUMERATION>,AbstractEnumerationDao<ENUMERATION> {

    Collection<ENUMERATION> readRoots();
    Long countRoots();
    
	Collection<ENUMERATION> readByParent(ENUMERATION parent);
	Long countByParent(ENUMERATION parent);
	
	Collection<ENUMERATION> readDirectChildrenByParent(ENUMERATION parent);
	Long countDirectChildrenByParent(ENUMERATION parent);
	
	ENUMERATION readParent(ENUMERATION node);
	Collection<ENUMERATION> readParentRecursively(ENUMERATION node);
	
	/*
	Boolean isAncestorOf(ENUMERATION parent,ENUMERATION child);
	Boolean isAtLeastOneAncestorOf(Collection<ENUMERATION> ancestors,ENUMERATION child);
	*/
}
