package org.cyk.system.root.business.impl;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.SpecificBusinessManager;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.utility.common.annotation.Startup;
import org.cyk.utility.common.cdi.AbstractStartupBean;

@Startup
public class AbstractSpecificBusinessManagerImpl extends AbstractStartupBean implements SpecificBusinessManager,Serializable {

    @Inject protected GenericBusiness genericBusiness;
    
    @Override
    public void createInitialData() {
        genericBusiness.create(PhoneNumberType.class, new PhoneNumberType("FIXE", "Fixe"));
        genericBusiness.create(PhoneNumberType.class, new PhoneNumberType("MOBILE", "Mobile"));
    }
    
    @SuppressWarnings("unchecked")
    protected <T extends AbstractIdentifiable> T create(T anObject){
        return (T) genericBusiness.create(anObject.getClass(), anObject);
    }

}
