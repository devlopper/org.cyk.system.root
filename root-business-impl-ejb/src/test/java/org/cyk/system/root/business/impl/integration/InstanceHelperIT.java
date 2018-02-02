package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.party.person.Sex;
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
    }

}
