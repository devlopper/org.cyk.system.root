package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.information.IdentifiableCollectionBusiness;
import org.cyk.system.root.business.api.information.IdentifiableCollectionItemBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.information.IdentifiableCollectionItem;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionDao;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionItemDao;
import org.cyk.system.root.persistence.api.information.IdentifiableCollectionTypeDao;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.junit.Test;

public class IdentifiableCollectionBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Override
    protected void businesses() {}

    @Test
    public void crud(){
    	create(inject(PersonBusiness.class).instanciateOneRandomly("P001"));
    	create(inject(PersonBusiness.class).instanciateOneRandomly("P002"));
    	create(inject(PersonBusiness.class).instanciateOneRandomly("P003"));
    	create(inject(PersonBusiness.class).instanciateOneRandomly("P004"));
    	create(inject(PersonBusiness.class).instanciateOneRandomly("P005"));
    	
    	create(inject(IdentifiableCollectionBusiness.class).instanciateOne(new String[]{"G1","Groupe 1"})
    		.setType(inject(IdentifiableCollectionTypeDao.class).read(RootConstant.Code.IdentifiableCollectionType.PERSON)));
    	create(inject(IdentifiableCollectionBusiness.class).instanciateOne(new String[]{"G2","Groupe de personnes 2"})
        		.setType(inject(IdentifiableCollectionTypeDao.class).read(RootConstant.Code.IdentifiableCollectionType.PERSON)));
    	
    	assertEquals(0l, inject(IdentifiableCollectionItemDao.class).countByCollection(inject(IdentifiableCollectionDao.class).read("G1")));
    	assertEquals(0l, inject(IdentifiableCollectionItemDao.class).countByCollection(inject(IdentifiableCollectionDao.class).read("G2")));
    	
    	IdentifiableCollectionItem identifiableCollectionItem = inject(IdentifiableCollectionItemBusiness.class).instanciateOne();
    	identifiableCollectionItem.setIdentifiableGlobalIdentifier(inject(PersonDao.class).read("P001").getGlobalIdentifier());
    	identifiableCollectionItem.setCollection(inject(IdentifiableCollectionDao.class).read("G1"));
    	create(identifiableCollectionItem);
    	
    	identifiableCollectionItem = inject(IdentifiableCollectionItemBusiness.class).instanciateOne();
    	identifiableCollectionItem.setIdentifiableGlobalIdentifier(inject(PersonDao.class).read("P002").getGlobalIdentifier());
    	identifiableCollectionItem.setCollection(inject(IdentifiableCollectionDao.class).read("G2"));
    	create(identifiableCollectionItem);
    	
    	identifiableCollectionItem = inject(IdentifiableCollectionItemBusiness.class).instanciateOne();
    	identifiableCollectionItem.setIdentifiableGlobalIdentifier(inject(PersonDao.class).read("P005").getGlobalIdentifier());
    	identifiableCollectionItem.setCollection(inject(IdentifiableCollectionDao.class).read("G2"));
    	create(identifiableCollectionItem);
    	
    	assertEquals(1l, inject(IdentifiableCollectionItemDao.class).countByCollection(inject(IdentifiableCollectionDao.class).read("G1")));
    	assertEquals(2l, inject(IdentifiableCollectionItemDao.class).countByCollection(inject(IdentifiableCollectionDao.class).read("G2")));
    	
    	identifiableCollectionItem = inject(IdentifiableCollectionItemBusiness.class).instanciateOne();
    	identifiableCollectionItem.setIdentifiableGlobalIdentifier(inject(PersonDao.class).read("P003").getGlobalIdentifier());
    	identifiableCollectionItem.setCollection(inject(IdentifiableCollectionDao.class).read("G1"));
    	create(identifiableCollectionItem);
    	
    	identifiableCollectionItem = inject(IdentifiableCollectionItemBusiness.class).instanciateOne();
    	identifiableCollectionItem.setIdentifiableGlobalIdentifier(inject(PersonDao.class).read("P004").getGlobalIdentifier());
    	identifiableCollectionItem.setCollection(inject(IdentifiableCollectionDao.class).read("G2"));
    	create(identifiableCollectionItem);
    	
    	assertEquals(2l, inject(IdentifiableCollectionItemDao.class).countByCollection(inject(IdentifiableCollectionDao.class).read("G1")));
    	assertEquals(3l, inject(IdentifiableCollectionItemDao.class).countByCollection(inject(IdentifiableCollectionDao.class).read("G2")));
    	
    	delete(inject(IdentifiableCollectionItemDao.class).readByCollection(inject(IdentifiableCollectionDao.class).read("G1")).iterator().next());
    	
    	assertEquals(1l, inject(IdentifiableCollectionItemDao.class).countByCollection(inject(IdentifiableCollectionDao.class).read("G1")));
    	assertEquals(3l, inject(IdentifiableCollectionItemDao.class).countByCollection(inject(IdentifiableCollectionDao.class).read("G2")));
    	
    }
    
    
}
