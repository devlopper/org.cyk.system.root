package org.cyk.system.root.dao.impl.tree;

import javax.inject.Inject;

import org.cyk.system.root.dao.api.AbstractEnumerationTreeDao;
import org.cyk.system.root.dao.api.EnumerationTreeDao;
import org.cyk.system.root.dao.api.TypedDao;
import org.cyk.system.root.dao.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.dao.impl.AbstractCrudIT;
import org.cyk.system.root.dao.impl.AbstractEnumerationTreeDaoImpl;
import org.cyk.system.root.dao.impl.pattern.tree.NestedSetNodeDaoImpl;
import org.cyk.system.root.model.EnumerationTree;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class EnumerationTreeDaoCrudIT extends AbstractCrudIT<EnumerationTree> {

	@Inject private EnumerationTreeDao enumerationTreeDao;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return createDeployment(NestedSet.class.getPackage(),EnumerationTree.class.getPackage(),
				NestedSetNodeDao.class.getPackage(),AbstractEnumerationTreeDao.class.getPackage(),
				NestedSetNodeDaoImpl.class.getPackage(),AbstractEnumerationTreeDaoImpl.class.getPackage());
	}
	
	@Override
	protected TypedDao<EnumerationTree> dao() {
		return enumerationTreeDao;
	}
	
	@Override
	protected EnumerationTree sampleOne() {
		return new EnumerationTree(null,"MyEnumA");
	}
	
	@Override
	protected EnumerationTree _create_() {
		return new EnumerationTree(null,"MyEnumB");
	}

	@Override
	protected void _update_(EnumerationTree model) {
		model.setLibelle("Hello");
	}
	

}
