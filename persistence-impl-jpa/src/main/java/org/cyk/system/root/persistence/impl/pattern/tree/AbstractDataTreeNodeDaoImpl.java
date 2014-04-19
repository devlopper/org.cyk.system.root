package org.cyk.system.root.persistence.impl.pattern.tree;

import static org.cyk.utility.common.computation.ArithmeticOperator.GT;
import static org.cyk.utility.common.computation.ArithmeticOperator.LT;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeNodeDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;

public abstract class AbstractDataTreeNodeDaoImpl<ENUMERATION extends AbstractDataTreeNode> extends AbstractEnumerationDaoImpl<ENUMERATION> 
	implements AbstractDataTreeNodeDao<ENUMERATION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	@Inject protected NestedSetNodeDao nestedSetNodeDao;
	
	/* 
	 *Named Queries Identifiers Declaration 
	 */
	private String readByParent,countByParent; 
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByParent, _select().where("node.set", "nestedSet").and("node.leftIndex","leftIndex",GT).and("node.leftIndex","rightIndex",LT));
	}
		
	@Override
	public Collection<ENUMERATION> readByParent(ENUMERATION parent) {
	    NestedSetNode n = parent.getNode();
	    return namedQuery(readByParent).parameter("nestedSet", n.getSet()).parameter("leftIndex", n.getLeftIndex()).parameter("rightIndex", n.getRightIndex())
                .resultMany();
	}
	
	@Override
	public Long countByParent(ENUMERATION parent) {
	    NestedSetNode n = parent.getNode();
        return countNamedQuery(countByParent).parameter("nestedSet", n.getSet()).parameter("leftIndex", n.getLeftIndex()).parameter("rightIndex", n.getRightIndex())
                .resultOne();
	}
	
	/*
	 * CRUD specialization
	 */
	
	@Override
	public ENUMERATION create(ENUMERATION enumeration) {
		if(enumeration.getNode()==null)
			enumeration.setNode(new NestedSetNode(new NestedSet(), null));
		
		if(enumeration.getNode().getIdentifier()==null)
			nestedSetNodeDao.create(enumeration.getNode());
		return super.create(enumeration);
	}
	
	@Override
	public ENUMERATION delete(ENUMERATION enumeration) {
		Collection<ENUMERATION> list = readByParent(enumeration);
		list.add(enumeration);
		NestedSetNode node = enumeration.getNode();
		for(ENUMERATION e : list)
			entityManager.remove(entityManager.merge(e));
		nestedSetNodeDao.delete(node);
		return enumeration;
	}
	
}
