package org.cyk.system.root.persistence.impl.integration.tree;

import javax.inject.Inject;

import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.persistence.impl.integration.AbstractPersistenceIT;
import org.cyk.utility.test.TestMethod;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class NestedSetPersistenceIT extends AbstractPersistenceIT {
	
	private static final long serialVersionUID = 5561034357231097748L;

	@Deployment
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{NestedSet.class,NestedSetNode.class,AbstractDataTreeNode.class}).getArchive();
	} 
	
	private static NestedSet setA,set2,set3;
	private static NestedSetNode a;
	private static NestedSetNode a1,a2,a3,a4;
	private static NestedSetNode a11,a12,a13,a14,a21,a22,a23,a24,a31,a32,a33,a34,a41,a42,a43,a44;
	private static NestedSetNode a111,a112,a113,a114,a121,a122,a123,a124,a131,a132,a133,a134,a141,a142,a143,a144
		,a211,a212,a213,a214,a221,a222,a223,a224,a231,a232,a233,a234,a241,a242,a243,a244
		,a311,a312,a313,a314,a321,a322,a323,a324,a331,a332,a333,a334,a341,a342,a343,a344
		,a411,a412,a413,a414,a421,a422,a423,a424,a431,a432,a433,a434,a441,a442,a443,a444;
	
	@Inject private NestedSetDao nestedSetDao;
	@Inject private NestedSetNodeDao nestedSetNodeDao;
		
	@Override
	protected void populate() {
		/*setA = new NestedSet();
		nestedSetDao.create(setA);
		set2 = new NestedSet();
		nestedSetDao.create(set2);
		nestedSetNodeDao.create(a = new NestedSetNode(setA,null));
		
		a1 = createChild(a);
		a2 = createChild(a);
		
		createChild(a1);
		
		createChild(a2);
		createChild(a2);*/
	}
	
	private void createAll(){
		
		transaction(new TestMethod() { /**
			 * 
			 */
			private static final long serialVersionUID = 9044556157930560303L;

		@Override protected void test() {
			NestedSetNode parent =  null;
			
			setA = new NestedSet();
			nestedSetDao.create(setA);
			nestedSetNodeDao.create(a = new NestedSetNode(setA,null));
			
			parent = a;
			a1 = createChild(parent);
			a2 = createChild(parent);
			a3 = createChild(parent);
			a4 = createChild(parent);
			
			parent = a1;
			a11 = createChild(parent);
			a12 = createChild(parent);
			a13 = createChild(parent);
			a14 = createChild(parent);
			
			parent = a2;
			a21 = createChild(parent);
			a22 = createChild(parent);
			a23 = createChild(parent);
			a24 = createChild(parent);
			
			parent = a3;
			a31 = createChild(parent);
			a32 = createChild(parent);
			a33 = createChild(parent);
			a34 = createChild(parent);
			
			parent = a4;
			a41 = createChild(parent);
			a42 = createChild(parent);
			a43 = createChild(parent);
			a44 = createChild(parent);
			
			parent = a11;
			a111 = createChild(parent);
			a112 = createChild(parent);
			a113 = createChild(parent);
			a114 = createChild(parent);
			
			parent = a12;
			a121 = createChild(parent);
			a122 = createChild(parent);
			a123 = createChild(parent);
			a124 = createChild(parent);
			
			parent = a13;
			a131 = createChild(parent);
			a132 = createChild(parent);
			a133 = createChild(parent);
			a134 = createChild(parent);
			
			parent = a14;
			a141 = createChild(parent);
			a142 = createChild(parent);
			a143 = createChild(parent);
			a144 = createChild(parent);
			
			parent = a21;
			a211 = createChild(parent);
			a212 = createChild(parent);
			a213 = createChild(parent);
			a214 = createChild(parent);
			
			parent = a22;
			a221 = createChild(parent);
			a222 = createChild(parent);
			a223 = createChild(parent);
			a224 = createChild(parent);
			
			parent = a23;
			a231 = createChild(parent);
			a232 = createChild(parent);
			a233 = createChild(parent);
			a234 = createChild(parent);
			
			parent = a24;
			a241 = createChild(parent);
			a242 = createChild(parent);
			a243 = createChild(parent);
			a244 = createChild(parent);
			
			parent = a31;
			a311 = createChild(parent);
			a312 = createChild(parent);
			a313 = createChild(parent);
			a314 = createChild(parent);
			
			parent = a32;
			a321 = createChild(parent);
			a322 = createChild(parent);
			a323 = createChild(parent);
			a324 = createChild(parent);
			
			parent = a33;
			a331 = createChild(parent);
			a332 = createChild(parent);
			a333 = createChild(parent);
			a334 = createChild(parent);
			
			parent = a34;
			a341 = createChild(parent);
			a342 = createChild(parent);
			a343 = createChild(parent);
			a344 = createChild(parent);
			
			parent = a41;
			a411 = createChild(parent);
			a412 = createChild(parent);
			a413 = createChild(parent);
			a414 = createChild(parent);
			
			parent = a42;
			a421 = createChild(parent);
			a422 = createChild(parent);
			a423 = createChild(parent);
			a424 = createChild(parent);
			
			parent = a43;
			a431 = createChild(parent);
			a432 = createChild(parent);
			a433 = createChild(parent);
			a434 = createChild(parent);
			
			parent = a44;
			a441 = createChild(parent);
			a442 = createChild(parent);
			a443 = createChild(parent);
			a444 = createChild(parent);
			
		} }); 
		
		
		
	}
					
	// CRUD 
	
	@Override
	protected void create() {
		//set3 = new NestedSet();
		//nestedSetDao.create(set3);
		//Assert.assertTrue("Create", nestedSetDao.read(set3.getIdentifier())!=null);
	}

	@Override
	protected void read() {
		//Assert.assertTrue("Read", nestedSetDao.read(setA.getIdentifier())!=null);
	}

	@Override
	protected void update() {
		//nestedSetNodeDao.create(new NestedSetNode(set2,null));
		//Assert.assertTrue("Update", nestedSetDao.read(set2.getIdentifier()).getRoot()!=null);
	}

	@Override
	protected void delete() {
		//nestedSetDao.delete(set3);
		//Assert.assertTrue("Delete",nestedSetDao.read(set3.getIdentifier())==null);
	}
	
	@Override
	protected void queries() {
		deleteCascade(a); 
		createAll();
		
		System.out.println(nestedSetNodeDao.readByParent(a));
		
		/*
		Collection<NestedSetNode> allNodes = nestedSetNodeDao.readBySet(setA);
		long allCount = nestedSetNodeDao.countBySet(setA);
		Assert.assertEquals("Count all in set",allCount,allNodes.size());
		
		Collection<NestedSetNode> allParentNodes = nestedSetNodeDao.readByParent(a);
		long allParentCount = nestedSetNodeDao.countByParent(a);
		Assert.assertEquals("Count all of parent",allParentCount,allParentNodes.size());
		
		deleteCascade(a2);
		*/
	}
	
	/**/
	
	private NestedSetNode createChild(NestedSetNode parent){
		return nestedSetNodeDao.create(new NestedSetNode(parent.getSet(), parent));
	}
	
	private void deleteCascade(NestedSetNode n){
		if(n==null)
			return;
		final NestedSetNode node = nestedSetNodeDao.read(n.getIdentifier());
		if(node==null)
			return;
		long allCount = nestedSetNodeDao.countBySet(node.getSet());
		long deletedCount = nestedSetNodeDao.countByParent(node)+1;
		transaction(new TestMethod() { /**
			 * 
			 */
			private static final long serialVersionUID = 9044556157930560303L;

		@Override protected void test() {nestedSetNodeDao.delete(node);} }); 
		long remainingCount = nestedSetNodeDao.countBySet(node.getSet());
		System.out.println("Delete cascade of "+node+" - all count = "+allCount+" - delete count = "+deletedCount+" - remaining count = "+remainingCount);
		Assert.assertEquals("Cascade delete",allCount-deletedCount,remainingCount);
		
	}
	
}
