package org.cyk.system.root.dao.impl.pattern;

import javax.inject.Inject;

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

public class NestedSetDaoCrudIT extends AbstractCrudIT<NestedSet> {

	@Inject private NestedSetNodeDao nestedSetNodeDao;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return createDeployment(NestedSet.class,NestedSetNode.class,NestedSetDao.class,NestedSetDaoImpl.class,NestedSetNodeDao.class,NestedSetNodeDaoImpl.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractIdentifiable>[] entitiesToDelete() {
		return (Class<? extends AbstractIdentifiable>[]) new Class<?>[]{NestedSetNode.class,NestedSet.class};
	}
	
	@Override
	protected NestedSet sampleOne() {
		return new NestedSet();
	}
	
	@Override
	protected NestedSet _create_() {
		return new NestedSet();
	}

	@Override
	protected void _update_(NestedSet model) {
		NestedSetNode root = new NestedSetNode(model,null);
		nestedSetNodeDao.create(root);
		model.setRoot(root);
	}
	
}
