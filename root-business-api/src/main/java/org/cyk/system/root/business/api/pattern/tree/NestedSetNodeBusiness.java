package org.cyk.system.root.business.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;

public interface NestedSetNodeBusiness extends TypedBusiness<NestedSetNode> {
	
	Collection<NestedSetNode> readByParent(NestedSetNode parent);
	Long countByParent(NestedSetNode parent);
	
	Collection<NestedSetNode> findBySet(NestedSet set);
	Long countBySet(NestedSet set);
	
	NestedSetNode detach(NestedSetNode node);
	NestedSetNode attach(NestedSetNode node,NestedSetNode parent);
	
	Collection<NestedSetNode> findByDetachedIdentifier(String identifier);
	Long countByDetachedIdentifier(String identifier);
	
	Collection<NestedSetNode> findWhereDetachedIdentifierIsNullBySet(NestedSet set);
	Long countWhereDetachedIdentifierIsNullBySet(NestedSet set);
	
}
