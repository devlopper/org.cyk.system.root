package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.model.value.Value;

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
    	//for(GlobalIdentifier globalIdentifier : inject(GlobalIdentifierDao.class).readAll())
    	//	System.out.println(globalIdentifier.getCode());
    	//System.out.println("Class excluded to global identifiers : "+GlobalIdentifier.EXCLUDED);
    	//System.out.println("Number of global identifiers : "+inject(GlobalIdentifierDao.class).countAll());
    	//System.out.println("Number of named queries : "+AbstractPersistenceService.NAMED_QUERIES.size());
    	
    }
    
    
}
