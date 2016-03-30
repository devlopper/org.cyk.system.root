package org.cyk.system.root.business.impl.pattern.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.cyk.system.root.business.api.pattern.tree.NestedSetNodeBusiness;
import org.cyk.system.root.business.impl.AbstractTypedBusinessService;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;

@Stateless
public class NestedSetNodeBusinessImpl extends AbstractTypedBusinessService<NestedSetNode, NestedSetNodeDao> implements NestedSetNodeBusiness,Serializable {

	private static final long serialVersionUID = -3799482462496328200L;
	
	@Inject
	public NestedSetNodeBusinessImpl(NestedSetNodeDao dao) {
		super(dao); 
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Collection<NestedSetNode> readByParent(NestedSetNode parent) {
		return dao.readByParent(parent);
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public Long countByParent(NestedSetNode parent) {
		return dao.countByParent(parent);
	}

	@Override
	public NestedSetNode detach(NestedSetNode node) {
		List<NestedSetNode> nodes = new ArrayList<>(dao.readByParent(node));
		nodes.add(0, node);
		System.out.println("NODES : "+nodes);
		for(NestedSetNode n : nodes){
			n.setParent(null);
			n.setLeftIndex(-1);
			n.setRightIndex(-1);
			dao.update(n);
		}
		return node;
	}

	@Override
	public NestedSetNode attach(NestedSetNode node, NestedSetNode parent) {
		/*NestedSetNode oldParent = node.getParent();
		node.setParent(newParent);
		
		Integer parentRightIndex = null;
		Collection<NestedSetNode> nestedSetNodesWhereIndexesToBeRecomputed = null;
		if(parent==null)
			;
		else{
			parentRightIndex = parent.getRightIndex();
			nestedSetNodesWhereIndexesToBeRecomputed = readBySetByLeftOrRightGreaterThanOrEqualTo(parent.getSet(), parentRightIndex);
			logTrace("Node indexes to be recomputed. Count = {} , List = {}", nestedSetNodesWhereIndexesToBeRecomputed.size(),nestedSetNodesWhereIndexesToBeRecomputed);
		}
		
		computeIndexes(node, node.getSet().getRoot()==null, nestedSetNodesWhereIndexesToBeRecomputed);
			
		if(node.getSet().getRoot()==null){//first node of the set
			node.setParent(null);
			node.getSet().setRoot(node);
			logTrace("First set node {} created",node);
		}else{
			
			logTrace("Node {} created",node);
		}
		
		entityManager.persist(node);
		*/
		return node;
	}
		
}
