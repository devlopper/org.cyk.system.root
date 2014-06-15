package org.cyk.system.root.service.impl;

import javax.inject.Inject;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.pattern.tree.DataTreeDao;
import org.cyk.system.root.persistence.impl.pattern.tree.DataTreeDaoImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;

public class GeographyBusinessIT extends AbstractBusinessIT {
	   
	@Inject private LocalityBusiness localityBusiness;  
	//@Inject private LocalityTypeBusiness localityTypeBusiness;
	protected @Inject GenericBusiness genericBusiness;
	
	@Deployment 
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{Locality.class,LocalityType.class,NestedSet.class,NestedSetNode.class,AbstractEnumeration.class,AbstractDataTreeNode.class,
		        DataTreeType.class,AbstractDataTree.class,DataTreeDao.class,DataTreeDaoImpl.class}).getArchive();
	}
		 
	@Override
    protected void populate() {
	   
    }
	
    @Override
    protected void create() {
        LocalityType t1 = new LocalityType(null, "C", "Continent");
        genericBusiness.create(t1);
        Assert.assertNotNull(t1.getIdentifier());
        Locality l = new Locality(null, t1, "A");
        genericBusiness.create(l);
        Assert.assertNotNull(l.getIdentifier());
    }

    @Override
    protected void read() {
        Assert.assertNotNull(localityBusiness.find("A"));
    }
    
    @Override
    protected void update() {
        Locality l =localityBusiness.find("A");
        l.setName("Dabou");
        Assert.assertEquals("Dabou", localityBusiness.find("A").getName());
    }
    
    @Override
    protected void delete() {
       genericBusiness.delete(localityBusiness.find("A"));
       Assert.assertNull(localityBusiness.find("A"));
    }
		
    @Override
    protected void finds() {
        
    }

    @Override
    protected void businesses() {
        
    }

    /**/
    
    


}
