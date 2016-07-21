package org.cyk.system.root.persistence.impl.pattern.tree;

import static org.cyk.utility.common.computation.ArithmeticOperator.GT;
import static org.cyk.utility.common.computation.ArithmeticOperator.LT;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractDataTreeDao;
import org.cyk.utility.common.computation.ArithmeticOperator;

public abstract class AbstractDataTreeDaoImpl<ENUMERATION extends AbstractDataTree<TYPE>,TYPE extends AbstractDataTreeType> extends AbstractDataTreeNodeDaoImpl<ENUMERATION>
	implements AbstractDataTreeDao<ENUMERATION,TYPE>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	/* 
	 *Named Queries Identifiers Declaration 
	 */

	private String readByParentByType,countByParentByType;
	private String readByType,countByType; 
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByType, _select().where("type", "rtype", ArithmeticOperator.EQ));
		registerNamedQuery(readByParentByType, _select().where("type").and("node.set", "nestedSet",ArithmeticOperator.EQ).and("node.leftIndex","leftIndex",GT).and("node.leftIndex","rightIndex",LT));
	}
		
	@Override
	public Collection<ENUMERATION> readByParentByType(ENUMERATION parent, TYPE type) {
	    NestedSetNode n = parent.getNode();
	    return namedQuery(readByParentByType).parameter("type", type).parameter("nestedSet", n.getSet())
	            .parameter("leftIndex", n.getLeftIndex()).parameter("rightIndex", n.getRightIndex())
                .resultMany();
	}
	@Override
	public Long countByParentByType(ENUMERATION parent, TYPE type) {
	    NestedSetNode n = parent.getNode();
        return countNamedQuery(countByParentByType).parameter("type", type).parameter("nestedSet", n.getSet())
                .parameter("leftIndex", n.getLeftIndex()).parameter("rightIndex", n.getRightIndex())
                .resultOne();
	}
	
	@Override
	public Collection<ENUMERATION> readByType(TYPE type) {
		return namedQuery(readByType).parameter("rtype", type).resultMany();
	}
	@Override
	public Long countByType(TYPE type) {
		return countNamedQuery(countByType).parameter("rtype", type).resultOne();
	}
		
}
