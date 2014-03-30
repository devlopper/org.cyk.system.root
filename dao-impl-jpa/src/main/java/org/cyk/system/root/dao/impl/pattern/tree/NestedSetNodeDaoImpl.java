package org.cyk.system.root.dao.impl.pattern.tree;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.dao.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.dao.impl.AbstractTypedDao;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;

public class NestedSetNodeDaoImpl extends AbstractTypedDao<NestedSetNode> implements NestedSetNodeDao,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	@Override
	public NestedSetNode create(NestedSetNode node) {
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
	
	public static final String READ_BY_PARENT = "NestedSetNode.readByParent";
	@Override
	public Collection<NestedSetNode> readByParent(NestedSetNode parent) {
		return query(READ_BY_PARENT,QueryType.NAMED_JPQL)
				.queryParameter("nestedSet", parent.getSet())
				.queryParameter("leftIndex", parent.getLeftIndex())
				.queryParameter("rightIndex", parent.getRightIndex())
				.queryResultMany();
	}
	
	public static final String FIND_BY_NESTEDSET_BY_LEFTORRIGHTGREATERTHANOREQUALTO = "NestedSetNode.findByNestedSetByLeftOrRightGreaterThanOrEqualTo";
	public Collection<NestedSetNode> readBySetByLeftOrRightGreaterThanOrEqualTo(NestedSet set,Integer index){
		return query("SELECT node FROM NestedSetNode node WHERE node.set=:nestedSet AND (node.leftIndex >= :index OR node.rightIndex >= :index) "
				+ "ORDER BY node.leftIndex",QueryType.JPQL)
				.queryParameter("nestedSet", set)
				.queryParameter("index", index)
		.queryResultMany();
		//return getListResult(namedQuery(FIND_BY_NESTEDSET_BY_LEFTORRIGHTGREATERTHANOREQUALTO), "nestedSet",nestedSet,"index",index);
	}
	
	

}
