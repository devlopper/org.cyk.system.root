package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.junit.Test;

public class NestedSetBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;
	
	@Override
	protected void populate() {}
	
	@Test
	public void crudSet(){
		TestCase testCase = instanciateTestCase();
		NestedSet set = new NestedSet();
		testCase.create(set);
		testCase.clean();
	}
	
	@Test
	public void crudNode(){
		TestCase testCase = instanciateTestCase();
		NestedSet set = new NestedSet();
		testCase.create(set);
		NestedSetNode node = new NestedSetNode(set,null);
		testCase.create(node);
		testCase.clean();
	}
	
	//@Test
	public void crudTree(){
		TestCase testCase = instanciateTestCase();
		NestedSet localities = new NestedSet();
		testCase.create(localities);
		NestedSetNode africa = new NestedSetNode(localities,null);
		testCase.create(africa);
		
		NestedSetNode europa = new NestedSetNode(localities,null);
		testCase.create(europa);
		
		NestedSetNode asia = new NestedSetNode(localities,null);
		testCase.create(asia);
		
		NestedSetNode coteDivoire = new NestedSetNode(localities,africa);
		testCase.create(coteDivoire);
		
		NestedSetNode benin = new NestedSetNode(localities,africa);
		testCase.create(benin);
		
		NestedSetNode abidjan = new NestedSetNode(localities,coteDivoire);
		testCase.create(abidjan);
		
		NestedSetNode bouake = new NestedSetNode(localities,coteDivoire);
		testCase.create(bouake);
		
		assertEquals(1, inject(NestedSetNodeDao.class).countByParent(africa));
		assertEquals(1, inject(NestedSetNodeDao.class).countByParent(europa));
		assertEquals(1, inject(NestedSetNodeDao.class).countByParent(asia));
		
		//testCase.clean();
	}
}
