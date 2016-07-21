package org.cyk.system.root.business.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;

public interface AbstractDataTreeBusiness<ENUMERATION extends AbstractDataTree<TYPE>,TYPE extends AbstractDataTreeType> extends AbstractDataTreeNodeBusiness<ENUMERATION> {

	Collection<ENUMERATION> findByType(TYPE type);
	Long countByType(TYPE type);
	
	Collection<ENUMERATION> findByParentByType(ENUMERATION parent,TYPE type);
	Long countByParentByType(ENUMERATION parent,TYPE type);
	
}
