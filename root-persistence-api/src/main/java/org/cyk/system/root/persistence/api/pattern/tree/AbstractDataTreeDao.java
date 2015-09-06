package org.cyk.system.root.persistence.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;

public interface AbstractDataTreeDao<ENUMERATION extends AbstractDataTree<TYPE>,TYPE extends DataTreeType> extends AbstractDataTreeNodeDao<ENUMERATION> {

	Collection<ENUMERATION> readByType(TYPE type);
	Long countByType(TYPE type);
	
	Collection<ENUMERATION> readByParentByType(ENUMERATION parent,TYPE type);
	Long countByParentByType(ENUMERATION parent,TYPE type);
	
}
