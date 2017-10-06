package org.cyk.system.root.business.impl.unit;

import java.util.Collection;

import org.cyk.system.root.business.impl.DetailsClassLocator;
import org.cyk.system.root.business.impl.geography.ContactCollectionBusinessImpl;
import org.cyk.system.root.business.impl.geography.LocalityBusinessImpl;
import org.cyk.system.root.business.impl.geography.LocalityTypeBusinessImpl;
import org.cyk.system.root.business.impl.party.person.AllergyBusinessImpl;
import org.cyk.system.root.business.impl.party.person.MedicationBusinessImpl;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.party.person.Allergy;
import org.cyk.system.root.model.party.person.Medication;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.mockito.InjectMocks;

public class DetailsClassLocatorUT extends AbstractUnitTest {

	private static final long serialVersionUID = 3681555921786058917L;
	
	@InjectMocks private DetailsClassLocator locator;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(locator);
	}
	
	@Override
	protected void _execute_() {
		super._execute_();
		assertDetailsClass(Locality.class,LocalityBusinessImpl.Details.class);
		assertDetailsClass(LocalityType.class,LocalityTypeBusinessImpl.Details.class);
		assertDetailsClass(Allergy.class,AllergyBusinessImpl.Details.class);
		assertDetailsClass(Medication.class,MedicationBusinessImpl.Details.class);
		assertDetailsClass(ContactCollection.class,ContactCollectionBusinessImpl.Details.class);
	}
	
	private void assertDetailsClass(Class<?> aClass,Class<?> expectedDetails){
		assertEquals(aClass.getName()+" details not found", expectedDetails, locator.locate(aClass));
	}
	
}
