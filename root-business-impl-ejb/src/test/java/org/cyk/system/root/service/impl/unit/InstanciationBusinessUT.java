package org.cyk.system.root.service.impl.unit;

import java.util.Collection;

import org.cyk.system.root.business.impl.geography.PhoneNumberTypeBusinessImpl;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.test.TestEnvironmentListener;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

public class InstanciationBusinessUT extends AbstractBusinessUT {

	private static final long serialVersionUID = 124355073578123984L;

	@InjectMocks private PhoneNumberTypeBusinessImpl phoneNumberTypeBusiness;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(phoneNumberTypeBusiness);
	}
	
	@Test
	public void enumeration() {
		Assert.assertNotNull(phoneNumberTypeBusiness.instanciateOne());
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
	}
	
	/**/
	
	protected void assertEquals(Object actual,ObjectFieldValues expected){
		for(TestEnvironmentListener listener : TestEnvironmentListener.COLLECTION)
			listener.assertEquals(actual, expected);
	}

}
