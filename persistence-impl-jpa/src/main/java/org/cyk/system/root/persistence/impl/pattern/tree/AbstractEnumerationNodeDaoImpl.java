package org.cyk.system.root.persistence.impl.pattern.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.inject.Inject;

import org.cyk.system.root.model.pattern.tree.AbstractEnumerationNode;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.AbstractEnumerationNodeDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.persistence.impl.AbstractEnumerationDaoImpl;
import org.cyk.utility.common.computation.ArithmeticOperator;

public abstract class AbstractEnumerationNodeDaoImpl<ENUMERATION extends AbstractEnumerationNode> extends AbstractEnumerationDaoImpl<ENUMERATION> 
	implements AbstractEnumerationNodeDao<ENUMERATION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	@Inject protected NestedSetNodeDao nestedSetNodeDao;
	
	/* 
	 *Named Queries Identifiers Declaration 
	 */
	private String readByNodeIds; 
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByNodeIds, _select().where("node.identifier", "ids",ArithmeticOperator.IN));
	}
		
	@Override
	public Collection<ENUMERATION> readByParent(ENUMERATION parent) {
		Collection<NestedSetNode> nodes = nestedSetNodeDao.readByParent(parent.getNode());
		if(nodes.isEmpty())
			return new HashSet<>();
		return namedQuery(readByNodeIds).parameter("ids", ids(nodes)).resultMany();
	}
	
	@Override
	public Long countByParent(ENUMERATION parent) {
		return nestedSetNodeDao.countByParent(parent.getNode());
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
