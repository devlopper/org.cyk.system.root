package org.cyk.system.root.dao.impl.pattern;

import javax.inject.Inject;

import org.cyk.system.root.dao.api.TypedDao;
import org.cyk.system.root.dao.api.pattern.tree.NestedSetDao;
import org.cyk.system.root.dao.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.dao.impl.AbstractCrudIT;
import org.cyk.system.root.dao.impl.pattern.tree.NestedSetDaoImpl;
import org.cyk.system.root.dao.impl.pattern.tree.NestedSetNodeDaoImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class NestedSetNodeDaoCrudIT extends AbstractCrudIT<NestedSetNode> {

	@Inject private NestedSetNodeDao nestedSetNodeDao;
	//private NestedSet set1,set2;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return createDeployment(NestedSet.class,NestedSetNode.class,NestedSetDao.class,NestedSetDaoImpl.class,NestedSetNodeDao.class,NestedSetNodeDaoImpl.class);
	}
	
	@Override
	protected TypedDao<NestedSetNode> dao() {
		return nestedSetNodeDao;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractIdentifiable>[] entitiesToDelete() {
		return (Class<? extends AbstractIdentifiable>[]) new Class<?>[]{NestedSetNode.class,NestedSet.class};
	}
	
	@Override
	protected NestedSetNode sampleOne() {
		NestedSet set1 = (NestedSet) genericDao.create(NestedSet.class, new NestedSet());
		return new NestedSetNode(set1,null);
	}
	
	@Override
	protected NestedSetNode _create_() {
		NestedSet set1 = (NestedSet) genericDao.create(NestedSet.class, new NestedSet());
		return new NestedSetNode(set1,null);
	}

	@Override
	protected void _update_(NestedSetNode model) {
		NestedSet set1 = (NestedSet) genericDao.create(NestedSet.class, new NestedSet());
		model.setParent(new NestedSetNode(set1,null));
	}
	

}
