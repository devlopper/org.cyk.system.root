package org.cyk.system.root.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.inject.Inject;

import org.cyk.system.root.dao.api.AbstractEnumerationTreeDao;
import org.cyk.system.root.dao.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.model.EnumerationTree;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;

public abstract class AbstractEnumerationTreeDaoImpl<ENUMERATION extends EnumerationTree> extends AbstractTypedDao<ENUMERATION> implements AbstractEnumerationTreeDao<ENUMERATION>,Serializable {

	private static final long serialVersionUID = 6306356272165070761L;

	@Inject protected NestedSetNodeDao nestedSetNodeDao;
	
	/*
	 *Named Queries Identifiers Declaration 
	 */
	private String readByNodeIds;
	private String readByNodeIdsByType;
	private String readByType; 
	
	@Override
	protected void namedQueriesInitialisation() {
		super.namedQueriesInitialisation();
		registerNamedQuery(readByNodeIds, _select()+"WHERE r.node.identifier IN :ids");
		registerNamedQuery(readByNodeIdsByType, _select()+"WHERE r.node.identifier IN :ids AND r.type = :rtype");
		registerNamedQuery(readByType, _select()+"WHERE r.type = :rtype");
	}
		
	@Override
	public Collection<ENUMERATION> readByParent(ENUMERATION parent) {
		Collection<NestedSetNode> nodes = nestedSetNodeDao.readByParent(parent.getNode());
		if(nodes.isEmpty())
			return new HashSet<>();
		return namedQuery(readByNodeIds).parameter("ids", ids(nodes)).resultMany();
	}
	
	@Override
	public Collection<ENUMERATION> readByParentByType(ENUMERATION parent, EnumerationTree type) {
		Collection<NestedSetNode> nodes = nestedSetNodeDao.readByParent(parent.getNode());
		if(nodes.isEmpty())
			return new HashSet<>();
		return namedQuery(readByNodeIdsByType).parameter("ids", ids(nodes)).parameter("rtype", type).resultMany();
	}
	
	@Override
	public Collection<ENUMERATION> readByType(EnumerationTree type) {
		return namedQuery(readByType).parameter("rtype", type).resultMany();
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
	
}
