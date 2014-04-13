package org.cyk.system.root.persistence.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.inject.Inject;

import org.cyk.system.root.persistence.api.AbstractEnumerationTreeDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.model.EnumerationTree;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.utility.common.computation.ArithmeticOperator;

public abstract class AbstractEnumerationTreeDaoImpl<ENUMERATION extends EnumerationTree> extends AbstractTypedDao<ENUMERATION> implements AbstractEnumerationTreeDao<ENUMERATION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	@Inject protected NestedSetNodeDao nestedSetNodeDao;
	
	/* 
	 *Named Queries Identifiers Declaration 
	 */
	private String readByNodeIds;
	private String readByNodeIdsByType/*,countParentByType*/;
	private String readByType,countByType; 
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByNodeIds, _select().where("node.identifier", "ids",ArithmeticOperator.IN) /*+"WHERE r.node.identifier IN :ids"*/);
		registerNamedQuery(readByNodeIdsByType, _select().where("node.identifier", "ids",ArithmeticOperator.IN).and("type", "rtype", ArithmeticOperator.EQ) /*+"WHERE r.node.identifier IN :ids AND r.type = :rtype"*/);
		registerNamedQuery(readByType, _select().where("type", "rtype", ArithmeticOperator.EQ) /*+"WHERE r.type = :rtype"*/);
		//registerNamedQuery(countParentByType, _select());
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
	
	@Override
	public Collection<ENUMERATION> readByParentByType(ENUMERATION parent, EnumerationTree type) {
		Collection<NestedSetNode> nodes = nestedSetNodeDao.readByParent(parent.getNode());
		if(nodes.isEmpty())
			return new HashSet<>();
		return namedQuery(readByNodeIdsByType).parameter("ids", ids(nodes)).parameter("rtype", type).resultMany();
	}
	@Override
	public Long countByParentByType(ENUMERATION parent, EnumerationTree type) {
		return new Long(readByParentByType(parent, type).size());
	}
	
	@Override
	public Collection<ENUMERATION> readByType(EnumerationTree type) {
		return namedQuery(readByType).parameter("rtype", type).resultMany();
	}
	@Override
	public Long countByType(EnumerationTree type) {
		return namedQuery(countByType,Long.class).parameter("rtype", type).resultOne();
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
