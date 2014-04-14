package org.cyk.system.root.persistence.impl.pattern.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import org.cyk.system.root.model.pattern.tree.AbstractEnumerationTree;
import org.cyk.system.root.model.pattern.tree.AbstractEnumerationTreeType;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractEnumerationTreeDao;
import org.cyk.utility.common.computation.ArithmeticOperator;

public abstract class AbstractEnumerationTreeDaoImpl<ENUMERATION extends AbstractEnumerationTree<TYPE>,TYPE extends AbstractEnumerationTreeType> extends AbstractEnumerationNodeDaoImpl<ENUMERATION>
	implements AbstractEnumerationTreeDao<ENUMERATION,TYPE>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	/* 
	 *Named Queries Identifiers Declaration 
	 */

	private String readByNodeIdsByType/*,countParentByType*/;
	private String readByType,countByType; 
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByNodeIdsByType, _select().where("node.identifier", "ids",ArithmeticOperator.IN).and("type", "rtype", ArithmeticOperator.EQ));
		registerNamedQuery(readByType, _select().where("type", "rtype", ArithmeticOperator.EQ));
		//registerNamedQuery(countParentByType, _select());
	}
		
	@Override
	public Collection<ENUMERATION> readByParentByType(ENUMERATION parent, TYPE type) {
		Collection<NestedSetNode> nodes = nestedSetNodeDao.readByParent(parent.getNode());
		if(nodes.isEmpty())
			return new HashSet<>();
		return namedQuery(readByNodeIdsByType).parameter("ids", ids(nodes)).parameter("rtype", type).resultMany();
	}
	@Override
	public Long countByParentByType(ENUMERATION parent, TYPE type) {
		return new Long(readByParentByType(parent, type).size());
	}
	
	@Override
	public Collection<ENUMERATION> readByType(TYPE type) {
		return namedQuery(readByType).parameter("rtype", type).resultMany();
	}
	@Override
	public Long countByType(TYPE type) {
		return namedQuery(countByType,Long.class).parameter("rtype", type).resultOne();
	}
		
}
