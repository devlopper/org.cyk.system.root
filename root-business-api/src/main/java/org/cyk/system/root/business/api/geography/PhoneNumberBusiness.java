package org.cyk.system.root.business.api.geography;

import java.util.List;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.PhoneNumber;

public interface PhoneNumberBusiness extends AbstractContactBusiness<PhoneNumber> {

	PhoneNumber instanciateOne(ContactCollection collection,String countryCode,String typeCode, String number);
	PhoneNumber instanciateOne(ContactCollection collection, String number);
	List<PhoneNumber> instanciateMany(ContactCollection collection,List<String[]> values);
	List<PhoneNumber> instanciateMany(ContactCollection collection,String[] numbers);
}
