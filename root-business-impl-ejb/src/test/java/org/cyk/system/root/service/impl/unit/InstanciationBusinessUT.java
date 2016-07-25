package org.cyk.system.root.service.impl.unit;

import java.util.Collection;

import org.cyk.system.root.business.impl.geography.PhoneNumberTypeBusinessImpl;
import org.cyk.system.root.business.impl.mathematics.IntervalCollectionBusinessImpl;
import org.cyk.system.root.business.impl.mathematics.MetricCollectionBusinessImpl;
import org.junit.Test;
import org.mockito.InjectMocks;

public class InstanciationBusinessUT extends AbstractBusinessUT {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private PhoneNumberTypeBusinessImpl phoneNumberTypeBusiness;
	@InjectMocks private MetricCollectionBusinessImpl metricCollectionBusiness;
	@InjectMocks private IntervalCollectionBusinessImpl intervalCollectionBusiness;
	//@InjectMocks private MetricBusinessImpl metricCollectionBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(phoneNumberTypeBusiness);
		collection.add(metricCollectionBusiness);
		collection.add(intervalCollectionBusiness);
	}
	
	//@Test
	public void enumeration() {
		/*Assert.assertNotNull(phoneNumberTypeBusiness.instanciateOne());
		assertEquals(phoneNumberTypeBusiness.instanciateOne("Land"), new ObjectFieldValues(PhoneNumberType.class)
			.set(PhoneNumberType.FIELD_CODE, "Land").set(PhoneNumberType.FIELD_NAME, "Land"));
		assertEquals(phoneNumberTypeBusiness.instanciateOne("My Type"), new ObjectFieldValues(PhoneNumberType.class)
			.set(PhoneNumberType.FIELD_CODE, "MyType").set(PhoneNumberType.FIELD_NAME, "My Type"));
		
		ObjectFieldValues objectFieldValues = new ObjectFieldValues(PhoneNumberType.class).set(PhoneNumberType.FIELD_CODE, "YaYA")
				.set(PhoneNumberType.FIELD_NAME, "My Name");
		assertEquals(phoneNumberTypeBusiness.instanciateOne(objectFieldValues), objectFieldValues);
		
		objectFieldValues = new ObjectFieldValues(PhoneNumberType.class)
				.set(PhoneNumberType.FIELD_NAME, "My Name");
		assertEquals(phoneNumberTypeBusiness.instanciateOne(objectFieldValues), new ObjectFieldValues(PhoneNumberType.class)
				.set(PhoneNumberType.FIELD_NAME, "My Name"));
		*/
	}
	
	@Test
	public void locality() {
		//ObjectFieldValues objectFieldValues = new ObjectFieldValues(Locality.class).set(Locality.FIELD_CODE, "MonCOde");
	}
	
	@Test
	public void party() {
		//ObjectFieldValues objectFieldValues = new ObjectFieldValues(Party.class).set(Party.FIELD_CODE, "MonCOde");
	}
	
	@Test
	public void person() {
		//ObjectFieldValues objectFieldValues = new ObjectFieldValues(Person.class).set(Person.FIELD_CODE, "MonCOde");
	}
	
	/**/
	
	

}
