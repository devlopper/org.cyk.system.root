package org.cyk.system.root.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.impl.file.FileValidator;
import org.cyk.system.root.business.impl.party.person.PersonValidator;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.utility.common.CommonUtils;


public class BusinessIntegrationTestHelper {

    /*
	@SuppressWarnings("unchecked")
    public static Class<?>[] BASE_CLASSES = ArrayUtils.addAll(PersistenceIntegrationTestHelper.BASE_CLASSES,
	        BusinessService.class,AbstractBusinessService.class,GenericBusiness.class,GenericBusinessServiceImpl.class,ValidationPolicy.class,ValidationPolicyImpl.class,
	        LanguageBusiness.class,LanguageBusinessImpl.class,LanguageDao.class,LanguageDaoImpl.class,
	        NestedSet.class,NestedSetNode.class,AbstractEnumeration.class,AbstractDataTreeNode.class,NestedSetNodeDao.class,NestedSetNodeDaoImpl.class,
	        DataTreeType.class,AbstractDataTree.class,DataTreeDao.class,DataTreeDaoImpl.class,DataTreeTypeBusiness.class,DataTreeTypeBusinessImpl.class,
	        DataTreeTypeDao.class,DataTreeTypeDaoImpl.class,
	        ValidationPolicy.class,ValidationPolicyImpl.class,ExceptionUtils.class);
	*/
    
    private static Package[] PACKAGES = {ExceptionUtils.class.getPackage()};
	
    
    
    //FIXME those classes are ignored. WHY 
    private static Class<?>[] CLASSES = {ExceptionUtils.class,BusinessLocator.class,PersonValidator.class,FileValidator.class};
    
    public static Package[] packages(){
        return PACKAGES;
    }
    
    public static Class<?>[] classes(){
        Collection<Class<?>> l = new ArrayList<>();
        l.addAll(Arrays.asList(CLASSES));
        l.addAll(CommonUtils.getInstance().getPackageClasses("org.cyk.system.root", Object.class));
        return l.toArray(new Class<?>[]{});
    }
}
