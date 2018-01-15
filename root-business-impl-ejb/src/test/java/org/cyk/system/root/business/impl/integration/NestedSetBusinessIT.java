package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.pattern.tree.NestedSetNodeBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.junit.Test;

public class NestedSetBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;
	
	@Override
	protected void populate() {}
	
	//@Test
	public void crudSet(){
		TestCase testCase = instanciateTestCase();
		NestedSet set = new NestedSet();
		testCase.create(set);
		
		testCase.assertCountAll(NestedSet.class, 1);
		testCase.assertCountAll(NestedSetNode.class, 1);
		testCase.assertCountAll(GlobalIdentifier.class, 1);
		
		testCase.clean();
	}
	
	//@Test
	public void crudNode(){
		TestCase testCase = instanciateTestCase();
		NestedSet set = new NestedSet();
		testCase.create(set);
		NestedSetNode node = new NestedSetNode(set,null);
		testCase.create(node);
		/*
		testCase.assertCountAll(NestedSet.class, 1);
		testCase.assertCountAll(NestedSetNode.class, 1);
		testCase.assertCountAll(GlobalIdentifier.class, 2);
		
		testCase.delete(node);
		
		testCase.assertCountAll(NestedSet.class, 0);
		testCase.assertCountAll(NestedSetNode.class, 0);
		testCase.assertCountAll(GlobalIdentifier.class, 0);
		*/
		testCase.clean();
	}
	
	@Test
	public void createAfricaSet(){
		TestCase testCase = instanciateTestCase();
		NestedSet africaLocalities = new NestedSet().setCode("AFSET");
		testCase.create(africaLocalities);
		testCase.assertNestedSet("AFSET", null);
		
		NestedSetNode africa = new NestedSetNode().setCode("AF").setSet(africaLocalities);
		testCase.create(africa);
		testCase.assertNestedSet("AFSET", "AF");
		testCase.assertNestedSetNode("AF", "AFSET", null, 0, 1,0l,0l);
		
		NestedSetNode coteDivoire = new NestedSetNode().setCode("CI").setParent(africa);
		testCase.create(coteDivoire);
		testCase.assertNestedSet("AFSET", "AF");
		testCase.assertNestedSetNode("AF", "AFSET", null, 0, 3,1l,1l);
		testCase.assertNestedSetNode("CI", "AFSET", "AF", 1, 2,0l,0l);
		
		NestedSetNode benin = new NestedSetNode().setCode("BE").setParent(africa);
		testCase.create(benin);
		testCase.assertNestedSet("AFSET", "AF");
		testCase.assertNestedSetNode("AF", "AFSET", null, 0, 5,2l,2l);
		testCase.assertNestedSetNode("CI", "AFSET", "AF", 1, 2,0l,0l);
		testCase.assertNestedSetNode("BE", "AFSET", "AF", 3, 4,0l,0l);
		
		NestedSetNode abidjan = new NestedSetNode().setCode("ABJ").setParent(coteDivoire);
		testCase.create(abidjan);
		testCase.assertNestedSet("AFSET", "AF");
		testCase.assertNestedSetNode("AF", "AFSET", null, 0, 7,2l,3l);
		testCase.assertNestedSetNode("CI", "AFSET", "AF", 1, 4,1l,1l);
		testCase.assertNestedSetNode("BE", "AFSET", "AF", 5, 6,0l,0l);
		testCase.assertNestedSetNode("ABJ", "AFSET", "CI", 2, 3,0l,0l);
		
		NestedSetNode bouake = new NestedSetNode().setCode("BK").setParent(coteDivoire);
		testCase.create(bouake);
		testCase.assertNestedSet("AFSET", "AF");
		testCase.assertNestedSetNode("AF", "AFSET", null, 0, 9,2l,4l);
		testCase.assertNestedSetNode("CI", "AFSET", "AF", 1, 6,2l,2l);
		testCase.assertNestedSetNode("BE", "AFSET", "AF", 7, 8,0l,0l);
		testCase.assertNestedSetNode("ABJ", "AFSET", "CI", 2, 3,0l,0l);
		testCase.assertNestedSetNode("BK", "AFSET", "CI", 4, 5,0l,0l);
		
		//testCase.clean();
	}
	
	//@Test
	public void crudTree(){
		TestCase testCase = instanciateTestCase();
		NestedSet africaLocalities = new NestedSet().setCode("AFSET");
		testCase.create(africaLocalities);
		testCase.assertNestedSet("AFSET", null);
		
		NestedSetNode africa = new NestedSetNode().setCode("AF").setSet(africaLocalities);
		testCase.create(africa);
		testCase.assertNestedSet("AFSET", "AF");
		testCase.assertNestedSetNode("AF", "AFSET", null, 0, 1,0l,0l);
		
		NestedSet europaLocalities = new NestedSet();
		testCase.create(europaLocalities);
		NestedSetNode europa = new NestedSetNode(europaLocalities,null);
		testCase.create(europa);
		
		NestedSet asiaLocalities = new NestedSet();
		testCase.create(asiaLocalities);
		NestedSetNode asia = new NestedSetNode(asiaLocalities,null);
		testCase.create(asia);
		
		NestedSetNode coteDivoire = new NestedSetNode().setCode("CI").setParent(africa);
		testCase.create(coteDivoire);
		
		NestedSetNode benin = new NestedSetNode().setCode("BE").setParent(africa);
		testCase.create(benin);
		
		NestedSetNode abidjan = new NestedSetNode().setCode("ABJ").setParent(coteDivoire);
		testCase.create(abidjan);
		
		NestedSetNode bouake = new NestedSetNode().setCode("BK").setParent(coteDivoire);
		testCase.create(bouake);
		
		System.out.println(africa.getLeftIndex()+" - "+africa.getRightIndex());
		System.out.println(inject(NestedSetNodeDao.class).readByParent(africa)+" ::: "+inject(NestedSetNodeDao.class).readDirectChildrenByParent(africa));
		assertEquals(4, inject(NestedSetNodeDao.class).countByParent(africa));
		assertEquals(0, inject(NestedSetNodeDao.class).countByParent(europa));
		assertEquals(0, inject(NestedSetNodeDao.class).countByParent(asia));
		
		
		//testCase.clean();
	}
}
