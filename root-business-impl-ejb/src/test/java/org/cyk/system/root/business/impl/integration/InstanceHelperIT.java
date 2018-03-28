package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.junit.Test;

public class InstanceHelperIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void populate() {}
    
    @Test
    public void getLabel(){
    	TestCase testCase = instanciateTestCase();
    	Sex male = new Sex();
    	testCase.create(male);
    	assertThat("label is not null", StringHelper.getInstance().isNotBlank(InstanceHelper.getInstance().getLabel(male)));
    	testCase.clean();
    }
    
    @Test
    public void getBirthDate(){
    	TestCase testCase = instanciateTestCase();
    	Sex male = new Sex();
    	male.setBirthDateFromString("1/1/2000");
    	assertEquals(male.getBirthDate(), InstanceHelper.getInstance().getBirthDate(male));
    	testCase.clean();
    }

    @Test
    public void getDeathDate(){
    	TestCase testCase = instanciateTestCase();
    	Sex male = new Sex();
    	male.setDeathDateFromString("1/1/2000");
    	assertEquals(male.getDeathDate(), InstanceHelper.getInstance().getDeathDate(male));
    	testCase.clean();
    }
    
    @Test
    public void getIdentifiablePeriod(){
    	TestCase testCase = instanciateTestCase();
    	Movement movement = new Movement();
    	movement.set__identifiablePeriod__(new IdentifiablePeriod());
    	assertEquals(movement.get__identifiablePeriod__(), InstanceHelper.getInstance().getIdentifiablePeriod(movement));
    	testCase.clean();
    }
}
