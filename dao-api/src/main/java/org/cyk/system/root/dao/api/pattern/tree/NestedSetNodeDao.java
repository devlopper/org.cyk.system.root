package org.cyk.system.root.dao.api.pattern.tree;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.dao.api.TypedIdentifiableQuery;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;

public interface NestedSetNodeDao extends TypedIdentifiableQuery<NestedSetNode> , Serializable {

	/**
	 * Get all child node of parent (direct and indirect child are returned)
	 * @param parent
	 * @return
	 */
	Collection<NestedSetNode> readByParent(NestedSetNode parent);
	
	Collection<NestedSetNode> readBySetByLeftOrRightGreaterThanOrEqualTo(NestedSet nestedSet,Integer index);
	
	/*
	
	Collection<NestedSetNode> readByNestedSetByLeftGreaterThan(NestedSet nestedSet,int index);
	
	Collection<NestedSetNode> readByNestedSetByRightGreaterThan(NestedSet nestedSet,int index);
	
	Collection<NestedSetNode> readByNestedSetByLeftGreaterThanOrRightGreaterThan(NestedSet nestedSet,int leftIndex,int rightIndex);
	
	Collection<NestedSetNode> readByNestedSetByLeftGreaterThanAndRightLessThan(NestedSet nestedSet,int leftIndex,int rightIndex);
	
	Collection<Long> countChildrenByParent(NestedSetNode parent);
	
	Collection<NestedSetNode> readChildrenByNestedSet(NestedSet set);
	
	Collection<NestedSetNode> readDirectChildrenByParent(NestedSetNode parent);
	
	Collection<NestedSetNode> readDirectChildrenByNestedSet(NestedSet set);
	
	Collection<NestedSetNode> readByNestedSetFromIndex(NestedSet set,int index);
	*/
}
