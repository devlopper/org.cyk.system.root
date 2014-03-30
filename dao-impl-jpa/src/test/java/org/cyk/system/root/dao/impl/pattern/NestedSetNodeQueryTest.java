package org.cyk.system.root.dao.impl.pattern;

import javax.inject.Inject;

import org.cyk.system.root.dao.api.TypedIdentifiableQuery;
import org.cyk.system.root.dao.api.pattern.tree.NestedSetDao;
import org.cyk.system.root.dao.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.dao.impl.AbstractQueryTest;
import org.cyk.system.root.dao.impl.pattern.tree.NestedSetNodeDaoImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class NestedSetNodeQueryTest extends AbstractQueryTest<NestedSetNode> {
	
	@Inject private NestedSetNodeDao dao;
	private NestedSet set1;
	private NestedSetNode a1,a11,a12;

	@Deployment
	public static Archive<?> createDeployment() {
		return createDeployment(NestedSet.class.getPackage(),NestedSetDao.class.getPackage(),NestedSetNodeDaoImpl.class.getPackage());
	}
	
	@Override
	protected TypedIdentifiableQuery<NestedSetNode> dao() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends AbstractIdentifiable>[] entitiesToDelete() {
		return (Class<? extends AbstractIdentifiable>[]) new Class<?>[]{NestedSet.class,NestedSetNode.class};
	}
		
	@Override
	protected void populate() {
		set1 = new NestedSet();
		create(set1);
		create(a1 = new NestedSetNode(set1, null));
		//System.out.println(dao.select().all());
		
		a11 = createChild(a1);
		a12 = createChild(a1);
		
		createChild(a11);
		
		createChild(a12);
		createChild(a12);
		
	}
	
	@Override
	protected void create(AbstractIdentifiable object) {
		if(object instanceof NestedSetNode)
			dao.create((NestedSetNode) object);
		else
			super.create(object);
	}
	
	@Test
	public void set1Size1() {
		Assert.assertTrue(dao.readByParent(a1).size()==5);
	}
	
	
	/**/
	
	private NestedSetNode createChild(NestedSetNode parent){
		NestedSetNode node = new NestedSetNode(parent.getSet(), parent);
		create(node);
		//System.out.println(dao.select().all());
		return node;
	}

}
