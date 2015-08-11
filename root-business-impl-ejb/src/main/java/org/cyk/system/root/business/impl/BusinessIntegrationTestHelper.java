package org.cyk.system.root.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.impl.file.FileValidator;
import org.cyk.system.root.business.impl.party.person.PersonValidator;
import org.cyk.system.root.business.impl.validation.ExceptionUtils;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.slf4j.Logger;

public class BusinessIntegrationTestHelper {

    private static Package[] PACKAGES = {ExceptionUtils.class.getPackage()};

    //FIXME those classes are ignored. WHY 
    private static Class<?>[] CLASSES = {ExceptionUtils.class,BusinessLocator.class,PersonValidator.class,FileValidator.class,
    	RandomDataProvider.class,RootRandomDataProvider.class,ResourceProducer.class,Logger.class,RootReportRepository.class};
    
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
