package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.persistence.api.geography.LocalityDao;

public class ApplicationSetupBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Override
    protected void populate() {
    	GlobalIdentifier.EXCLUDED.add(NestedSet.class);
    	GlobalIdentifier.EXCLUDED.add(NestedSetNode.class);
    	GlobalIdentifier.EXCLUDED.add(Value.class);
    	super.populate();
    }
    
    @Override
    protected void businesses() {
    	assertEquals("number of direct children of africa",Boolean.TRUE, inject(LocalityBusiness.class).findDirectChildrenByParent(inject(LocalityDao.class).read(RootConstant.Code.Locality.AFRICA)).size()>0);
    }
    
    
}
