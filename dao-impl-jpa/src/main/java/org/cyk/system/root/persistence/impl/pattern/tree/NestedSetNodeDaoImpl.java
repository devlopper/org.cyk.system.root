package org.cyk.system.root.persistence.impl.pattern.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.impl.AbstractTypedDao;

import static org.cyk.utility.common.computation.ArithmeticOperator.*;

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
		return namedQuery(countByParent,Long.class).parameter("nestedSet", parent.getSet()).parameter("leftIndex", parent.getLeftIndex()).parameter("rightIndex", parent.getRightIndex())
				.resultOne();
	}
	
	@Override
	public Collection<NestedSetNode> readBySet(NestedSet set) {
		return namedQuery(readBySet).parameter("nestedSet", set).resultMany();
	}
	@Override
	public Long countBySet(NestedSet set) {
		return namedQuery(countBySet,Long.class).parameter("nestedSet", set).resultOne();
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
		List<NestedSetNode> tree = new ArrayList<>(readByParent(node));
		Collections.reverse(tree);
		tree.add(node);
		//System.out.println("To delete : "+tree);
		int step = tree.size()*2;
		Integer rightIndex = node.getRightIndex();
		for(NestedSetNode n : readBySetByLeftOrRightGreaterThanOrEqualTo(node.getSet(), rightIndex+1) ){
			//those are node to be updated
			//System.out.print(n+" -> ");
			n.updateBoundaries(-step, n.getLeftIndex()>rightIndex?null:false);//both bounds or right only
			//System.out.print(n+" | ");
			entityManager.merge(n);
		}
		
		if(node.getParent()==null){
			node.getSet().setRoot(null);
			entityManager.merge(node.getSet());
		}
		
		//delete tree
		for(NestedSetNode n : tree)
			entityManager.remove(entityManager.merge(n));
		return node;
	}

}
