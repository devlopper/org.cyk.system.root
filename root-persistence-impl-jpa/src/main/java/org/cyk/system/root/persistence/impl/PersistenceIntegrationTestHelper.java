package org.cyk.system.root.persistence.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.persistence.api.GenericDao;
import org.cyk.system.root.persistence.api.PersistenceService;
import org.cyk.utility.common.CommonUtils;

public class PersistenceIntegrationTestHelper {

	private static Package[] PACKAGES = {};
	    
    //FIXME those classes are ignored. WHY 
    private static Class<?>[] CLASSES = {QueryStringBuilder.class,PersistenceService.class
    	,AbstractPersistenceService.class,GenericDao.class,GenericDaoImpl.class,QueryStringBuilder.class,PersistenceInterfaceLocator.class};
    
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
