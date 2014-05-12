package org.cyk.system.root.service.impl;

import javax.inject.Inject;

import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.BusinessManagerImpl;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.language.LanguageEntry;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.PersistenceManager;
import org.cyk.system.root.persistence.impl.PersistenceManagerImpl;
import org.cyk.utility.common.annotation.Model.CrudStrategy;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class BusinessManagerIT extends AbstractBusinessIT {
	
	@Inject private BusinessManager businessManager;
 
	@Deployment
	public static Archive<?> createDeployment() {
		return deployment(new Class<?>[]{Language.class,LanguageEntry.class,Locality.class,LocalityType.class,
		        NestedSet.class,NestedSetNode.class,AbstractEnumeration.class,AbstractDataTreeNode.class,AbstractDataTreeType.class,AbstractDataTree.class,
		        BusinessManager.class,BusinessManagerImpl.class,PersistenceManager.class,PersistenceManagerImpl.class,
		        LanguageBusiness.class,LanguageBusinessImpl.class}).getArchive();
	}
		
	@Override
    protected void populate() {}
	
    @Override
    protected void create() {}

    @Override
    protected void read() {}
    
    @Override
    protected void update() {}
    
    @Override
    protected void delete() {}
	
    @Override
    protected void businesses() {
        
    }

    @Override
    protected void finds() {
        System.out.println(businessManager.findEntitiesInfos());
        System.out.println(businessManager.findEntitiesInfos(CrudStrategy.ENUMERATION));
        //Assert.assertEquals(2, businessManager.findEntitiesInfos().size());
    }

    /**/
    
    


}
