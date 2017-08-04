package org.cyk.system.root.business.impl.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.PhoneNumber;

@Getter @Setter
public class PhoneNumberDetails extends AbstractContactDetails<PhoneNumber> implements Serializable {

	private static final long serialVersionUID = 4444472169870625893L;

	public PhoneNumberDetails(PhoneNumber electronicMail) {
		super(electronicMail);
	}

}
