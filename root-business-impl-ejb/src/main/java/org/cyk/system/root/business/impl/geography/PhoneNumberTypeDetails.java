package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.geography.PhoneNumberType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PhoneNumberTypeDetails extends AbstractOutputDetails<PhoneNumberType> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	
	public PhoneNumberTypeDetails(PhoneNumberType phoneNumberType) {
		super(phoneNumberType);
		
	}

	
}
