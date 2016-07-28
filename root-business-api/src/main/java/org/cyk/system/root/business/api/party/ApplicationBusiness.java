package org.cyk.system.root.business.api.party;

import java.util.Collection;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.security.ApplicationPropertiesProvider;
import org.cyk.system.root.business.api.security.ShiroConfigurator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.generator.ValueGenerator;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.Party.PartySearchCriteria;
import org.cyk.system.root.model.security.Installation;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

public interface ApplicationBusiness extends AbstractPartyBusiness<Application,PartySearchCriteria> {
	
	void install(Installation installation);
	
	Application findCurrentInstance();
    
    Collection<BusinessEntityInfos> findBusinessEntitiesInfos();
    
    BusinessEntityInfos findBusinessEntityInfos(Class<? extends AbstractIdentifiable> aClass);
    BusinessEntityInfos findBusinessEntityInfos(String identifier);
    
    Collection<BusinessEntityInfos> findBusinessEntitiesInfos(CrudStrategy crudStrategy);
    
    /* Security */
    
    ApplicationPropertiesProvider findPropertiesProvider();
    void setPropertiesProvider(ApplicationPropertiesProvider anApplicationPropertiesProvider);
    
    ShiroConfigurator findShiroConfigurator();
    void setShiroConfigurator(ShiroConfigurator aShiroConfigurator);
    
    void configureShiro();
    
    void registerValueGenerator(ValueGenerator<?, ?> valueGenerator);
    ValueGenerator<?, ?> findValueGenerator(String identifier);
    
    <INPUT,OUTPUT> OUTPUT generateValue(String identifier,Class<INPUT> inputClass,Class<OUTPUT> outputClass,INPUT input);

	
    
    /**/
    
    
     
}
