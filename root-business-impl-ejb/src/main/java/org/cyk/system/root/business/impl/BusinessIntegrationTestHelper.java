package org.cyk.system.root.business.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.BusinessService;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.business.impl.pattern.tree.DataTreeTypeBusinessImpl;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.system.root.business.impl.validation.ValidationPolicyImpl;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.persistence.api.language.LanguageDao;
import org.cyk.system.root.persistence.api.pattern.tree.DataTreeDao;
import org.cyk.system.root.persistence.api.pattern.tree.DataTreeTypeDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.persistence.impl.PersistenceIntegrationTestHelper;
import org.cyk.system.root.persistence.impl.language.LanguageDaoImpl;
import org.cyk.system.root.persistence.impl.pattern.tree.DataTreeDaoImpl;
import org.cyk.system.root.persistence.impl.pattern.tree.DataTreeTypeDaoImpl;
import org.cyk.system.root.persistence.impl.pattern.tree.NestedSetNodeDaoImpl;


public class BusinessIntegrationTestHelper {

    
	@SuppressWarnings("unchecked")
    public static Class<?>[] BASE_CLASSES = ArrayUtils.addAll(PersistenceIntegrationTestHelper.BASE_CLASSES,
	        BusinessService.class,AbstractBusinessService.class,GenericBusiness.class,GenericBusinessServiceImpl.class,ValidationPolicy.class,ValidationPolicyImpl.class,
	        LanguageBusiness.class,LanguageBusinessImpl.class,LanguageDao.class,LanguageDaoImpl.class,
	        NestedSet.class,NestedSetNode.class,AbstractEnumeration.class,AbstractDataTreeNode.class,NestedSetNodeDao.class,NestedSetNodeDaoImpl.class,
	        DataTreeType.class,AbstractDataTree.class,DataTreeDao.class,DataTreeDaoImpl.class,DataTreeTypeBusiness.class,DataTreeTypeBusinessImpl.class,
	        DataTreeTypeDao.class,DataTreeTypeDaoImpl.class,
	        ValidationPolicy.class,ValidationPolicyImpl.class,ExceptionUtils.class);
	
	
}
