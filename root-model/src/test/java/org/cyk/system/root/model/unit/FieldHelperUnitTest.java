package org.cyk.system.root.model.unit;


import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;

public class FieldHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = 8008545189557409317L;

	@Override
	protected void _execute_() {
		super._execute_();
		Person person = new Person();
		FieldHelper.getInstance().set(person,(Object)null, Person.FIELD_NATIONALITY);
		
		assertEquals(ContactCollection.class, FieldHelper.getInstance().getType(PhoneNumber.class
				, FieldHelper.getInstance().get(PhoneNumber.class, PhoneNumber.FIELD_COLLECTION)));
	}
	
	
	
}
