package org.cyk.system.root.persistence.impl.integration.tree;

import java.util.Collection;

import javax.inject.Inject;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.persistence.impl.integration.AbstractPersistenceIT;
import org.cyk.utility.common.test.TestMethod;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class NestedSetIT extends AbstractPersistenceIT {
	
	@Deployment
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{NestedSet.class,NestedSetNode.class,AbstractDataTreeNode.class}).getArchive();
	} 
	
	private static NestedSet set1,set2,set3;
	private static NestedSetNode a1,a11,a12;
	
	@Inject private NestedSetDao nestedSetDao;
	@Inject private NestedSetNodeDao nestedSetNodeDao;
		
	@Override
	protected void populate() {
		set1 = new NestedSet();
		nestedSetDao.create(set1);
		set2 = new NestedSet();
		nestedSetDao.create(set2);
		nestedSetNodeDao.create(a1 = new NestedSetNode(set1,null));
		
		a11 = createChild(a1);
		a12 = createChild(a1);
		
		createChild(a11);
		
		createChild(a12);
		createChild(a12);
	}
					
	// CRUD 
	
	@Override
	protected void create() {
		set3 = new NestedSet();
		nestedSetDao.create(set3);
		Assert.assertTrue("Create", nestedSetDao.read(set3.getIdentifier())!=null);
	}

	@Override
	protected void read() {
		Assert.assertTrue("Read", nestedSetDao.read(set1.getIdentifier())!=null);
	}

	@Override
	protected void update() {
		nestedSetNodeDao.create(new NestedSetNode(set2,null));
		Assert.assertTrue("Update", nestedSetDao.read(set2.getIdentifier()).getRoot()!=null);
	}

	@Override
	protected void delete() {
		nestedSetDao.delete(set3);
		Assert.assertTrue("Delete",nestedSetDao.read(set3.getIdentifier())==null);
	}
	
	@Override
	protected void queries() {
		Collection<NestedSetNode> allNodes = nestedSetNodeDao.readBySet(set1);
		long allCount = nestedSetNodeDao.countBySet(set1);
		Assert.assertEquals("Count all in set",allCount,allNodes.size());
		
		Collection<NestedSetNode> allParentNodes = nestedSetNodeDao.readByParent(a1);
		long allParentCount = nestedSetNodeDao.countByParent(a1);
		Assert.assertEquals("Count all of parent",allParentCount,allParentNodes.size());
		
		deleteCascade(a12);
		deleteCascade(a1);
	}
	
	/**/
	
	private NestedSetNode createChild(NestedSetNode parent){
		return nestedSetNodeDao.create(new NestedSetNode(parent.getSet(), parent));
	}
	
	private void deleteCascade(final NestedSetNode node){
		long allCount = nestedSetNodeDao.countBySet(node.getSet());
		long deletedCount = nestedSetNodeDao.countByParent(node)+1;
		transaction(new TestMethod() { @Override protected void test() {nestedSetNodeDao.delete(node);} }); 
		long remainingCount = nestedSetNodeDao.countBySet(node.getSet());
		Assert.assertEquals("Cascade delete",allCount-deletedCount,remainingCount);
		
	}
	
}
