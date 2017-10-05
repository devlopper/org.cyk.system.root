package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.model.party.person.Sex;
import org.cyk.utility.common.helper.ClassHelper;
import org.junit.Test;

public class InstanciationBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Test
    public void instanciateSex(){
    	Sex male1 = new Sex();
    	Sex male2 = ClassHelper.getInstance().instanciateOne(Sex.class);
    	assertThat("gid is not null", male1.getGlobalIdentifier()==null);
    	assertThat("gid is null", male2.getGlobalIdentifier()!=null);
    }

}
