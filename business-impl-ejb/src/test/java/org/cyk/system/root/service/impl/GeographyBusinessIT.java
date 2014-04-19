package org.cyk.system.root.service.impl;

import javax.inject.Inject;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class GeographyBusinessIT extends AbstractBusinessIT {
	
	@Inject private LocalityBusiness localityBusiness;
	@Inject private LocalityTypeBusiness localityTypeBusiness;
     
	@Deployment
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{Locality.class,LocalityType.class,NestedSet.class,NestedSetNode.class,AbstractEnumeration.class,AbstractDataTreeNode.class,
		        AbstractDataTreeType.class,AbstractDataTree.class}).getArchive();
	}
		
	@Override
    protected void populate() {
	   
    }
	
    @Override
    protected void create() {
        LocalityType t1 = new LocalityType(null, "C", "Continent");
        localityTypeBusiness.create(t1);
        Assert.assertNotNull(t1.getIdentifier());
        Locality l = new Locality(null, t1, "A");
        localityBusiness.create(l);
        Assert.assertNotNull(l.getIdentifier());
    }

    @Override
    protected void read() {
       
    }
    
    @Override
    protected void update() {
       
    }
    
    @Override
    protected void delete() {
       
    }
		
    @Override
    protected void finds() {
        
        
    }

    @Override
    protected void businesses() {
        
    }

    /**/
    
    


}
