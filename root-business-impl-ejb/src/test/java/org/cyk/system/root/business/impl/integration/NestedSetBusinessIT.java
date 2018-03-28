package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.utility.common.helper.RandomHelper;
import org.junit.Test;

public class NestedSetBusinessIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;
	
	@Override
	protected void populate() {}
	
	@Test
	public void crudSet(){
		TestCase testCase = instanciateTestCase();
		String setCode = RandomHelper.getInstance().getAlphanumeric(5);
		NestedSet set = new NestedSet().setCode(setCode);
		testCase.create(set);
		testCase.assertCountAll(NestedSet.class, 1);
		testCase.assertCountAll(NestedSetNode.class, 0);
		testCase.assertCountAll(GlobalIdentifier.class, 1);
		testCase.deleteByCode(NestedSet.class, setCode);//delete called
		
		set = new NestedSet().setCode(setCode);
		testCase.create(set);
		testCase.assertCountAll(NestedSet.class, 1);
		testCase.assertCountAll(NestedSetNode.class, 0);
		testCase.assertCountAll(GlobalIdentifier.class, 1);
		//no call of delete
		
		testCase.clean();
	}
	
	@Test
	public void crudNode(){
		TestCase testCase = instanciateTestCase();
		String setCode = RandomHelper.getInstance().getAlphanumeric(5);
		NestedSet set = new NestedSet().setCode(setCode);
		testCase.create(set);
		testCase.assertCountAll(NestedSet.class, 1);
		testCase.assertCountAll(NestedSetNode.class, 0);
		testCase.assertCountAll(GlobalIdentifier.class, 1);
		testCase.assertNestedSet(setCode, null,0l);
		
		String nodeCode = RandomHelper.getInstance().getAlphanumeric(5);
		NestedSetNode node = new NestedSetNode().setSet(set).setCode(nodeCode);
		testCase.create(node);
		testCase.assertCountAll(NestedSet.class, 1);
		testCase.assertCountAll(NestedSetNode.class, 1);
		testCase.assertCountAll(GlobalIdentifier.class, 2);
		testCase.assertNestedSet(setCode, nodeCode,1l);
		testCase.assertNestedSetNode(nodeCode, setCode, null, 0, 1, 0l, 0l);
		testCase.deleteByCode(NestedSetNode.class, nodeCode);
		
		set = testCase.read(NestedSet.class, setCode);
		nodeCode = RandomHelper.getInstance().getAlphanumeric(5);
		node = new NestedSetNode().setSet(set).setCode(nodeCode);
		testCase.create(node);
		testCase.assertCountAll(NestedSet.class, 1);
		testCase.assertCountAll(NestedSetNode.class, 1);
		testCase.assertCountAll(GlobalIdentifier.class, 2);
		testCase.assertNestedSet(setCode, nodeCode,1l);
		testCase.assertNestedSetNode(nodeCode, setCode, null, 0, 1, 0l, 0l);
		
		testCase.clean();
	}
	
	@Test
	public void createAfricaSet(){
		TestCase testCase = instanciateTestCase();
		NestedSet africaLocalities = new NestedSet().setCode("AFSET");
		testCase.create(africaLocalities);
		testCase.assertNestedSet("AFSET", null,0l);
		
		NestedSetNode africa = new NestedSetNode().setCode("AF").setSet(africaLocalities);
		testCase.create(africa);
		testCase.assertNestedSet("AFSET", "AF",1l);
		testCase.assertNestedSetNode("AF", "AFSET", null, 0, 1,0l,0l);
		
		NestedSetNode coteDivoire = new NestedSetNode().setCode("CI").setParent(africa);
		testCase.create(coteDivoire);
		testCase.assertNestedSet("AFSET", "AF",2l);
		testCase.assertNestedSetNode("AF", "AFSET", null, 0, 3,1l,1l);
		testCase.assertNestedSetNode("CI", "AFSET", "AF", 1, 2,0l,0l);
		
		NestedSetNode benin = new NestedSetNode().setCode("BE").setParent(africa);
		testCase.create(benin);
		testCase.assertNestedSet("AFSET", "AF",3l);
		testCase.assertNestedSetNode("AF", "AFSET", null, 0, 5,2l,2l);
		testCase.assertNestedSetNode("CI", "AFSET", "AF", 1, 2,0l,0l);
		testCase.assertNestedSetNode("BE", "AFSET", "AF", 3, 4,0l,0l);
		
		NestedSetNode abidjan = new NestedSetNode().setCode("ABJ").setParent(coteDivoire);
		testCase.create(abidjan);
		testCase.assertNestedSet("AFSET", "AF",4l);
		testCase.assertNestedSetNode("AF", "AFSET", null, 0, 7,2l,3l);
		testCase.assertNestedSetNode("CI", "AFSET", "AF", 1, 4,1l,1l);
		testCase.assertNestedSetNode("BE", "AFSET", "AF", 5, 6,0l,0l);
		testCase.assertNestedSetNode("ABJ", "AFSET", "CI", 2, 3,0l,0l);
		
		NestedSetNode bouake = new NestedSetNode().setCode("BK").setParent(coteDivoire);
		testCase.create(bouake);
		testCase.assertNestedSet("AFSET", "AF",5l);
		testCase.assertNestedSetNode("AF", "AFSET", null, 0, 9,2l,4l);
		testCase.assertNestedSetNode("CI", "AFSET", "AF", 1, 6,2l,2l);
		testCase.assertNestedSetNode("BE", "AFSET", "AF", 7, 8,0l,0l);
		testCase.assertNestedSetNode("ABJ", "AFSET", "CI", 2, 3,0l,0l);
		testCase.assertNestedSetNode("BK", "AFSET", "CI", 4, 5,0l,0l);
		
		testCase.clean();
	}
}
