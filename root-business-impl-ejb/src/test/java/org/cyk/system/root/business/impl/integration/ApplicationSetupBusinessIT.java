package org.cyk.system.root.business.impl.integration;

import java.util.Map;
import java.util.Set;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.persistence.api.geography.LocalityDao;
import org.cyk.system.root.persistence.impl.AbstractPersistenceService;

public class ApplicationSetupBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Override
    protected void populate() {
    	GlobalIdentifier.EXCLUDED.add(Value.class);
    	super.populate();
    	System.out.println("Queries registered");
    	Integer count = 0;
    	for(Map.Entry<Class<?>, Set<String>> entry : AbstractPersistenceService.NAMED_QUERIES_MAP.getMap().entrySet()){
    		System.out.println(entry.getKey()+" : "+entry.getValue().size());
    		count += entry.getValue().size();
    	}
    	System.out.println("Total : "+count);
    }
    
    @Override
    protected void businesses() {
    	assertEquals(inject(LocalityBusiness.class).find(RootConstant.Code.Locality.AFRICA), inject(LocalityBusiness.class).findParent(RootConstant.Code.Country.COTE_DIVOIRE));
    	assertEquals("number of direct children of africa",Boolean.TRUE, inject(LocalityBusiness.class).findDirectChildrenByParent(inject(LocalityDao.class).read(RootConstant.Code.Locality.AFRICA)).size()>0);
    	assertEquals("number of direct children of europe",Boolean.TRUE, inject(LocalityBusiness.class).findDirectChildrenByParent(inject(LocalityDao.class).read(RootConstant.Code.Locality.EUROPE)).size()>0);
    	assertEquals("number of direct children of america",Boolean.TRUE, inject(LocalityBusiness.class).findDirectChildrenByParent(inject(LocalityDao.class).read(RootConstant.Code.Locality.AMERICA)).size()>0);
    	/*
    	assertEquals(javax.persistence.TemporalType.TIMESTAMP, GlobalIdentifierPersistenceMappingConfiguration
    			.getProperty(javax.persistence.Temporal.class, Movement.class, FieldHelper.getInstance().buildPath(Movement.FIELD_GLOBAL_IDENTIFIER
    					,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE)).value());
    	*/
    }
    
    
}
