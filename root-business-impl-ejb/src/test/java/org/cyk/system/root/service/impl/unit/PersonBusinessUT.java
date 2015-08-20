package org.cyk.system.root.service.impl.unit;

import java.util.Collection;

import org.cyk.system.root.business.impl.party.person.PersonBusinessImpl;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PersonBusinessUT extends AbstractUnitTest {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private PersonBusinessImpl personBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(personBusiness);
		//languageBusiness.setCachingEnabled(Boolean.FALSE);
	}
	
	@Test
	public void findNames() {
	    assertEquals("Koffi Paul",personBusiness.findNames(new Person("Koffi", "Paul")));
	    assertEquals("Koffi",personBusiness.findNames(new Person("Koffi", null)));
	    assertEquals("Paul",personBusiness.findNames(new Person(null, "Paul")));
	    assertEquals("",personBusiness.findNames(new Person(null, null)));
	}

	
	
}
