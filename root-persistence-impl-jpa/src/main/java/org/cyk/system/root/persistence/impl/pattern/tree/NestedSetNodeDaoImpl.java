package org.cyk.system.root.persistence.impl.pattern.tree;

import static org.cyk.utility.common.computation.ArithmeticOperator.GT;
import static org.cyk.utility.common.computation.ArithmeticOperator.LT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

public class NestedSetNodeDaoImpl extends AbstractTypedDao<NestedSetNode> implements NestedSetNodeDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;
	 
	/* 
	 *Named Queries names 
	 */
	private String readByParent,countByParent;
	private String readBySet,countBySet;
	private String readBySetByLeftOrRightGreaterThanOrEqualTo;
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		 registerNamedQuery(readByParent, _select().where("set", "nestedSet").and("leftIndex",GT).and("leftIndex","rightIndex",LT));
		 registerNamedQuery(readBySet,_select().where("set", "nestedSet")); 
		 registerNamedQuery(readBySetByLeftOrRightGreaterThanOrEqualTo, //_select().
				 //where("set", "nestedSet").parenthesis(true).and("leftIndex","index",GTE).or("rightIndex", "index",GTE).parenthesis(false).orderBy("leftIndex")
				 "SELECT node FROM NestedSetNode node WHERE node.set=:nestedSet AND (node.leftIndex >= :index OR node.rightIndex >= :index) ORDER BY node.leftIndex"
				);
	}
	
	@Override
	public Collection<NestedSetNode> readByParent(NestedSetNode parent) {
		return namedQuery(readByParent).parameter("nestedSet", parent.getSet()).parameter("leftIndex", parent.getLeftIndex()).parameter("rightIndex", parent.getRightIndex())
				.resultMany();
	}
	@Override
	public Long countByParent(NestedSetNode parent) {
		return countNamedQuery(countByParent).parameter("nestedSet", parent.getSet()).parameter("leftIndex", parent.getLeftIndex()).parameter("rightIndex", parent.getRightIndex())
				.resultOne();
	}
	
	@Override
	public Collection<NestedSetNode> readBySet(NestedSet set) {
		return namedQuery(readBySet).parameter("nestedSet", set).resultMany();
	}
	@Override
	public Long countBySet(NestedSet set) {
		return countNamedQuery(countBySet).parameter("nestedSet", set).resultOne();
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
			logTrace("Set {} auto created",node.getSet());
		}
		
		NestedSetNode parent = node.getParent();
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
			//node.setLeftIndex(NestedSetNode.FIRST_LEFT_INDEX);
			//node.setRightIndex(NestedSetNode.FIRST_RIGHT_INDEX);
			//entityManager.persist(node);
			//entityManager.merge(node.getSet());
			logTrace("First set node {} created",node);
		}else{
			/*NestedSetNode parent = node.getParent();
			Integer parentRightIndex = parent.getRightIndex();
			node.setLeftIndex(parentRightIndex);
			node.setRightIndex(node.getLeftIndex()+1);
			for(NestedSetNode n : readBySetByLeftOrRightGreaterThanOrEqualTo(parent.getSet(), parentRightIndex)){
				updateBoundariesGreaterThanOrEqualTo(n,true, parentRightIndex);
				entityManager.merge(n);
				logTrace("Node {} updated",node);
			}
			*/
			//entityManager.persist(node);
			logTrace("Node {} created",node);
		}
		
		entityManager.persist(node);
		return node;
	}
	
	private void computeIndexes(NestedSetNode node,Boolean isFirstNode,Collection<NestedSetNode> nestedSetNodesWhereIndexesToBeRecomputed){
		if(Boolean.TRUE.equals(isFirstNode)){//first node of the set
			node.setLeftIndex(NestedSetNode.FIRST_LEFT_INDEX);
			node.setRightIndex(NestedSetNode.FIRST_RIGHT_INDEX);
		}else{
			NestedSetNode parent = node.getParent();
			Integer parentRightIndex = parent.getRightIndex();
			node.setLeftIndex(parentRightIndex);
			node.setRightIndex(node.getLeftIndex()+1);
			for(NestedSetNode n : nestedSetNodesWhereIndexesToBeRecomputed){
				updateBoundariesGreaterThanOrEqualTo(n,true, parentRightIndex);
				logTrace("Node indexes {} recomputed",node);
			}
		}
		logTrace("Node indexes {} computed",node);
	}
	
	@Override
	public NestedSetNode delete(NestedSetNode node) {
		List<NestedSetNode> tree = new ArrayList<>(readByParent(node));
		Collections.reverse(tree);
		tree.add(node);
		logTrace("Deleting Sub tree ({}). size = {}. Elements = {}",node, tree.size(),tree);
		
		int step = tree.size()*2;
		Integer rightIndex = node.getRightIndex();
		for(NestedSetNode n : readBySetByLeftOrRightGreaterThanOrEqualTo(node.getSet(), rightIndex+1) ){
			updateBoundaries(n,-step, n.getLeftIndex()>rightIndex?null:false);//both bounds or right only
			entityManager.merge(n);
		}
		
		if(node.getParent()==null){
			node.getSet().setRoot(null);
			entityManager.merge(node.getSet());
			
		}
		
		//delete tree
		for(NestedSetNode n : tree){
			n.setParent(null);
			entityManager.remove(entityManager.merge(n));
			logTrace("Node {} deleted", n);
		}
		return node;
	}
	
	/**/
	
	private void updateBoundariesGreaterThanOrEqualTo(NestedSetNode node,Boolean increase,Integer index){
		int sign = increase?+1:-1;
		if(node.getLeftIndex()>=index)
			updateBoundaries(node, sign*2, Boolean.TRUE);
			//commonUtils.increment(Integer.class, node, NestedSetNode.FIELD_LEFT_INDEX, sign*2);
		if(node.getRightIndex()>=index)
			//commonUtils.increment(Integer.class, node, NestedSetNode.FIELD_RIGHT_INDEX, sign*2);
			updateBoundaries(node, sign*2, Boolean.FALSE);
	}
	
	private void updateBoundaries(NestedSetNode node,Integer step,Boolean left){
		if(left==null || left)
			commonUtils.increment(Integer.class, node, NestedSetNode.FIELD_LEFT_INDEX, step);
		if(left==null || !left)
			commonUtils.increment(Integer.class, node, NestedSetNode.FIELD_RIGHT_INDEX, step);
	}

}
