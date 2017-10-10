package org.cyk.system.root.business.impl.unit;

import java.util.Collection;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.geography.LocalityTypeBusiness;
import org.cyk.system.root.business.api.party.person.AllergyBusiness;
import org.cyk.system.root.business.api.party.person.MedicationBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipTypeGroupBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.business.impl.DetailsClassLocator;
import org.cyk.system.root.business.impl.geography.ContactCollectionBusinessImpl;
import org.cyk.system.root.business.impl.geography.LocalityBusinessImpl;
import org.cyk.system.root.business.impl.geography.LocalityTypeBusinessImpl;
import org.cyk.system.root.business.impl.party.person.AllergyBusinessImpl;
import org.cyk.system.root.business.impl.party.person.MedicationBusinessImpl;
import org.cyk.system.root.business.impl.party.person.PersonRelationshipBusinessImpl;
import org.cyk.system.root.business.impl.party.person.PersonRelationshipTypeBusinessImpl;
import org.cyk.system.root.business.impl.party.person.PersonRelationshipTypeGroupBusinessImpl;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.party.person.Allergy;
import org.cyk.system.root.model.party.person.Medication;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.utility.common.ClassLocator;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.mockito.InjectMocks;

public class ClassLocatorUT extends AbstractUnitTest {

	private static final long serialVersionUID = 3681555921786058917L;
	
	@InjectMocks private BusinessInterfaceLocator businessInterfaceLocator;
	@InjectMocks private DetailsClassLocator detailsClassLocator;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(businessInterfaceLocator);
		collection.add(detailsClassLocator);
	}
	
	public void assertBusinessInterface(){
		assertClass(businessInterfaceLocator,Locality.class,LocalityBusiness.class);
		assertClass(businessInterfaceLocator,LocalityType.class,LocalityTypeBusiness.class);
		assertClass(businessInterfaceLocator,Allergy.class,AllergyBusiness.class);
		assertClass(businessInterfaceLocator,Medication.class,MedicationBusiness.class);
		assertClass(businessInterfaceLocator,ContactCollection.class,ContactCollectionBusiness.class);
		assertClass(businessInterfaceLocator,PersonRelationship.class,PersonRelationshipBusiness.class);
		assertClass(businessInterfaceLocator,PersonRelationshipTypeGroup.class,PersonRelationshipTypeGroupBusiness.class);
		assertClass(businessInterfaceLocator,PersonRelationshipType.class,PersonRelationshipTypeBusiness.class);
	}
		
	public void assertDetails(){
		assertClass(detailsClassLocator,Locality.class,LocalityBusinessImpl.Details.class);
		assertClass(detailsClassLocator,LocalityType.class,LocalityTypeBusinessImpl.Details.class);
		assertClass(detailsClassLocator,Allergy.class,AllergyBusinessImpl.Details.class);
		assertClass(detailsClassLocator,Medication.class,MedicationBusinessImpl.Details.class);
		assertClass(detailsClassLocator,ContactCollection.class,ContactCollectionBusinessImpl.Details.class);
		assertClass(detailsClassLocator,PersonRelationship.class,PersonRelationshipBusinessImpl.Details.class);
		assertClass(detailsClassLocator,PersonRelationshipTypeGroup.class,PersonRelationshipTypeGroupBusinessImpl.Details.class);
		assertClass(detailsClassLocator,PersonRelationshipType.class,PersonRelationshipTypeBusinessImpl.Details.class);
	}
	
	private void assertClass(ClassLocator locator,Class<?> aClass,Class<?> expectedDetails){
		assertEquals(aClass.getName()+" "+locator.getClassType()+" not found", expectedDetails, locator.locate(aClass));
	}
	
}
