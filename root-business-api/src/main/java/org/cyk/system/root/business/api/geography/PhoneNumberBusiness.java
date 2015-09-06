package org.cyk.system.root.business.api.geography;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.geography.PhoneNumber;

public interface PhoneNumberBusiness extends TypedBusiness<PhoneNumber> {

	String format(PhoneNumber phoneNumber);
    
}
