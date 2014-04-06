package org.cyk.system.root.dao.impl.pattern.tree;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.dao.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.dao.impl.AbstractTypedDao;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;


public class NestedSetNodeDaoImpl extends AbstractTypedDao<NestedSetNode> implements NestedSetNodeDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	
	/* 
	 *Named Queries Identifiers Declaration 
	 */
	private String readByParent;
	private String readBySetByLeftOrRightGreaterThanOrEqualTo;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		 registerNamedQuery(readByParent, "SELECT node FROM NestedSetNode node "
					+"WHERE node.set=:nestedSet AND node.leftIndex > :leftIndex AND node.leftIndex < :rightIndex ORDER BY node.leftIndex");
			
		 registerNamedQuery(readBySetByLeftOrRightGreaterThanOrEqualTo, 
				"SELECT node FROM NestedSetNode node WHERE node.set=:nestedSet AND (node.leftIndex >= :index OR node.rightIndex >= :index) ORDER BY node.leftIndex");
	}
	
	@Override
	public Collection<NestedSetNode> readByParent(NestedSetNode parent) {
		return namedQuery(readByParent).parameter("nestedSet", parent.getSet()).parameter("leftIndex", parent.getLeftIndex()).parameter("rightIndex", parent.getRightIndex())
				.resultMany();
	}
	
	@Override
	public Collection<NestedSetNode> readBySetByLeftOrRightGreaterThanOrEqualTo(NestedSet set,Integer index){
		return namedQuery(readBySetByLeftOrRightGreaterThanOrEqualTo).parameter("nestedSet", set).parameter("index", index)
				.resultMany();
	}
	
	/*
	 * CRUD specialization
	 */
	
	@Override
	public NestedSetNode create(NestedSetNode node) {
		if(node.getSet().getIdentifier()==null){//set not yet created
			//node.getSet().setRoot(null);
			entityManager.persist(node.getSet());
		}
			
		if(node.getSet().getRoot()==null){//first node of the set
			node.setParent(null);
			node.getSet().setRoot(node);
			node.setLeftIndex(NestedSetNode.FIRST_LEFT_INDEX);
			node.setRightIndex(NestedSetNode.FIRST_RIGHT_INDEX);
			entityManager.persist(node);
			entityManager.merge(node.getSet());
		}else{
			NestedSetNode parent = node.getParent();
			Integer parentRightIndex = parent.getRightIndex();
			node.setLeftIndex(parentRightIndex);
			node.setRightIndex(node.getLeftIndex()+1);
			for(NestedSetNode n : readBySetByLeftOrRightGreaterThanOrEqualTo(parent.getSet(), parentRightIndex)){
				//System.out.print("Updating "+n+" to ");
				n.updateBoundariesGreaterThanOrEqualTo(true, parentRightIndex);
				//System.out.println(n);
				entityManager.merge(n);
			}
			entityManager.persist(node);
		}
		return node;
	}
	
	@Override
	public NestedSetNode delete(NestedSetNode node) {
		node.getSet().setRoot(null);
		
		node.setParent(null);
		node.setSet(null);
		
		return super.delete(node);
	}

}
