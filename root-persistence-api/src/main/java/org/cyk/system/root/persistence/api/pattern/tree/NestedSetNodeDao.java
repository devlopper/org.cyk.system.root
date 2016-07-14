package org.cyk.system.root.persistence.api.pattern.tree;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.TypedDao;

public interface NestedSetNodeDao extends TypedDao<NestedSetNode> , Serializable {

	/**
	 * Get all child node of parent (direct and indirect child are returned)
	 * @param parent 
	 * @return
	 */
	Collection<NestedSetNode> readByParent(NestedSetNode parent);
	Long countByParent(NestedSetNode parent);
	
	Collection<NestedSetNode> readBySetByLeftOrRightGreaterThanOrEqualTo(NestedSet nestedSet,Integer index);

	Collection<NestedSetNode> readBySet(NestedSet set);
	Long countBySet(NestedSet set);
	
	Collection<NestedSetNode> readWhereDetachedIdentifierIsNullBySet(NestedSet set);
	Long countWhereDetachedIdentifierIsNullBySet(NestedSet set);
	
	Collection<NestedSetNode> readByDetachedIdentifier(String identifier);
	Long countByDetachedIdentifier(String identifier);
	
	//void incrementRightIndex(Collection<Long> identifiers,Long increment);
	void incrementLeftIndex(Collection<NestedSetNode> nestedSetNodes,Long increment);
	void incrementRightIndex(Collection<NestedSetNode> nestedSetNodes,Long increment);
	
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
