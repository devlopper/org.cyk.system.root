package org.cyk.system.root.business.api.pattern.tree;

import java.util.Collection;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;

public interface NestedSetNodeBusiness extends TypedBusiness<NestedSetNode> {
	
	Collection<NestedSetNode> readByParent(NestedSetNode parent);
	Long countByParent(NestedSetNode parent);
	
	NestedSetNode detach(NestedSetNode node);
	NestedSetNode attach(NestedSetNode node,NestedSetNode parent);
	
}
