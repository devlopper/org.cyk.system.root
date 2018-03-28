package org.cyk.system.root.business.impl.integration;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.MetricCollectionBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.__test__.TestCase;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.persistence.api.mathematics.MetricCollectionDao;
import org.junit.Test;

public class DeleteIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
    
    @Test
    public void deleteContactCollection(){
    	TestCase testCase = instanciateTestCase();
    	ContactCollection contactCollection  = inject(ContactCollectionBusiness.class).instanciateOneRandomly();
    	testCase.create(contactCollection);
    	testCase.delete(contactCollection);
    	testCase.clean();
    }
    
    @Test
    public void deletePerson(){
    	TestCase testCase = instanciateTestCase();
    	Person person  = inject(PersonBusiness.class).instanciateOneRandomly("p001");
    	testCase.create(person);
    	testCase.delete(person);
    	testCase.clean();
    }

    @Test
    public void deletePersonAndMetricCollection(){
    	TestCase testCase = instanciateTestCase();
    	testCase.create(inject(MetricCollectionBusiness.class).instanciateOne("mc001", "my metric collections"));
    	
    	Person person  = inject(PersonBusiness.class).instanciateOneRandomly("p002");
    	person.getMetricCollectionIdentifiableGlobalIdentifiers().setSynchonizationEnabled(Boolean.TRUE);
    	person.getMetricCollectionIdentifiableGlobalIdentifiers().addOne(new MetricCollectionIdentifiableGlobalIdentifier(inject(MetricCollectionDao.class).read("mc001")
    			, person, null));
    	testCase.create(person);
    	testCase.delete(person);
    	testCase.clean();
    }
}
