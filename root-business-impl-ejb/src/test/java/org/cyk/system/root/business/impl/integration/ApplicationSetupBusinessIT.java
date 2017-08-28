package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
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
    	System.out.println(inject(LocalityBusiness.class).findByParent(inject(LocalityDao.class).read(RootConstant.Code.Locality.AFRICA)));
    	System.out.println(inject(LocalityBusiness.class).findDirectChildrenByParent(inject(LocalityDao.class).read(RootConstant.Code.Locality.AFRICA)));
    	org.cyk.system.root.model.geography.Locality africa = inject(LocalityDao.class).read(RootConstant.Code.Locality.AFRICA);
    	inject(LocalityBusiness.class).findHierarchy(africa);
    	for(AbstractIdentifiable locality : africa.getChildren()){
    		System.out.println(locality.getCode());
    	}
    	assertEquals("number of direct children of africa",Boolean.TRUE, inject(LocalityBusiness.class).findDirectChildrenByParent(inject(LocalityDao.class).read(RootConstant.Code.Locality.AFRICA)).size()>0);
    	//for(GlobalIdentifier globalIdentifier : inject(GlobalIdentifierDao.class).readAll())
    	//	System.out.println(globalIdentifier.getCode());
    	//System.out.println("Class excluded to global identifiers : "+GlobalIdentifier.EXCLUDED);
    	//System.out.println("Number of global identifiers : "+inject(GlobalIdentifierDao.class).countAll());
    	//System.out.println("Number of named queries : "+AbstractPersistenceService.NAMED_QUERIES.size());
    	
    }
    
    
}
