package org.cyk.system.root.business.impl.integration;

import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_FATHER;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_SON;

import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeRoleBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeRoleNameBusiness;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRoleName;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeGroupDao;
import org.cyk.utility.common.CommonUtils;
import org.junit.Test;

public class PersonRelationshipBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void businesses() {}

    @Test
    public void crudPersonRelationshipType(){
    	instanciateTestCase().crud(PersonRelationshipType.class, (PersonRelationshipType) inject(PersonRelationshipTypeBusiness.class).instanciateOne("PRT001")
    		.setType(inject(PersonRelationshipTypeGroupDao.class).read(RootConstant.Code.PersonRelationshipTypeGroup.FAMILY))
    		, new Object[][]{new Object[]{CommonUtils.getInstance().attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME),"OtherName" }});
    }
    
    @Test
    public void crudPersonRelationshipTypeRoleName(){
    	instanciateTestCase().crud(PersonRelationshipTypeRoleName.class, (PersonRelationshipTypeRoleName) inject(PersonRelationshipTypeRoleNameBusiness.class).instanciateOne("PRTRN001")
			, new Object[][]{new Object[]{CommonUtils.getInstance().attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME),"OtherName" }});
    }
    
    @Test
    public void crudPersonRelationshipTypeRole(){
    	TestCase testCase = instanciateTestCase();
    	testCase.create((PersonRelationshipType) inject(PersonRelationshipTypeBusiness.class).instanciateOne("PRT001")
        		.setType(inject(PersonRelationshipTypeGroupDao.class).read(RootConstant.Code.PersonRelationshipTypeGroup.FAMILY)));
    	testCase.create((PersonRelationshipTypeRoleName) inject(PersonRelationshipTypeRoleNameBusiness.class).instanciateOne("PRTRN001"));
    	
    	testCase.crud(PersonRelationshipTypeRole.class, (PersonRelationshipTypeRole) inject(PersonRelationshipTypeRoleBusiness.class)
    			.instanciateOne("PRT001","PRTRN001")
			, new Object[][]{new Object[]{CommonUtils.getInstance().attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME),"OtherName" }});	
    
    }
    
    @Test
    public void crudPersonRelationship(){
    	TestCase testCase = instanciateTestCase();
    	String person1Code = testCase.createOnePersonRandomly("p1").getCode();
    	String person2Code = testCase.createOnePersonRandomly("p2").getCode();
    	
    	testCase.crud(PersonRelationship.class, (PersonRelationship) inject(PersonRelationshipBusiness.class)
    		.instanciateOne(person1Code,FAMILY_PARENT_FATHER,person2Code,FAMILY_PARENT_SON)
			, new Object[][]{new Object[]{CommonUtils.getInstance().attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME),"OtherName" }});	
    	
    }
    
    @Test
    public void businessPersonRelationship(){
    	TestCase testCase = instanciateTestCase();
    	testCase.createFamilyPersonRelationship("FATHER01F1","MOTHER01F1", new String[]{"SON01F1","SON02F1"}, new String[]{"DAUGHTER01F1"});//Family 1
    	
    	/*
    	testCase.createManyPersonRandomly(new String[]{"FATHER01F2","MOTHER01F2","SON01F2","DAUGHTER01F2","DAUGHTER02F2"});//Family 2
    	testCase.createManyPersonRandomly(new String[]{"FATHER01F3","MOTHER01F3"});//Family 3
    	testCase.createManyPersonRandomly(new String[]{"FATHER01F4","DAUGHTER01F4"});//Family 4
    	testCase.createManyPersonRandomly(new String[]{"MOTHER01F5","SON01F5"});//Family 5
    	testCase.createManyPersonRandomly(new String[]{"SON01F6","DAUGHTER01F6","DAUGHTER02F6"});//Family 6
    	*/
    	
    	
    	testCase.clean();
    }
    
}
