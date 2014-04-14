package org.cyk.system.root.persistence.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.model.pattern.tree.AbstractEnumerationTree;
import org.cyk.system.root.model.pattern.tree.AbstractEnumerationTreeType;

public interface AbstractEnumerationTreeDao<ENUMERATION extends AbstractEnumerationTree<TYPE>,TYPE extends AbstractEnumerationTreeType> extends AbstractEnumerationNodeDao<ENUMERATION> {

	Collection<ENUMERATION> readByType(TYPE type);
	Long countByType(TYPE type);
	
	Collection<ENUMERATION> readByParentByType(ENUMERATION parent,TYPE type);
	Long countByParentByType(ENUMERATION parent,TYPE type);
	
}
