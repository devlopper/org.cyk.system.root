package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeGroupDao;
import org.cyk.utility.common.CommonUtils;
import org.junit.Test;

public class PersonRelationshipBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void businesses() {}

    @Test
    public void crudPersonRelationshipType(){
    	TestCase testCase = instanciateTestCase();
    	String code = "PRT001";
    	testCase.create(inject(PersonRelationshipTypeBusiness.class).instanciateOne(code, code).setType(inject(PersonRelationshipTypeGroupDao.class)
    			.read(RootConstant.Code.PersonRelationshipTypeGroup.FAMILY)) );
    	testCase.update(PersonRelationshipType.class,code, new Object[][]{new Object[]{CommonUtils.getInstance()
    			.attributePath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME),"OtherName" }});    	
    	testCase.deleteByCode(PersonRelationshipType.class,code);
    	testCase.clean();
    }
    
    //@Test
    public void crudPersonRelationshipTypeRoleName(){
    	
    }
    
    //@Test
    public void crudPersonRelationshipTypeRole(){
    	
    }
    
}
