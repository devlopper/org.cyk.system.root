package org.cyk.system.root.service.impl.integration;

import javax.inject.Inject;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.service.impl.data.PersonTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class GenericBusinessIT extends AbstractBusinessIT {
	   
	@Inject private GenericBusiness genericBusiness;  
	  
	@Deployment
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{Locality.class,LocalityType.class,NestedSet.class,NestedSetNode.class,AbstractEnumeration.class,AbstractDataTreeNode.class,
		        DataTreeType.class,AbstractDataTree.class,PersonTest.class}).getArchive();
	}
		 
	@Override
    protected void populate() {
	   
    }
	
    @Override
    protected void create() {
        genericBusiness.create(new PersonTest("M01","Tata","pion"));
        genericBusiness.create(new PersonTest("M02","Toto","paon"));
        genericBusiness.create(new PersonTest("M03","Tutu","poon"));
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
        //Assert.assertTrue("M01 Not found", genericBusiness.e);
    }

    @Override
    protected void businesses() {
        
    }

    /**/
    
    


}
